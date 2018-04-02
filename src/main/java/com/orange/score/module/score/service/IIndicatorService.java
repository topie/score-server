package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.Indicator;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-02.
*/
public interface IIndicatorService extends IService<Indicator> {

    PageInfo<Indicator> selectByFilterAndPage(Indicator indicator, int pageNum, int pageSize);

    List<Indicator> selectByFilter(Indicator indicator);
}
