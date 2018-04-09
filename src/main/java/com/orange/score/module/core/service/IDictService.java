package com.orange.score.module.core.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.core.model.Dict;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-08.
*/
public interface IDictService extends IService<Dict> {

    PageInfo<Dict> selectByFilterAndPage(Dict dict, int pageNum, int pageSize);

    List<Dict> selectByFilter(Dict dict);
}
