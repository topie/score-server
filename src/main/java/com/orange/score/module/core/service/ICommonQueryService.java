package com.orange.score.module.core.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.core.model.CommonQuery;
import com.orange.score.module.core.dto.CommonQueryDto;

import java.io.IOException;
import java.util.List;

/**
 * Created by chenguojun on 2017/4/19.
 */
public interface ICommonQueryService extends IService<CommonQuery> {

    PageInfo<CommonQuery> selectByFilterAndPage(CommonQuery commonQuery, int pageNum, int pageSize);

    List<CommonQuery> selectByFilter(CommonQuery commonQuery);

    void export(CommonQueryDto commonQueryDto, String path) throws IOException;

    List selectFormItemsByTable(String table);

    List selectSearchItemsByTable(String table);
}
