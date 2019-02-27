package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.FakeRecord;
import com.orange.score.database.score.model.FakeRecordCompany;

import java.util.List;

/**
* Created by chenJz1012 on 2018-04-04.
*/
public interface IFakeRecordCompanyService extends IService<FakeRecordCompany> {

    PageInfo<FakeRecordCompany> selectByFilterAndPage(FakeRecordCompany fakeRecordCompany, int pageNum, int pageSize);

    List<FakeRecordCompany> selectByFilter(FakeRecordCompany fakeRecordCompany);
}
