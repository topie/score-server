package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.HouseProfession;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-08.
*/
public interface IHouseProfessionService extends IService<HouseProfession> {

    PageInfo<HouseProfession> selectByFilterAndPage(HouseProfession houseProfession, int pageNum, int pageSize);

    List<HouseProfession> selectByFilter(HouseProfession houseProfession);
}
