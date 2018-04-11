package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.MaterialTitle;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-11.
*/
public interface IMaterialTitleService extends IService<MaterialTitle> {

    PageInfo<MaterialTitle> selectByFilterAndPage(MaterialTitle materialTitle, int pageNum, int pageSize);

    List<MaterialTitle> selectByFilter(MaterialTitle materialTitle);
}
