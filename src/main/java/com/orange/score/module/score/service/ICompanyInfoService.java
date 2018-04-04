package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.CompanyInfo;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-04.
*/
public interface ICompanyInfoService extends IService<CompanyInfo> {

    PageInfo<CompanyInfo> selectByFilterAndPage(CompanyInfo companyInfo, int pageNum, int pageSize);

    List<CompanyInfo> selectByFilter(CompanyInfo companyInfo);
}
