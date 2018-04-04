package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.AcceptAddress;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-02.
*/
public interface IAcceptAddressService extends IService<AcceptAddress> {

    PageInfo<AcceptAddress> selectByFilterAndPage(AcceptAddress acceptAddress, int pageNum, int pageSize);

    List<AcceptAddress> selectByFilter(AcceptAddress acceptAddress);
}
