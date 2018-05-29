package com.orange.score.database.score.dao;

import com.orange.score.common.core.Mapper;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.module.score.dto.SearchDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IdentityInfoMapper extends Mapper<IdentityInfo> {

    List<IdentityInfo> selectByCompany(@Param("search") SearchDto searchDto);
}
