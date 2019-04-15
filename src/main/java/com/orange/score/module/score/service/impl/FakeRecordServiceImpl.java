package com.orange.score.module.score.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.database.score.dao.FakeRecordMapper;
import com.orange.score.database.score.model.FakeRecord;
import com.orange.score.module.score.service.IFakeRecordService;
import com.orange.score.common.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;



/**
 * Created by chenJz1012 on 2018-04-04.
 */
@Service
@Transactional
public class FakeRecordServiceImpl extends BaseService<FakeRecord> implements IFakeRecordService {

    @Autowired
    private FakeRecordMapper fakeRecordMapper;

    @Override
    public PageInfo<FakeRecord> selectByFilterAndPage(FakeRecord fakeRecord, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FakeRecord> list = selectByFilter(fakeRecord);
        return new PageInfo<>(list);
    }

    @Override
    public List<FakeRecord> selectByFilter(FakeRecord fakeRecord) {
        Condition condition = new Condition(FakeRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (fakeRecord.getUserName()!="" && fakeRecord.getUserName() != null){
            criteria.andLike("userName", "%"+fakeRecord.getUserName()+"%");
        }
        return fakeRecordMapper.selectByCondition(condition);
    }
}

