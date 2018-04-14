package com.orange.score.module.score.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.SqlUtil;
import com.orange.score.database.score.dao.IndicatorJsonMapper;
import com.orange.score.database.score.model.IndicatorJson;
import com.orange.score.module.score.service.IIndicatorJsonService;
import com.orange.score.common.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import com.orange.score.common.utils.MethodUtil;
import com.orange.score.common.utils.SearchItem;
import com.orange.score.common.utils.SearchUtil;
import com.orange.score.database.core.model.ColumnJson;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.orange.score.module.core.service.IColumnJsonService;
import org.apache.commons.lang3.StringUtils;



/**
 * Created by chenJz1012 on 2018-04-14.
 */
@Service
@Transactional
public class IndicatorJsonServiceImpl extends BaseService<IndicatorJson> implements IIndicatorJsonService {

    @Autowired
    private IndicatorJsonMapper indicatorJsonMapper;

    @Autowired
    private IColumnJsonService iColumnJsonService;

    @Override
    public PageInfo<IndicatorJson> selectByFilterAndPage(IndicatorJson indicatorJson, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<IndicatorJson> list = selectByFilter(indicatorJson);
        return new PageInfo<>(list);
    }

    @Override
    public List<IndicatorJson> selectByFilter(IndicatorJson indicatorJson) {
        Page<IndicatorJson> tmp = SqlUtil.getLocalPage();
        SqlUtil.clearLocalPage();
        Condition condition = new Condition(IndicatorJson.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (indicatorJson != null) {
            ColumnJson columnJson = new ColumnJson();
            columnJson.setTableName("t_indicator_json");
            List<ColumnJson> list = iColumnJsonService.selectByFilter(columnJson);
            if (list.size() > 0) {
                List<SearchItem> searchItems = new ArrayList<>();
                columnJson = list.get(0);
                JSONArray jsonArray = JSONArray.parseArray(columnJson.getSearchConf());
                if(StringUtils.isNotEmpty(columnJson.getSearchConf())){
                    for (Object o : jsonArray) {
                        o = (JSONObject) o;
                        SearchItem searchItem = new SearchItem();
                        searchItem.setLabel(((JSONObject) o).getString("label"));
                        searchItem.setName(((JSONObject) o).getString("name"));
                        searchItem.setType(((JSONObject) o).getString("type"));
                        searchItem.setSearchType(((JSONObject) o).getString("searchType"));
                        if (StringUtils.isNotEmpty(searchItem.getName())) {
                            Object value = MethodUtil.invokeGet(indicatorJson, searchItem.getName());
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
        return indicatorJsonMapper.selectByCondition(condition);
    }
}

