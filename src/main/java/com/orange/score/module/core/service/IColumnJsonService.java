package com.orange.score.module.core.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.core.model.ColumnJson;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-12.
*/
public interface IColumnJsonService extends IService<ColumnJson> {

    PageInfo<ColumnJson> selectByFilterAndPage(ColumnJson columnJson, int pageNum, int pageSize);

    List<ColumnJson> selectByFilter(ColumnJson columnJson);
}
