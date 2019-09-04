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
        CompanyInfo companyInfo = iCompanyInfoService.findById(identityInfo.getCompanyId());
        List<Indicator> indicators = iIndicatorService.findAll();
        for (Indicator indicator : indicators) {
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
                /*
                2019年6月24日
                “守法诚信【公安】”生成时自动打为0分。实际打分时，因数量稀少，公安窗口手动申请重新打分。
                 */
//                if(record.getIndicatorId()!=null && record.getIndicatorId() == 1011){
//                    record.setStatus(4);
//                    record.setScoreValue(new BigDecimal(0));
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

