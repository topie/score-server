package com.orange.score.module.score.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.database.score.dao.HouseOtherMapper;
import com.orange.score.database.score.model.HouseOther;
import com.orange.score.module.score.service.IHouseOtherService;
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
import java.util.List;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.orange.score.module.core.service.IColumnJsonService;



/**
 * Created by chenJz1012 on 2018-04-10.
 */
@Service
@Transactional
public class HouseOtherServiceImpl extends BaseService<HouseOther> implements IHouseOtherService {

    @Autowired
    private HouseOtherMapper houseOtherMapper;

    @Autowired
    private IColumnJsonService iColumnJsonService;

    @Override
    public PageInfo<HouseOther> selectByFilterAndPage(HouseOther houseOther, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<HouseOther> list = selectByFilter(houseOther);
        return new PageInfo<>(list);
    }

    @Override
    public List<HouseOther> selectByFilter(HouseOther houseOther) {
        Condition condition = new Condition(HouseOther.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (houseOther.getIdentityInfoId() != null) {
            criteria.andEqualTo("identityInfoId", houseOther.getIdentityInfoId());
        }
        if (houseOther != null) {
            ColumnJson columnJson = new ColumnJson();
            columnJson.setTableName("t_house_other");
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
                        Object value = MethodUtil.invokeGet(houseOther, searchItem.getName());
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
        return houseOtherMapper.selectByCondition(condition);
    }
}

