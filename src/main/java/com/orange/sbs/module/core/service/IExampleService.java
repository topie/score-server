package com.orange.sbs.module.core.service;

import com.github.pagehelper.PageInfo;
import com.orange.sbs.common.core.IService;
import com.orange.sbs.database.core.model.Example;
import java.util.List;

/**
* Created by chenJz1012 on 2017/12/27.
*/
public interface IExampleService extends IService<Example> {

    PageInfo<Example> selectByFilterAndPage(Example example, int pageNum, int pageSize);

    List<Example> selectByFilter(Example example);
}
