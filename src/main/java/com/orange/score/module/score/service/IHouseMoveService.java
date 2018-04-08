package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.HouseMove;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-08.
*/
public interface IHouseMoveService extends IService<HouseMove> {

    PageInfo<HouseMove> selectByFilterAndPage(HouseMove houseMove, int pageNum, int pageSize);

    List<HouseMove> selectByFilter(HouseMove houseMove);
}
