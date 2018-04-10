package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.AcceptDateConf;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-10.
*/
public interface IAcceptDateConfService extends IService<AcceptDateConf> {

    PageInfo<AcceptDateConf> selectByFilterAndPage(AcceptDateConf acceptDateConf, int pageNum, int pageSize);

    List<AcceptDateConf> selectByFilter(AcceptDateConf acceptDateConf);
}
