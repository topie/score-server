package com.orange.score.module.score.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.SqlUtil;
import com.orange.score.common.core.BaseService;
import com.orange.score.common.utils.MethodUtil;
import com.orange.score.common.utils.SearchItem;
import com.orange.score.common.utils.SearchUtil;
import com.orange.score.database.core.model.ColumnJson;
import com.orange.score.database.score.dao.ScoreRecordMapper;
import com.orange.score.database.score.model.*;
import com.orange.score.database.security.model.Role;
import com.orange.score.module.core.service.IColumnJsonService;
import com.orange.score.module.score.service.*;
import com.orange.score.module.security.service.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by chenJz1012 on 2018-04-16.
 */
@Service
@Transactional
public class ScoreRecordServiceImpl extends BaseService<ScoreRecord> implements IScoreRecordService {

    @Autowired
    private ScoreRecordMapper scoreRecordMapper;

    @Autowired
    private IColumnJsonService iColumnJsonService;

    @Autowired
    private IIndicatorService iIndicatorService;

    @Autowired
    private IHouseOtherService iHouseOtherService;

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private IHouseProfessionService iHouseProfessionService;

    @Autowired
    private IHouseRelationshipService iHouseRelationshipService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private IScoreResultService iScoreResultService;

    @Autowired
    private IMaterialAcceptRecordService iMaterialAcceptRecordService;

