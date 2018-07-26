package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.Luohu;
import java.util.List;

/**
* Created by chenJz1012 on 2018-07-26.
*/
public interface ILuohuService extends IService<Luohu> {

    PageInfo<Luohu> selectByFilterAndPage(Luohu luohu, int pageNum, int pageSize);

    List<Luohu> selectByFilter(Luohu luohu);
}
