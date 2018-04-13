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
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.core.model.ColumnJson;
import com.orange.score.database.core.model.Region;
import com.orange.score.database.score.dao.MaterialInfoMapper;
import com.orange.score.database.score.model.MaterialInfo;
import com.orange.score.module.core.service.IColumnJsonService;
import com.orange.score.module.score.service.IMaterialInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenJz1012 on 2018-04-11.
 */
@Service
@Transactional
public class MaterialInfoServiceImpl extends BaseService<MaterialInfo> implements IMaterialInfoService {

    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    @Autowired
    private IColumnJsonService iColumnJsonService;

    @Override
    public PageInfo<MaterialInfo> selectByFilterAndPage(MaterialInfo materialInfo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MaterialInfo> list = selectByFilter(materialInfo);
        return new PageInfo<>(list);
    }

    @Override
    public List<MaterialInfo> selectByFilter(MaterialInfo materialInfo) {
        Page<Region> tmp = SqlUtil.getLocalPage();
        SqlUtil.clearLocalPage();
        Condition condition = new Condition(MaterialInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (materialInfo != null) {
            if (materialInfo.getTitleId() != null) {
                criteria.andEqualTo("titleId", materialInfo.getTitleId());
            }
            ColumnJson columnJson = new ColumnJson();
            columnJson.setTableName("t_material_info");
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
                        Object value = MethodUtil.invokeGet(materialInfo, searchItem.getName());
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
        return materialInfoMapper.selectByCondition(condition);
    }

    @Override
    public List<TreeNode> selectMaterialTreeNodes() {
        return materialInfoMapper.selectMaterialTreeNodes();
    }
}

