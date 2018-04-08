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



/**
 * Created by chenJz1012 on 2018-04-08.
 */
@Service
@Transactional
public class HouseOtherServiceImpl extends BaseService<HouseOther> implements IHouseOtherService {

    @Autowired
    private HouseOtherMapper houseOtherMapper;

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
        return houseOtherMapper.selectByCondition(condition);
    }
}

