package com.orange.score.module.core.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.core.model.Log;
import java.util.List;

/**
* Created by chenJz1012 on 2018-08-19.
*/
public interface ILogService extends IService<Log> {

    PageInfo<Log> selectByFilterAndPage(Log log, int pageNum, int pageSize);

    List<Log> selectByFilter(Log log);
}
