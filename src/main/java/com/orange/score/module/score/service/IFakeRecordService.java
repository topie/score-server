package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.FakeRecord;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-04.
*/
public interface IFakeRecordService extends IService<FakeRecord> {

    PageInfo<FakeRecord> selectByFilterAndPage(FakeRecord fakeRecord, int pageNum, int pageSize);

    List<FakeRecord> selectByFilter(FakeRecord fakeRecord);
}
