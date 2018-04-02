package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.IndicatorItem;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-02.
*/
public interface IIndicatorItemService extends IService<IndicatorItem> {

    PageInfo<IndicatorItem> selectByFilterAndPage(IndicatorItem indicatorItem, int pageNum, int pageSize);

    List<IndicatorItem> selectByFilter(IndicatorItem indicatorItem);
}
