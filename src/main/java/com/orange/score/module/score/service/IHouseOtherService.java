package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.HouseOther;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-10.
*/
public interface IHouseOtherService extends IService<HouseOther> {

    PageInfo<HouseOther> selectByFilterAndPage(HouseOther houseOther, int pageNum, int pageSize);

    List<HouseOther> selectByFilter(HouseOther houseOther);
}
