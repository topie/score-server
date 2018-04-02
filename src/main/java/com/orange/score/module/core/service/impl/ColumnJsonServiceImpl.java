package com.orange.score.module.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.BaseService;
import com.orange.score.database.core.dao.ColumnJsonMapper;
import com.orange.score.database.core.model.ColumnJson;
import com.orange.score.module.core.service.IColumnJsonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Created by chenJz1012 on 2018-04-02.
 */
@Service
@Transactional
public class ColumnJsonServiceImpl extends BaseService<ColumnJson> implements IColumnJsonService {

    @Autowired
    private ColumnJsonMapper columnJsonMapper;

    @Override
    public PageInfo<ColumnJson> selectByFilterAndPage(ColumnJson columnJson, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ColumnJson> list = selectByFilter(columnJson);
        return new PageInfo<>(list);
    }

    @Override
    public List<ColumnJson> selectByFilter(ColumnJson columnJson) {
        Condition condition = new Condition(ColumnJson.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (StringUtils.isNotEmpty(columnJson.getTableName())) {
            criteria.andEqualTo("tableName", columnJson.getTableName());
        }
        return columnJsonMapper.selectByCondition(condition);
    }
}

