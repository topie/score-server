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
import com.orange.score.database.score.dao.OnlinePersonMaterialMapper;
import com.orange.score.database.score.model.OnlinePersonMaterial;
import com.orange.score.module.core.service.IColumnJsonService;
import com.orange.score.module.score.service.IOnlinePersonMaterialService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenJz1012 on 2018-04-17.
 */
@Service
@Transactional
public class OnlinePersonMaterialServiceImpl extends BaseService<OnlinePersonMaterial>
        implements IOnlinePersonMaterialService {

    @Autowired
    private OnlinePersonMaterialMapper onlinePersonMaterialMapper;

    @Autowired
    private IColumnJsonService iColumnJsonService;

    @Override
    public PageInfo<OnlinePersonMaterial> selectByFilterAndPage(OnlinePersonMaterial onlinePersonMaterial, int pageNum,
            int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OnlinePersonMaterial> list = selectByFilter(onlinePersonMaterial);
        return new PageInfo<>(list);
    }

    @Override
    public List<OnlinePersonMaterial> selectByFilter(OnlinePersonMaterial onlinePersonMaterial) {
        Page<OnlinePersonMaterial> tmp = SqlUtil.getLocalPage();
        SqlUtil.clearLocalPage();
        Condition condition = new Condition(OnlinePersonMaterial.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (onlinePersonMaterial != null) {
            if (onlinePersonMaterial.getBatchId() != null) {
                criteria.andEqualTo("batchId", onlinePersonMaterial.getBatchId());
            }
            if (onlinePersonMaterial.getPersonId() != null) {
                criteria.andEqualTo("personId", onlinePersonMaterial.getPersonId());
            }
            ColumnJson columnJson = new ColumnJson();
            columnJson.setTableName("t_online_person_material");
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
                            Object value = MethodUtil.invokeGet(onlinePersonMaterial, searchItem.getName());
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
        return onlinePersonMaterialMapper.selectByCondition(condition);
    }
}

