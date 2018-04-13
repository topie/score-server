package com.orange.score.module.core.service.impl;

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
import com.orange.score.database.core.dao.ColumnJsonMapper;
import com.orange.score.database.core.model.ColumnJson;
import com.orange.score.database.core.model.Region;
import com.orange.score.module.core.service.IColumnJsonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenJz1012 on 2018-04-12.
 */
@Service
@Transactional
public class ColumnJsonServiceImpl extends BaseService<ColumnJson> implements IColumnJsonService {

    @Autowired
    private ColumnJsonMapper columnJsonMapper;

    @Autowired
    private IColumnJsonService iColumnJsonService;

    @Override
    public PageInfo<ColumnJson> selectByFilterAndPage(ColumnJson columnJson, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ColumnJson> list = selectByFilter(columnJson);
        return new PageInfo<>(list);
    }

    @Override
    public List<ColumnJson> selectByFilter(ColumnJson columnJson) {
        Page<Region> tmp = SqlUtil.getLocalPage();
        SqlUtil.clearLocalPage();
        Condition condition = new Condition(ColumnJson.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (columnJson != null) {
            ColumnJson item = new ColumnJson();
            item.setTableName("d_column_json");
            item = iColumnJsonService.findBy("tableName", "d_column_json");
            if (item != null) {
                List<SearchItem> searchItems = new ArrayList<>();
                JSONArray jsonArray = JSONArray.parseArray(item.getSearchConf());
                for (Object o : jsonArray) {
                    o = (JSONObject) o;
                    SearchItem searchItem = new SearchItem();
                    searchItem.setLabel(((JSONObject) o).getString("label"));
                    searchItem.setName(((JSONObject) o).getString("name"));
                    searchItem.setType(((JSONObject) o).getString("type"));
                    searchItem.setSearchType(((JSONObject) o).getString("searchType"));
                    if (StringUtils.isNotEmpty(searchItem.getName())) {
                        Object value = MethodUtil.invokeGet(columnJson, searchItem.getName());
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
                SearchUtil.convert(criteria, searchItems);
            }
        }
        if (tmp != null) SqlUtil.setLocalPage(tmp);
        return columnJsonMapper.selectByCondition(condition);
    }
}

