package com.orange.score.module.score.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.BaseService;
import com.orange.score.database.score.dao.BatchConfMapper;
import com.orange.score.database.score.model.BatchConf;
import com.orange.score.module.score.service.IBatchConfService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Created by chenJz1012 on 2018-04-04.
 */
@Service
@Transactional
public class BatchConfServiceImpl extends BaseService<BatchConf> implements IBatchConfService {

    @Autowired
    private BatchConfMapper batchConfMapper;

    @Override
    public PageInfo<BatchConf> selectByFilterAndPage(BatchConf batchConf, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BatchConf> list = selectByFilter(batchConf);
        return new PageInfo<>(list);
    }

    @Override
    public List<BatchConf> selectByFilter(BatchConf batchConf) {
        Condition condition = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (batchConf!=null && StringUtils.isNotEmpty(batchConf.getBatchName())) {
            criteria.andLike("batchName", "%" + batchConf.getBatchName() + "%");
        }
        condition.orderBy("status").desc();
        return batchConfMapper.selectByCondition(condition);
    }
}

