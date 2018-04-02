package com.orange.score.database.score.dao;

import com.orange.score.common.core.Mapper;
import com.orange.score.database.score.model.Indicator;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IndicatorMapper extends Mapper<Indicator> {

    List<Integer> selectBindMaterialIds(@Param("id") Integer id);

    int insertBindMaterial(@Param("id") Integer id, @Param("mId") Integer mId);

    int deleteBindMaterial(@Param("id") Integer id);
}
