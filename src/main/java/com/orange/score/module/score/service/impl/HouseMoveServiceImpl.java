package com.orange.score.module.score.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.database.score.dao.HouseMoveMapper;
import com.orange.score.database.score.model.HouseMove;
import com.orange.score.module.score.service.IHouseMoveService;
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
public class HouseMoveServiceImpl extends BaseService<HouseMove> implements IHouseMoveService {

    @Autowired
    private HouseMoveMapper houseMoveMapper;

    @Override
    public PageInfo<HouseMove> selectByFilterAndPage(HouseMove houseMove, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<HouseMove> list = selectByFilter(houseMove);
        return new PageInfo<>(list);
    }

    @Override
    public List<HouseMove> selectByFilter(HouseMove houseMove) {
        Condition condition = new Condition(HouseMove.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        return houseMoveMapper.selectByCondition(condition);
    }
}

