package com.orange.score.module.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.SqlUtil;
import com.orange.score.common.core.BaseService;
import com.orange.score.common.utils.MethodUtil;
import com.orange.score.common.utils.Option;
import com.orange.score.common.utils.SearchItem;
import com.orange.score.common.utils.SearchUtil;
import com.orange.score.database.core.dao.DictMapper;
import com.orange.score.database.core.model.ColumnJson;
import com.orange.score.database.core.model.Dict;
import com.orange.score.database.core.model.Region;
import com.orange.score.module.core.service.IColumnJsonService;
import com.orange.score.module.core.service.IDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-08.
 */
@Service
@Transactional
public class DictServiceImpl extends BaseService<Dict> implements IDictService {

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private IColumnJsonService iColumnJsonService;

    @Override
    public PageInfo<Dict> selectByFilterAndPage(Dict dict, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Dict> list = selectByFilter(dict);
        return new PageInfo<>(list);
    }

    @Override
    public List<Dict> selectByFilter(Dict dict) {
        Page<Region> tmp = SqlUtil.getLocalPage();
        SqlUtil.clearLocalPage();
        Condition condition = new Condition(Dict.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (dict != null) {
            ColumnJson columnJson = new ColumnJson();
            columnJson.setTableName("d_dict");
            List<ColumnJson> list = iColumnJsonService.selectByFilter(columnJson);
            if (list.size() > 0) {
                List<SearchItem> searchItems = new ArrayList<>();
                columnJson = list.get(0);
                JSONArray jsonArray = JSONArray.parseArray(columnJson.getSearchConf());
                for (Object o : jsonArray) {
                    o = (JSONObject) o;
                    SearchItem searchItem = new SearchItem();
                    searchItem.setLabel(((JSONObject) o).getString("label"));
                    searchItem.setName(((JSONObject) o).getString("name"));
                    searchItem.setType(((JSONObject) o).getString("type"));
                    searchItem.setSearchType(((JSONObject) o).getString("searchType"));
                    if (StringUtils.isNotEmpty(searchItem.getName())) {
                        Object value = MethodUtil.invokeGet(dict, searchItem.getName());
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
            if (StringUtils.isNotEmpty(dict.getAlias())) {
                criteria.andEqualTo("alias", dict.getAlias());
            }
        }
        if (tmp != null) SqlUtil.setLocalPage(tmp);
        condition.orderBy("alias").asc().orderBy("sort").asc();
        return dictMapper.selectByCondition(condition);
    }

    @Override
    public List<Option> selectOptionsByAlias(String alias) {
        List<Option> options = new ArrayList<>();
        Dict dict = new Dict();
        dict.setAlias(alias);
        List<Dict> list = selectByFilter(dict);
        for (Dict item : list) {
            options.add(new Option(item.getText(), item.getValue()));
        }
        return options;
    }

    @Override
    public Map selectMapByAlias(String alias) {
        Map map = new HashMap();
        Dict dict = new Dict();
        dict.setAlias(alias);
        List<Dict> list = selectByFilter(dict);
        for (Dict item : list) {
            map.put(item.getValue(), item.getText());
        }
        return map;
    }
}

