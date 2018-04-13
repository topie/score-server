package ${basePackage}.module.${module}.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.SqlUtil;
import ${basePackage}.database.${module}.dao.${modelNameUpperCamel}Mapper;
import ${basePackage}.database.${module}.model.${modelNameUpperCamel};
import ${basePackage}.module.${module}.service.I${modelNameUpperCamel}Service;
import ${basePackage}.common.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import ${basePackage}.common.utils.MethodUtil;
import ${basePackage}.common.utils.SearchItem;
import ${basePackage}.common.utils.SearchUtil;
import ${basePackage}.database.core.model.ColumnJson;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ${basePackage}.module.core.service.IColumnJsonService;
import org.apache.commons.lang3.StringUtils;



/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${modelNameUpperCamel}ServiceImpl extends BaseService<${modelNameUpperCamel}> implements I${modelNameUpperCamel}Service {

    @Autowired
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

    @Autowired
    private IColumnJsonService iColumnJsonService;

    @Override
    public PageInfo<${modelNameUpperCamel}> selectByFilterAndPage(${modelNameUpperCamel} ${modelNameLowerCamel}, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<${modelNameUpperCamel}> list = selectByFilter(${modelNameLowerCamel});
        return new PageInfo<>(list);
    }

    @Override
    public List<${modelNameUpperCamel}> selectByFilter(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        Page<${modelNameUpperCamel}> tmp = SqlUtil.getLocalPage();
        SqlUtil.clearLocalPage();
        Condition condition = new Condition(${modelNameUpperCamel}.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (${modelNameLowerCamel} != null) {
            ColumnJson columnJson = new ColumnJson();
            columnJson.setTableName("${tableName}");
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
                            Object value = MethodUtil.invokeGet(${modelNameLowerCamel}, searchItem.getName());
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
        return ${modelNameLowerCamel}Mapper.selectByCondition(condition);
    }
}

