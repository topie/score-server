package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.BatchConf;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-04.
*/
public interface IBatchConfService extends IService<BatchConf> {

    PageInfo<BatchConf> selectByFilterAndPage(BatchConf batchConf, int pageNum, int pageSize);

    List<BatchConf> selectByFilter(BatchConf batchConf);
}