    @Override
    public PageInfo<ScoreRecord> selectByFilterAndPage(ScoreRecord scoreRecord, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScoreRecord> list = selectByFilter(scoreRecord);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<ScoreRecord> selectByFilterAndPage(Condition condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScoreRecord> list = selectByFilter(condition);
        return new PageInfo<>(list);
    }

    @Override
    public List<ScoreRecord> selectByFilter(ScoreRecord scoreRecord) {
        Page<ScoreRecord> tmp = SqlUtil.getLocalPage();
        SqlUtil.clearLocalPage();
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (scoreRecord != null) {
            if (scoreRecord.getBatchId() != null) {
                criteria.andEqualTo("batchId", scoreRecord.getBatchId());
            }
            ColumnJson columnJson = new ColumnJson();
            columnJson.setTableName("t_pb_score_record");
            List<ColumnJson> list = iColumnJsonService.selectByFilter(columnJson);
            if (list.size() > 0) {
                List<SearchItem> searchItems = new ArrayList<>();
                columnJson = list.get(0);
                JSONArray jsonArray = JSONArray.parseArray(columnJson.getSearchConf());
                if (StringUtils.isNotEmpty(columnJson.getSearchConf())) {
                    for (Object o : jsonArray) {
                        o = (JSONObject) o;
                        SearchItem searchItem = new SearchItem();
                        searchItem.setLabel(((JSONObject) o).getString("label"));
                        searchItem.setName(((JSONObject) o).getString("name"));
                        searchItem.setType(((JSONObject) o).getString("type"));
                        searchItem.setSearchType(((JSONObject) o).getString("searchType"));
                        if (StringUtils.isNotEmpty(searchItem.getName())) {
                            Object value = MethodUtil.invokeGet(scoreRecord, searchItem.getName());
                            if (value != null) {
                                if (value instanceof String) {
                                    if (StringUtils.isNotEmpty((String) value)) searchItem.setValue(value);
                                } else {
                                    searchItem.setValue(value);
                                }
                            }
                        }
                        searchItems.add(searchItem);
                    }
                }
                SearchUtil.convert(criteria, searchItems);
            }
        }
        if (tmp != null) SqlUtil.setLocalPage(tmp);
        return scoreRecordMapper.selectByCondition(condition);
    }

    @Override
    public List<ScoreRecord> selectByFilter(Condition condition) {
        return scoreRecordMapper.selectByCondition(condition);
    }

    @Override
    public void insertToInitRecords(Integer batchId, Integer personId) {
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("batchId", batchId);
        criteria.andEqualTo("personId", personId);
        scoreRecordMapper.deleteByCondition(condition);
        IdentityInfo identityInfo = iIdentityInfoService.findById(personId);
        HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", personId);
        HouseProfession profession = iHouseProfessionService.findBy("identityInfoId", personId);

        condition = new Condition(HouseRelationship.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("identityInfoId", personId);
        List<HouseRelationship> relationshipList = iHouseRelationshipService.findByCondition(condition);

        CompanyInfo companyInfo = iCompanyInfoService.findById(identityInfo.getCompanyId());
        List<Indicator> indicators = iIndicatorService.findAll();
        for (Indicator indicator : indicators) {
            /*
            1：只显示后台；2：自助测评+后台显示；3：只自助测评
             */
            if (indicator.getStatus()>2){
                continue;
            }

            /*
            2020年1月9日
            自有住房分数
            1、申请人输入页面中，页面7选择“按照“津发改社会〔2018〕26号”文件计算”
            （1）住房分数
                打分形式：单项点选

                分值项	分值
                    有2019年12月31日前购买的住房	40分
                    无	0分

                打分部门：住建委、规自局

            （2）区域分数
                打分形式：单项点选

                分值项	分值
                    滨海新区	20分
                    武清区、宝坻区、静海区、宁河区、蓟州区	15分
                    其他	0分
                打分部门：公安局
            2、申请人输入页面中，页面7选择“不按照“津发改社会〔2018〕26号”文件计算”
                打分形式：手动输入一位小数数字
                打分部门：住建委、规自局
             */
            // “不按照“津发改社会〔2018〕26号”文件计算”
//            if (identityInfo.getIs201826Doc()!=null && identityInfo.getIs201826Doc() == 0){
//                if (indicator.getId() == 1021 || indicator.getId() == 1022){
//                    continue;
//                }
//            }
//            // “按照“津发改社会〔2018〕26号”文件计算”
//            if (identityInfo.getIs201826Doc()!=null && identityInfo.getIs201826Doc() == 1){
//                if (indicator.getId() == 1025){
//                    continue;
//                }
//            }
//            // 如果为空值
//            if (identityInfo.getIs201826Doc()==null){
//                if (indicator.getId() == 1021 || indicator.getId() == 1022 || indicator.getId()==1025){
//                    continue;
//                }
//            }

            ScoreRecord record = new ScoreRecord();
            record.setBatchId(batchId);
            record.setAcceptAddressId(identityInfo.getAcceptAddressId());
            record.setAcceptDate(new Date());
            record.setAcceptNumber(identityInfo.getAcceptNumber());
            record.setIndicatorId(indicator.getId());
            record.setIndicatorName(indicator.getName());
            record.setcTime(new Date());
            record.setPersonName(identityInfo.getName());
            record.setPersonMobilePhone(houseOther.getSelfPhone());
            record.setPersonId(identityInfo.getId());
            record.setPersonIdNum(identityInfo.getIdNumber());
            if (companyInfo != null) {
                record.setCompanyId(companyInfo.getId());
                record.setCompanyName(companyInfo.getCompanyName());
            }
            List<Integer> materialList = iIndicatorService.selectBindMaterialIds(indicator.getId());
            if (materialList.size() > 0) {
                record.setStatus(2);
            } else {
                record.setStatus(1);
                record.setSubmitDate(new Date());
            }
            List<Integer> roleList = iIndicatorService.selectBindDepartmentIds(indicator.getId());
            for (Integer roleId : roleList) {
                Role role = roleService.findRoleById(roleId);
                if (role == null) continue;
                record.setId(null);
                record.setOpRoleId(roleId);
                record.setOpRole(role.getRoleName());
                /**
                 * 2020年2月26日，自动打分
                 * （一）居住
                 页面7居住信息中：
                 申请人既无自有住房，也无租赁备案信息，即页面信息层级PDF中进入最下方空白项。
                 住建委、规自局打分项自动打为
                 无	0分
                 （二）住房公积金
                 页面1基本信息中：
                 “申请人是否参加住房公积金”——否
                 自动打分0分。
                 （三）年龄
                 年龄自动打分
                 （四）受教育程度
                 1、申请人选择“高级技工学校高级班”、“无”。教委打分项自动打为
                 无	0分
                 2、申请人选择“本科及以上学历”、“大专学历”、“无”。人社打分项自动打为
                 无	0分
                 （五）专业技术人员职业资格、职业技能水平
                 页面3人社相关信息中：
                 是否具有国家职业资格——无
                 自动打为
                 无	0分
                 （六）职业（工种）
                 页面3人社相关信息中：
                 是否具有国家职业资格——“具有专业技术人员职业资格”、“无”
                 自动打为
                 无	0分
                 （七）婚姻情况
                 页面1基本信息中：
                 配偶是否在津就业且用人单位依法缴纳社会保险累计满24个月——否
                 民政、人社自动打为
                 无	0分
                 （八）奖项和荣誉称号
                 页面3人社相关信息中：
                 奖项和荣誉称号情况——无
                 自动打为
                 无	0分
                 （九）退役军人
                 页面8退役军人相关信息页面中：
                 服役期间立功情况——无
                 自动打为
                 无	0分
                 （十）违法违规与犯罪
                 1、人社局：虚假材料数据库自动打分
                 虚假材料数据库须解决历史数据录入问题。
                 2、公安局：如在页面1中“五年内有无刑事犯罪记录”选择“无”自动打为：
                 无	0分
                 */
//                if (record.getIndicatorId()==1020){
//                    if(identityInfo.getRentHouseAddress()==null){
//                        record.setItemId(0);
//                        record.setStatus(4);
//                        record.setScoreValue(new BigDecimal(0));
//                    }
//                }
//                // 年龄
//                if (record.getIndicatorId() == 1 && identityInfo.getAge()!=null){
//                    if(identityInfo.getAge()>45){
//                        record.setItemId(3);
//                        BigDecimal value = new BigDecimal(0);
//                        record.setScoreValue(value);
//                    }else if(identityInfo.getAge()>36){
//                        record.setItemId(2);
//                        BigDecimal value = new BigDecimal(5);
//                        record.setScoreValue(value);
//                    }else if (identityInfo.getAge()<36){
//                        record.setItemId(1);
//                        BigDecimal value = new BigDecimal(10);
//                        record.setScoreValue(value);
//                    }
//                    record.setStatus(4);
//                }
//
//                // 页面1基本信息中：“申请人是否参加住房公积金”——否         自动打分0分。
//                if (record.getIndicatorId()!=null && record.getIndicatorId() == 8){
//                    if (houseOther.getProvidentFund()!=null && houseOther.getProvidentFund()== 2){ // 1、是；2、否"
//                        record.setItemId(0);
//                        record.setStatus(4);
//                        record.setScoreValue(new BigDecimal(0));
//                    }
//                }
//                // 受教育程度，
//                if (record.getIndicatorId() == 3 ){
//                    //市教委
//                    if (record.getOpRoleId() == 6 && houseOther.getCultureDegree()!=null && (houseOther.getCultureDegree()==1011 || houseOther.getCultureDegree()==1013) ){
//                        record.setItemId(1013);
//                        record.setStatus(4);
//                        record.setScoreValue(new BigDecimal(0));
//                    }
//                    // 人社
//                    if (record.getOpRoleId() == 3 && houseOther.getCultureDegree()!=null && (houseOther.getCultureDegree()==4 || houseOther.getCultureDegree()==5 || houseOther.getCultureDegree()==1013) ){
//                        record.setItemId(1013);
//                        record.setStatus(4);
//                        record.setScoreValue(new BigDecimal(0));
//                    }
//                }
//                // 专业技术人员职业资格、职业技能水平
//                if (record.getIndicatorId() == 4){
//                    if (profession.getProfessionType()!=null && profession.getProfessionType()==1){
//                        record.setItemId(1014);
//                        record.setStatus(4);
//                        record.setScoreValue(new BigDecimal(0));
//                    }
//                }
//                //职业（工种）
//                if (record.getIndicatorId()==11){
//                    if (profession.getJobType()!=null && profession.getJobType()==30){
//                        record.setItemId(30);
//                        record.setStatus(4);
//                        record.setScoreValue(new BigDecimal(0));
//                    }
//                }
//                // 婚姻情况
//                if (record.getIndicatorId()==14 && record.getOpRoleId()!=null && record.getOpRoleId()==3){
//                    if (relationshipList.size()>0){
//                        for (HouseRelationship relationship : relationshipList){
//                            if (relationship.getRelationship()!=null && relationship.getRelationship().equalsIgnoreCase("配偶")){
//                                if (relationship.getInTianjin()!=null && relationship.getInTianjin()==2){
//                                    record.setItemId(37);
//                                    record.setStatus(4);
//                                    record.setScoreValue(new BigDecimal(0));
//                                }
//                            }
//                        }
//                    }
//                }
//                // 奖项和荣誉称号
//                if (record.getIndicatorId()==16){
//                    if (houseOther.getAwardsTitle()!=null && houseOther.getAwardsTitle()==9){
//                        record.setItemId(9);
//                        record.setStatus(4);
//                        record.setScoreValue(new BigDecimal(0));
//                    }
//                }
//                //退役军人
//                if (record.getIndicatorId()==17){
//                    if (houseOther.getSoldierMeritorious()!=null && houseOther.getSoldierMeritorious()==50){
//                        record.setItemId(50);
//                        record.setStatus(4);
//                        record.setScoreValue(new BigDecimal(0));
//                    }
//                }
//                // 5年内是否有刑事犯罪记录
//                if (record.getIndicatorId() == 902){
//                    if (houseOther.getPenalty()!=null && houseOther.getPenalty()==2){
//                        record.setItemId(1031);
//                        record.setStatus(4);
//                        record.setScoreValue(new BigDecimal(0));
//                    }
//                }

                save(record);
            }
        }
    }

    @Override
    public void insertToReInitRecords(Integer batchId, Integer personId, Integer indicatorId) {
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("batchId", batchId);
        criteria.andEqualTo("personId", personId);
        if (indicatorId != null) {
            criteria.andEqualTo("indicatorId", indicatorId);
        }
        scoreRecordMapper.deleteByCondition(condition);
        IdentityInfo identityInfo = iIdentityInfoService.findById(personId);
        HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", personId);
        CompanyInfo companyInfo = iCompanyInfoService.findById(identityInfo.getCompanyId());
        List<Indicator> indicators = new ArrayList<>();
        if (indicatorId != null) {
            Indicator indicator = iIndicatorService.findById(indicatorId);
            indicators.add(indicator);
        } else {
            indicators = iIndicatorService.findAll();
        }

        for (Indicator indicator : indicators) {
            ScoreRecord record = new ScoreRecord();
            record.setBatchId(batchId);
            record.setAcceptDate(new Date());
            record.setAcceptNumber(identityInfo.getAcceptNumber());
            record.setIndicatorId(indicator.getId());
            record.setIndicatorName(indicator.getName());
            record.setcTime(new Date());
            record.setPersonName(identityInfo.getName());
            record.setPersonMobilePhone(houseOther.getSelfPhone());
            record.setPersonId(identityInfo.getId());
            record.setPersonIdNum(identityInfo.getIdNumber());
            if (companyInfo != null) {
                record.setCompanyId(companyInfo.getId());
                record.setCompanyName(companyInfo.getCompanyName());
            }
            List<Integer> materialList = iIndicatorService.selectBindMaterialIds(indicator.getId());
            if (materialList.size() > 0) {
                record.setStatus(2);
            } else {
                record.setStatus(1);
                record.setSubmitDate(new Date());
            }
            List<Integer> roleList = iIndicatorService.selectBindDepartmentIds(indicator.getId());
            for (Integer roleId : roleList) {
                Role role = roleService.findRoleById(roleId);
                if (role == null) continue;
                record.setId(null);
                record.setOpRoleId(roleId);
                record.setOpRole(role.getRoleName());
                MaterialAcceptRecord materialAcceptRecord = new MaterialAcceptRecord();
                materialAcceptRecord.setIndicatorId(record.getIndicatorId());
                materialAcceptRecord.setBatchId(record.getBatchId());
                materialAcceptRecord.setPersonId(record.getPersonId());
                materialAcceptRecord.setRoleId(record.getOpRoleId());
                List<MaterialAcceptRecord> materialAcceptRecords = iMaterialAcceptRecordService
                        .findByT(materialAcceptRecord);
                if (materialAcceptRecords.size() > 0) {
                    for (MaterialAcceptRecord acceptRecord : materialAcceptRecords) {
                        iMaterialAcceptRecordService.deleteById(acceptRecord.getId());
                    }
                }
                save(record);
            }

        }
    }

    @Override
    public void insertToAppendInitRecords(Integer batchId, Integer personId) {
        IdentityInfo identityInfo = iIdentityInfoService.findById(personId);
        HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", personId);
        CompanyInfo companyInfo = iCompanyInfoService.findById(identityInfo.getCompanyId());
        List<Indicator> indicators = iIndicatorService.findAll();
        for (Indicator indicator : indicators) {
            ScoreRecord record = new ScoreRecord();
            record.setBatchId(batchId);
            record.setAcceptDate(new Date());
            record.setAcceptNumber(identityInfo.getAcceptNumber());
            record.setIndicatorId(indicator.getId());
            record.setIndicatorName(indicator.getName());
            record.setcTime(new Date());
            record.setPersonName(identityInfo.getName());
            record.setPersonMobilePhone(houseOther.getSelfPhone());
            record.setPersonId(identityInfo.getId());
            record.setPersonIdNum(identityInfo.getIdNumber());
            if (companyInfo != null) {
                record.setCompanyId(companyInfo.getId());
                record.setCompanyName(companyInfo.getCompanyName());
            }
            List<Integer> roleList = iIndicatorService.selectBindDepartmentIds(indicator.getId());
            for (Integer roleId : roleList) {
                Role role = roleService.findRoleById(roleId);
                if (role == null) continue;
                record.setOpRoleId(roleId);
                record.setOpRole(role.getRoleName());
                List<ScoreRecord> list = findByT(record);
                if (list == null || list.size() == 0) {
                    record.setId(null);
                    List<Integer> materialList = iIndicatorService.selectBindMaterialIds(indicator.getId());
                    if (materialList.size() > 0) {
                        record.setStatus(2);
                    } else {
                        record.setStatus(1);
                        record.setSubmitDate(new Date());
                    }
                    save(record);
                }
            }
        }
    }

    @Override
    public void insertToGetScoreResult(Integer batchId, Integer personId) {
        IdentityInfo identityInfo = iIdentityInfoService.findById(personId);
        Condition condition = new Condition(Indicator.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("scoreRule", 0);
        List<Indicator> indicators = iIndicatorService.findByCondition(condition);
        for (Indicator indicator : indicators) {
            BigDecimal resultValue = findScoreRecordResultType0(batchId, personId, indicator.getId());
            if (resultValue == null) continue;
            ScoreResult scoreResult = new ScoreResult();
            scoreResult.setIndicatorId(indicator.getId());
            scoreResult.setIndicatorName(indicator.getName());
            scoreResult.setPersonId(personId);
            scoreResult.setBatchId(batchId);
            scoreResult.setPersonIdNum(identityInfo.getIdNumber());
            scoreResult.setPersonName(identityInfo.getName());
            scoreResult.setScoreValue(resultValue);
            scoreResult.setcTime(new Date());
            iScoreResultService.save(scoreResult);
        }
        criteria.andEqualTo("scoreRule", 1);
        indicators = iIndicatorService.findByCondition(condition);
        for (Indicator indicator : indicators) {
            BigDecimal resultValue = findScoreRecordResultType1(batchId, personId, indicator.getId());
            if (resultValue == null) continue;
            ScoreResult scoreResult = new ScoreResult();
            scoreResult.setIndicatorId(indicator.getId());
            scoreResult.setIndicatorName(indicator.getName());
            scoreResult.setPersonId(personId);
            scoreResult.setBatchId(batchId);
            scoreResult.setPersonIdNum(identityInfo.getIdNumber());
            scoreResult.setPersonName(identityInfo.getName());
            scoreResult.setScoreValue(resultValue);
            scoreResult.setcTime(new Date());
            iScoreResultService.save(scoreResult);
        }
        criteria.andEqualTo("scoreRule", 2);
        indicators = iIndicatorService.findByCondition(condition);
        for (Indicator indicator : indicators) {
            BigDecimal resultValue = findScoreRecordResultType2(batchId, personId, indicator.getId());
            if (resultValue == null) continue;
            ScoreResult scoreResult = new ScoreResult();
            scoreResult.setIndicatorId(indicator.getId());
            scoreResult.setIndicatorName(indicator.getName());
            scoreResult.setPersonId(personId);
            scoreResult.setBatchId(batchId);
            scoreResult.setPersonIdNum(identityInfo.getIdNumber());
            scoreResult.setPersonName(identityInfo.getName());
            scoreResult.setScoreValue(resultValue);
            scoreResult.setcTime(new Date());
            iScoreResultService.save(scoreResult);
        }

        criteria.andEqualTo("scoreRule", 3);
        indicators = iIndicatorService.findByCondition(condition);
        for (Indicator indicator : indicators) {
            BigDecimal resultValue = findScoreRecordResultType3(batchId, personId, indicator.getId());
            if (resultValue == null) continue;
            ScoreResult scoreResult = new ScoreResult();
            scoreResult.setIndicatorId(indicator.getId());
            scoreResult.setIndicatorName(indicator.getName());
            scoreResult.setPersonId(personId);
            scoreResult.setBatchId(batchId);
            scoreResult.setPersonIdNum(identityInfo.getIdNumber());
            scoreResult.setPersonName(identityInfo.getName());
            scoreResult.setcTime(new Date());
            scoreResult.setScoreValue(resultValue);
            iScoreResultService.save(scoreResult);
        }
    }

    @Override
    public void updateToScore(Integer batchId, Integer personId, Integer indicatorId, Integer roleId, Integer itemId,
            BigDecimal scoreValue) {
        ScoreRecord scoreRecord = new ScoreRecord();
        scoreRecord.setBatchId(batchId);
        scoreRecord.setPersonId(personId);
        scoreRecord.setIndicatorId(indicatorId);
        scoreRecord.setOpRoleId(roleId);
        scoreRecord = scoreRecordMapper.selectOne(scoreRecord);
        if (scoreRecord != null) {
            scoreRecord.setItemId(itemId);
            scoreRecord.setScoreValue(scoreValue);
            update(scoreRecord);
        }
    }

    @Override
    public ScoreRecord selectOne(ScoreRecord scoreRecord) {
        return scoreRecordMapper.selectOne(scoreRecord);
    }

    @Override
    public PageInfo<ScoreRecord> selectIdentityInfoByPage(Map argMap, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScoreRecord> list = scoreRecordMapper.selectIdentityInfo(argMap);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<ScoreRecord> selectIdentityInfoByPage_1(Map argMap, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScoreRecord> list = scoreRecordMapper.selectIdentityInfo_1(argMap);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<ScoreRecord> selectIdentityInfoByPage2(Map argMap, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScoreRecord> list = scoreRecordMapper.selectIsPreviewed(argMap);
        return new PageInfo<>(list);
    }

    @Override
    public List<ScoreRecord> selectIndicatorIdsByIdentityInfoIdAndRoleIds(Integer identityInfoId, List<Integer> roles) {
        return scoreRecordMapper.selectIndicatorIdsByIdentityInfoIdAndRoleIds(identityInfoId, roles);
    }

    @Override
    public List<ScoreRecord> selectIndicatorIdsByIdentityInfoIdAndRoleIds(Integer identityInfoId, Integer indicatorId,
            List<Integer> roles) {
        return scoreRecordMapper
                .selectIndicatorIdsByIdentityInfoIdAndRoleIdsAndIndicatorId(identityInfoId, indicatorId, roles);
    }

    @Override
    public List<ScoreRecord> provideDataToPolice(BatchConf batchConf) {
        Map map = new HashMap();
        map.put("id",batchConf.getId());
        map.put("indicatorValue",batchConf.getIndicatorValue());
        map.put("scoreValue",batchConf.getScoreValue());
        return scoreRecordMapper.provideDataToPolice(map);
    }

    @Override
    public List<Map> exportScored(Map argMap) {
        return scoreRecordMapper.exportScored(argMap);
    }

    @Override
    public List<Map> exportReview(Map argMap,List<Integer> roles) {
        return scoreRecordMapper.exportReview(argMap,roles);
    }

    private BigDecimal findScoreRecordResultType0(Integer batchId, Integer personId, Integer indicatorId) {
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("batchId", batchId);
        criteria.andEqualTo("personId", personId);
        criteria.andEqualTo("indicatorId", indicatorId);
        List<ScoreRecord> list = findByCondition(condition);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0).getScoreValue() == null ? BigDecimal.ZERO : list.get(0).getScoreValue();
        }
    }

    private BigDecimal findScoreRecordResultType1(Integer batchId, Integer personId, Integer indicatorId) {
        BigDecimal max = BigDecimal.ZERO;
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("batchId", batchId);
        criteria.andEqualTo("personId", personId);
        criteria.andEqualTo("indicatorId", indicatorId);
        List<ScoreRecord> list = findByCondition(condition);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            for (ScoreRecord scoreRecord : list) {
                if (scoreRecord.getScoreValue().floatValue() >= max.floatValue()) {
                    max = scoreRecord.getScoreValue();
                }
            }
        }
        return max;
    }

    private BigDecimal findScoreRecordResultType2(Integer batchId, Integer personId, Integer indicatorId) {
        BigDecimal value = BigDecimal.ZERO;
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("batchId", batchId);
        criteria.andEqualTo("personId", personId);
        criteria.andEqualTo("indicatorId", indicatorId);
        List<ScoreRecord> list = findByCondition(condition);
        boolean flag = true;
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            value = list.get(0).getScoreValue();
            for (ScoreRecord scoreRecord : list) {
                if (scoreRecord.getScoreValue().floatValue() != value.floatValue()) {
                    flag = false;
                }
            }
        }
        return flag ? value : BigDecimal.ZERO;
    }

    private BigDecimal findScoreRecordResultType3(Integer batchId, Integer personId, Integer indicatorId) {
        BigDecimal value = BigDecimal.ZERO;
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("batchId", batchId);
        criteria.andEqualTo("personId", personId);
        criteria.andEqualTo("indicatorId", indicatorId);
        List<ScoreRecord> list = findByCondition(condition);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            for (ScoreRecord scoreRecord : list) {
                value = value.add(scoreRecord.getScoreValue());
            }
        }
        return value;
    }
}

