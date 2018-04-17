package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.OnlinePersonMaterial;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-17.
*/
public interface IOnlinePersonMaterialService extends IService<OnlinePersonMaterial> {

    PageInfo<OnlinePersonMaterial> selectByFilterAndPage(OnlinePersonMaterial onlinePersonMaterial, int pageNum, int pageSize);

    List<OnlinePersonMaterial> selectByFilter(OnlinePersonMaterial onlinePersonMaterial);
}
