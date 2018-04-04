package com.orange.score.module.score.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.BaseService;
import com.orange.score.database.score.dao.CompanyInfoMapper;
import com.orange.score.database.score.model.CompanyInfo;
import com.orange.score.module.score.service.ICompanyInfoService;
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
public class CompanyInfoServiceImpl extends BaseService<CompanyInfo> implements ICompanyInfoService {

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Override
    public PageInfo<CompanyInfo> selectByFilterAndPage(CompanyInfo companyInfo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CompanyInfo> list = selectByFilter(companyInfo);
        return new PageInfo<>(list);
    }

    @Override
    public List<CompanyInfo> selectByFilter(CompanyInfo companyInfo) {
        Condition condition = new Condition(CompanyInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (StringUtils.isNotEmpty(companyInfo.getCompanyName())) {
            criteria.andLike("companyName", "%" + companyInfo.getCompanyName() + "%");
        }
        return companyInfoMapper.selectByCondition(condition);
    }
}

