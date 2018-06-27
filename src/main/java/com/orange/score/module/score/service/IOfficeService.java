package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.Office;
import java.util.List;

/**
* Created by chenJz1012 on 2018-06-26.
*/
public interface IOfficeService extends IService<Office> {

    PageInfo<Office> selectByFilterAndPage(Office office, int pageNum, int pageSize);

    List<Office> selectByFilter(Office office);
}
