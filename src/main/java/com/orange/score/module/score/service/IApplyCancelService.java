package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.ApplyCancel;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
* Created by chenJz1012 on 2018-04-21.
*/
public interface IApplyCancelService extends IService<ApplyCancel> {

    PageInfo<ApplyCancel> selectByFilterAndPage(ApplyCancel applyCancel, int pageNum, int pageSize);

    List<ApplyCancel> selectByFilter(ApplyCancel applyCancel);

    PageInfo<ApplyCancel> selectByFilterAndPage(Condition condition, int pageNum, int pageSize);

    List<ApplyCancel> selectByFilter(Condition condition);
}
