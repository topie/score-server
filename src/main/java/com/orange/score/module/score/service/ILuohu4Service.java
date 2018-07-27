package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.Luohu4;

import java.util.List;

/**
* Created by chenJz1012 on 2018-07-26.
*/
public interface ILuohu4Service extends IService<Luohu4> {

    PageInfo<Luohu4> selectByFilterAndPage(Luohu4 luohu4, int pageNum, int pageSize);

    List<Luohu4> selectByFilter(Luohu4 luohu4);
}
