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
import com.orange.score.database.core.model.Dict;
import com.orange.score.database.score.dao.PersonBatchStatusRecordMapper;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.database.score.model.PersonBatchStatusRecord;
import com.orange.score.module.core.service.IColumnJsonService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.IIdentityInfoService;
import com.orange.score.module.score.service.IPersonBatchStatusRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenJz1012 on 2018-04-14.
 */
@Service
@Transactional
public class PersonBatchStatusRecordServiceImpl extends BaseService<PersonBatchStatusRecord>
        implements IPersonBatchStatusRecordService {

    @Autowired
    private PersonBatchStatusRecordMapper personBatchStatusRecordMapper;

    @Autowired
    private IColumnJsonService iColumnJsonService;

    @Autowired
    private IDictService iDictService;

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Override
    public PageInfo<PersonBatchStatusRecord> selectByFilterAndPage(PersonBatchStatusRecord personBatchStatusRecord,
            int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PersonBatchStatusRecord> list = selectByFilter(personBatchStatusRecord);
        return new PageInfo<>(list);
    }

    @Override
    public List<PersonBatchStatusRecord> selectByFilter(PersonBatchStatusRecord personBatchStatusRecord) {
        Page<PersonBatchStatusRecord> tmp = SqlUtil.getLocalPage();
        SqlUtil.clearLocalPage();
        Condition condition = new Condition(PersonBatchStatusRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (personBatchStatusRecord != null) {
            ColumnJson columnJson = new ColumnJson();
            columnJson.setTableName("t_person_batch_status_record");
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
                            Object value = MethodUtil.invokeGet(personBatchStatusRecord, searchItem.getName());
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
        return personBatchStatusRecordMapper.selectByCondition(condition);
    }

    @Override
    public void insertStatus(Integer batchId, Integer personId, String alias, Integer status) {
        PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
        personBatchStatusRecord.setBatchId(batchId);
        personBatchStatusRecord.setPersonId(personId);
        IdentityInfo identityInfo = iIdentityInfoService.findById(personId);
        if (identityInfo != null) {
            personBatchStatusRecord.setPersonIdNumber(identityInfo.getIdNumber());
        }
        personBatchStatusRecord.setStatusDictAlias(alias);
        personBatchStatusRecord.setStatusInt(status);
        personBatchStatusRecord.setStatusTime(new Date());
        Dict dict = new Dict();
        dict.setAlias(alias);
        dict.setValue(status);
        List<Dict> dicts = iDictService.selectByFilter(dict);
        if (dicts.size() > 0) {
            personBatchStatusRecord.setStatusStr(dicts.get(0).getText());
            personBatchStatusRecord.setStatusTypeDesc(dicts.get(0).getAliasName());
        }
        save(personBatchStatusRecord);
    }
}

