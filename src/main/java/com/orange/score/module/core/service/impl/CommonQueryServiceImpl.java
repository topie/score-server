package com.orange.score.module.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.BaseService;
import com.orange.score.common.tools.excel.ExcelFileUtil;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.CamelUtil;
import com.orange.score.common.utils.Option;
import com.orange.score.database.core.dao.CommonQueryMapper;
import com.orange.score.database.core.model.ColumnJson;
import com.orange.score.database.core.model.CommonQuery;
import com.orange.score.module.core.dto.CommonQueryDto;
import com.orange.score.module.core.service.IColumnJsonService;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.ICommonService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Service
public class CommonQueryServiceImpl extends BaseService<CommonQuery> implements ICommonQueryService {

    @Autowired
    private ICommonService iCommonService;

    @Autowired
    private CommonQueryMapper commonQueryMapper;

    @Autowired
    private IColumnJsonService iColumnJsonService;

    @Override
    public PageInfo<CommonQuery> selectByFilterAndPage(CommonQuery commonQuery, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommonQuery> list = selectByFilter(commonQuery);
        return new PageInfo<>(list);
    }

    @Override
    public List<CommonQuery> selectByFilter(CommonQuery commonQuery) {
        Condition condition = new Condition(CommonQuery.class);
        Example.Criteria criteria = condition.createCriteria();
        if (StringUtils.isNotEmpty(commonQuery.getTableAlias()))
            criteria.andEqualTo("tableAlias", commonQuery.getTableAlias());
        return this.findByCondition(condition);
    }

    @Override
    public void export(CommonQueryDto commonQueryDto, String path) throws IOException {
        CommonQuery c = new CommonQuery();
        c.setTableAlias(commonQueryDto.getTableAlias());
        List<CommonQuery> commonQueries = selectByFilter(c);
        if (CollectionUtils.isNotEmpty(commonQueries)) {
            List<Map> list = iCommonService.selectByCommonTableBySql(commonQueryDto.getSql());
            List<String> headers = new ArrayList();
            List<String> mapHeaders = new ArrayList();
            if (StringUtils.isNotEmpty(commonQueries.get(0).getExportQuery())) {
                String[] exportArr = commonQueries.get(0).getExportQuery().split(",");
                for (String export : exportArr) {
                    String[] exportEle = export.split("::");
                    if (exportEle.length == 2) {
                        headers.add(exportEle[0].trim());
                        mapHeaders.add(exportEle[1].trim());
                    }
                }
            }
            String[] headArr = new String[headers.size()];
            String[] h = headers.toArray(headArr);
            String[] mapHeadArr = new String[mapHeaders.size()];
            String[] m = mapHeaders.toArray(mapHeadArr);
            ExcelFileUtil.exportXlsx(path, list, m, h);
        }

    }

    @Override
    public List selectFormItemsByTable(String table) {
        List items = new ArrayList<>();
        List<Map> list = new ArrayList<>();
        ColumnJson columnJson = new ColumnJson();
        columnJson.setTableName(table);
        List<ColumnJson> columnJsons = iColumnJsonService.selectByFilter(columnJson);
        if (columnJsons.size() > 0) {
            columnJson = columnJsons.get(0);
            JSONArray jsonArray = JSONArray.parseArray(columnJson.getColumnJson());
            if (columnJson.getType() == 0) {
                for (Object o : jsonArray) {
                    items.add(o);
                }
                return items;
            }
        } else {
            list = commonQueryMapper.selectColumnsByTable(table);
        }
        for (Map map : list) {
            String name = (String) map.get("dataColumn");
            String comment = (String) map.get("dataComment");
            String[] cs = comment.split(":");
            if (cs.length > 2 && "skip".equals(cs[1])) continue;
            FormItem formItem = new FormItem();
            formItem.setName(CamelUtil.underlineToCamel(name));
            formItem.setId(CamelUtil.underlineToCamel(name));
            formItem.setLabel(cs[0]);
            formItem.setType("text");
            if (cs.length == 2) {
                formItem.setType(cs[1]);
                if ("date".equals(cs[1])) {
                    formItem.setType("datepicker");
                    Map config = new HashMap();
                    config.put("timePicker", false);
                    config.put("singleDatePicker", true);
                    Map locale = new HashMap();
                    locale.put("format", "YYYY-MM-DD");
                    config.put("locale", locale);
                    formItem.setConfig(config);
                } else if ("datetime".equals(cs[1])) {
                    formItem.setType("datepicker");
                    Map config = new HashMap();
                    config.put("timePicker", true);
                    config.put("singleDatePicker", true);
                    Map locale = new HashMap();
                    locale.put("format", "YYYY-MM-DD HH:mm:ss");
                    config.put("locale", locale);
                    formItem.setConfig(config);
                } else {
                    formItem.setType(cs[1]);
                    if ("code".equals(cs[1])) {
                        formItem.setType("textarea");
                        formItem.setCode(true);
                    }
                }
            }
            if (cs.length == 3) {
                formItem.setType(cs[1]);
                List<Option> options = new ArrayList<>();
                String[] arr = cs[2].split("[,\\]\\[]");
                for (String option : arr) {
                    if (StringUtils.isNotEmpty(option)) {
                        if (option.contains("#")) {
                            String[] sArr = option.split("#");
                            options.add(new Option(sArr[0], sArr[1]));
                        } else {
                            options.add(new Option(option, option));
                        }

                    }
                }
                formItem.setItems(options);
            }
            items.add(formItem);
        }
        return items;
    }

    @Override
    public List selectSearchItemsByTable(String table) {
        List items = new ArrayList<>();
        ColumnJson columnJson = new ColumnJson();
        columnJson.setTableName(table);
        List<ColumnJson> columnJsons = iColumnJsonService.selectByFilter(columnJson);
        if (columnJsons.size() > 0) {
            columnJson = columnJsons.get(0);
            if (StringUtils.isNotEmpty(columnJson.getSearchConf())) {
                JSONArray jsonArray = JSONArray.parseArray(columnJson.getSearchConf());
                items.addAll(jsonArray);
            }
            return items;
        }
        return null;
    }

}
