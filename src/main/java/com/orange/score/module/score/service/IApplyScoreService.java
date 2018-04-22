package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.ApplyScore;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Created by chenJz1012 on 2018-04-21.
 */
public interface IApplyScoreService extends IService<ApplyScore> {

    PageInfo<ApplyScore> selectByFilterAndPage(Condition condition, int pageNum, int pageSize);

    PageInfo<ApplyScore> selectByFilterAndPage(ApplyScore applyScore, int pageNum, int pageSize);

    List<ApplyScore> selectByFilter(ApplyScore applyScore);

    List<ApplyScore> selectByFilter(Condition condition);
}
