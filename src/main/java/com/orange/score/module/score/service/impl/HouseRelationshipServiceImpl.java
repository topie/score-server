package com.orange.score.module.score.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.database.score.dao.HouseRelationshipMapper;
import com.orange.score.database.score.model.HouseRelationship;
import com.orange.score.module.score.service.IHouseRelationshipService;
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
public class HouseRelationshipServiceImpl extends BaseService<HouseRelationship> implements IHouseRelationshipService {

    @Autowired
    private HouseRelationshipMapper houseRelationshipMapper;

    @Override
    public PageInfo<HouseRelationship> selectByFilterAndPage(HouseRelationship houseRelationship, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<HouseRelationship> list = selectByFilter(houseRelationship);
        return new PageInfo<>(list);
    }

    @Override
    public List<HouseRelationship> selectByFilter(HouseRelationship houseRelationship) {
        Condition condition = new Condition(HouseRelationship.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        return houseRelationshipMapper.selectByCondition(condition);
    }
}

