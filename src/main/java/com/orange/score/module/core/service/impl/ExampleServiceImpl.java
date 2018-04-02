package com.orange.score.module.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.database.core.dao.ExampleMapper;
import com.orange.score.database.core.model.Example;
import com.orange.score.module.core.service.IExampleService;
import com.orange.score.common.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;



/**
 * Created by chenJz1012 on 2017/12/27.
 */
@Service
@Transactional
public class ExampleServiceImpl extends BaseService<Example> implements IExampleService {

    @Autowired
    private ExampleMapper exampleMapper;

    @Override
    public PageInfo<Example> selectByFilterAndPage(Example example, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Example> list = selectByFilter(example);
        return new PageInfo<>(list);
    }

    @Override
    public List<Example> selectByFilter(Example example) {
        Condition condition = new Condition(Example.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        return exampleMapper.selectByCondition(condition);
    }
}

