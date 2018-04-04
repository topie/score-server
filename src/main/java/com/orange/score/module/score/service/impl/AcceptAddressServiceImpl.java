package com.orange.score.module.score.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.database.score.dao.AcceptAddressMapper;
import com.orange.score.database.score.model.AcceptAddress;
import com.orange.score.module.score.service.IAcceptAddressService;
import com.orange.score.common.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;



/**
 * Created by chenJz1012 on 2018-04-02.
 */
@Service
@Transactional
public class AcceptAddressServiceImpl extends BaseService<AcceptAddress> implements IAcceptAddressService {

    @Autowired
    private AcceptAddressMapper acceptAddressMapper;

    @Override
    public PageInfo<AcceptAddress> selectByFilterAndPage(AcceptAddress acceptAddress, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AcceptAddress> list = selectByFilter(acceptAddress);
        return new PageInfo<>(list);
    }

    @Override
    public List<AcceptAddress> selectByFilter(AcceptAddress acceptAddress) {
        Condition condition = new Condition(AcceptAddress.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        return acceptAddressMapper.selectByCondition(condition);
    }
}

