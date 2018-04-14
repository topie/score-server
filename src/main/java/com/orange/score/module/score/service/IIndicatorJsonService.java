package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.IndicatorJson;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-14.
*/
public interface IIndicatorJsonService extends IService<IndicatorJson> {

    PageInfo<IndicatorJson> selectByFilterAndPage(IndicatorJson indicatorJson, int pageNum, int pageSize);

    List<IndicatorJson> selectByFilter(IndicatorJson indicatorJson);
}
