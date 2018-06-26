package com.orange.score.database.score.dao;

import com.orange.score.common.core.Mapper;
import com.orange.score.database.score.model.BatchConf;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BatchConfMapper extends Mapper<BatchConf> {

    List<Map> selectMobilesByBatchId(@Param("batchId") Integer batchId);
}
