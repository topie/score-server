package com.orange.score.database.score.dao;

import com.orange.score.common.core.Mapper;
import com.orange.score.database.score.model.ScoreRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ScoreRecordMapper extends Mapper<ScoreRecord> {

    List<ScoreRecord> selectIdentityInfo(@Param("item") Map argMap);

    List<ScoreRecord> selectIdentityInfo_1(@Param("item") Map argMap);

    List<ScoreRecord> selectIsPreviewed(@Param("item") Map argMap);

    List<ScoreRecord> selectIndicatorIdsByIdentityInfoIdAndRoleIds(@Param("identityInfoId") Integer identityInfoId, @Param("roles") List<Integer> roles);

    List<ScoreRecord> selectIndicatorIdsByIdentityInfoIdAndRoleIdsAndIndicatorId(@Param("identityInfoId") Integer identityInfoId, @Param("indicatorId") Integer indicatorId, @Param("roles") List<Integer> roles);

    List<ScoreRecord> provideDataToPolice(@Param("item") Map argMap);
}
