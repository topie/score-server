package com.orange.score.database.score.dao;

import com.orange.score.common.core.Mapper;
import com.orange.score.database.score.model.AcceptDateConf;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AcceptDateConfMapper extends Mapper<AcceptDateConf> {

    List<Date> selectDistinctDateList(@Param("p") AcceptDateConf acceptDateConf);

}
