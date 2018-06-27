package com.orange.score.database.score.dao;

import com.orange.score.common.core.Mapper;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.module.score.dto.SearchDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IdentityInfoMapper extends Mapper<IdentityInfo> {

    List<IdentityInfo> selectByCompany(@Param("search") SearchDto searchDto);

    List<Map> selectExportList1(@Param("item") Map argMap);

    List<Map> selectExportList2(@Param("item") Map argMap);
}
