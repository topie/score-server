package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.MaterialAcceptRecord;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-19.
*/
public interface IMaterialAcceptRecordService extends IService<MaterialAcceptRecord> {

    PageInfo<MaterialAcceptRecord> selectByFilterAndPage(MaterialAcceptRecord materialAcceptRecord, int pageNum, int pageSize);

    List<MaterialAcceptRecord> selectByFilter(MaterialAcceptRecord materialAcceptRecord);
}
