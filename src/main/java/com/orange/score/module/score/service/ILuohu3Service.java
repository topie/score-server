package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.Luohu3;

import java.util.List;

/**
* Created by chenJz1012 on 2018-07-26.
*/
public interface ILuohu3Service extends IService<Luohu3> {

    PageInfo<Luohu3> selectByFilterAndPage(Luohu3 luohu3, int pageNum, int pageSize);

    List<Luohu3> selectByFilter(Luohu3 luohu3);
}
