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
import com.orange.score.database.score.model.CompanyInfo;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.database.score.model.Indicator;
import com.orange.score.database.score.model.ScoreRecord;
import com.orange.score.database.security.model.Role;
import com.orange.score.module.core.service.IColumnJsonService;
import com.orange.score.module.score.service.*;
import com.orange.score.module.security.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private IBatchConfService iBatchConfService;

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private RoleService roleService;

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
            ColumnJson columnJson = new ColumnJson();
            columnJson.setTableName("t_person_batch_score_record");
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
        CompanyInfo companyInfo = iCompanyInfoService.findById(identityInfo.getId());
        List<Indicator> indicators = iIndicatorService.findAll();
        for (Indicator indicator : indicators) {
            ScoreRecord record = new ScoreRecord();
            record.setBatchId(batchId);
            record.setAcceptDate(new Date());
            record.setIndicatorId(indicator.getId());
            record.setIndicatorName(indicator.getName());
            record.setcTime(new Date());
            record.setPersonName(identityInfo.getName());
            record.setPersonId(identityInfo.getId());
            record.setPersonIdNum(identityInfo.getIdNumber());
            record.setCompanyId(companyInfo.getId());
            record.setCompanyName(companyInfo.getCompanyName());
            List<Integer> materialList = iIndicatorService.selectBindMaterialIds(indicator.getId());
            if (materialList.size() > 0) {
                record.setStatus(2);
            } else {
                record.setStatus(1);
            }
            List<Integer> roleList = iIndicatorService.selectBindDepartmentIds(indicator.getId());
            for (Integer roleId : roleList) {
                Role role = roleService.findRoleById(roleId);
                if (role == null) continue;
                record.setId(null);
                record.setOpRoleId(roleId);
                record.setOpRole(role.getRoleName());
                save(record);
            }

        }
    }
}

