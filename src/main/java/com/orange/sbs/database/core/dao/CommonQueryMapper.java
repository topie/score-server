package com.orange.sbs.database.core.dao;

import com.orange.sbs.common.core.Mapper;
import com.orange.sbs.database.core.model.CommonQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CommonQueryMapper extends Mapper<CommonQuery> {

    List<Map> selectColumnsByTable(@Param("table") String table);
}
