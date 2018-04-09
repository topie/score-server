package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.BasicConf;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-08.
*/
public interface IBasicConfService extends IService<BasicConf> {

    PageInfo<BasicConf> selectByFilterAndPage(BasicConf basicConf, int pageNum, int pageSize);

    List<BasicConf> selectByFilter(BasicConf basicConf);
}
