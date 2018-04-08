package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.HouseRelationship;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-08.
*/
public interface IHouseRelationshipService extends IService<HouseRelationship> {

    PageInfo<HouseRelationship> selectByFilterAndPage(HouseRelationship houseRelationship, int pageNum, int pageSize);

    List<HouseRelationship> selectByFilter(HouseRelationship houseRelationship);
}
