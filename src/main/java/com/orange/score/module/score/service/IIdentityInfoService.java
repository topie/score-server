package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.IdentityInfo;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-08.
*/
public interface IIdentityInfoService extends IService<IdentityInfo> {

    PageInfo<IdentityInfo> selectByFilterAndPage(IdentityInfo identityInfo, int pageNum, int pageSize);

    List<IdentityInfo> selectByFilter(IdentityInfo identityInfo);
}
