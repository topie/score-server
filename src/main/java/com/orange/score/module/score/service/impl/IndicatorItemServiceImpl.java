package com.orange.score.module.score.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.BaseService;
import com.orange.score.database.score.dao.IndicatorItemMapper;
import com.orange.score.database.score.model.IndicatorItem;
import com.orange.score.module.score.service.IIndicatorItemService;
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
public class IndicatorItemServiceImpl extends BaseService<IndicatorItem> implements IIndicatorItemService {

    @Autowired
    private IndicatorItemMapper indicatorItemMapper;

    @Override
    public PageInfo<IndicatorItem> selectByFilterAndPage(IndicatorItem indicatorItem, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<IndicatorItem> list = selectByFilter(indicatorItem);
        return new PageInfo<>(list);
    }

    @Override
    public List<IndicatorItem> selectByFilter(IndicatorItem indicatorItem) {
        Condition condition = new Condition(IndicatorItem.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (indicatorItem.getIndicatorId() != null) criteria.andEqualTo("indicatorId", indicatorItem.getIndicatorId());
        return indicatorItemMapper.selectByCondition(condition);
    }
}

