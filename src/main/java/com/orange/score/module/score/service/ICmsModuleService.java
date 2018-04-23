package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.CmsModule;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-21.
*/
public interface ICmsModuleService extends IService<CmsModule> {

    PageInfo<CmsModule> selectByFilterAndPage(CmsModule cmsModule, int pageNum, int pageSize);

    List<CmsModule> selectByFilter(CmsModule cmsModule);
}
