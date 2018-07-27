package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.Luohu2;

import java.util.List;

/**
* Created by chenJz1012 on 2018-07-26.
*/
public interface ILuohu2Service extends IService<Luohu2> {

    PageInfo<Luohu2> selectByFilterAndPage(Luohu2 luohu2, int pageNum, int pageSize);

    List<Luohu2> selectByFilter(Luohu2 luohu2);
}
