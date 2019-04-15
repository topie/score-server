package com.orange.score.module.score.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.BaseService;
import com.orange.score.database.score.dao.FakeRecordCompanyMapper;
import com.orange.score.database.score.dao.FakeRecordMapper;
import com.orange.score.database.score.model.FakeRecord;
import com.orange.score.database.score.model.FakeRecordCompany;
import com.orange.score.module.score.service.IFakeRecordCompanyService;
import com.orange.score.module.score.service.IFakeRecordService;
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
public class FakeRecordCompanyServiceImpl extends BaseService<FakeRecordCompany> implements IFakeRecordCompanyService {

    @Autowired
    private FakeRecordCompanyMapper fakeRecordCompanyMapper;

    @Override
    public PageInfo<FakeRecordCompany> selectByFilterAndPage(FakeRecordCompany fakeRecordCompany, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FakeRecordCompany> list = selectByFilter(fakeRecordCompany);
        return new PageInfo<>(list);
    }

    @Override
    public List<FakeRecordCompany> selectByFilter(FakeRecordCompany fakeRecordCompany) {
        Condition condition = new Condition(FakeRecordCompany.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (fakeRecordCompany.getCompanyName() != "" && fakeRecordCompany.getCompanyName() != null){
            criteria.andLike("companyName","%"+fakeRecordCompany.getCompanyName()+"%");
        }
        return fakeRecordCompanyMapper.selectByCondition(condition);
    }
}

