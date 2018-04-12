package com.orange.score.module.core.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.core.model.Region;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-12.
*/
public interface IRegionService extends IService<Region> {

    PageInfo<Region> selectByFilterAndPage(Region region, int pageNum, int pageSize);

    List<Region> selectByFilter(Region region);
}
