package com.orange.score.database.score.dao;

import com.orange.score.common.core.Mapper;
import com.orange.score.database.score.model.ScoreResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreResultMapper extends Mapper<ScoreResult> {

    List<ScoreResult> selectRankByBatchId(@Param("batchId") Integer batchId);
}
