package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.SmsSendConfig;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-16.
*/
public interface ISmsSendConfigService extends IService<SmsSendConfig> {

    PageInfo<SmsSendConfig> selectByFilterAndPage(SmsSendConfig smsSendConfig, int pageNum, int pageSize);

    List<SmsSendConfig> selectByFilter(SmsSendConfig smsSendConfig);
}
