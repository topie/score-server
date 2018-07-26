package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.ScoreRecord;
import tk.mybatis.mapper.entity.Condition;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-16.
 */
public interface IScoreRecordService extends IService<ScoreRecord> {

    PageInfo<ScoreRecord> selectByFilterAndPage(ScoreRecord scoreRecord, int pageNum, int pageSize);

    PageInfo<ScoreRecord> selectByFilterAndPage(Condition condition, int pageNum, int pageSize);

    List<ScoreRecord> selectByFilter(ScoreRecord scoreRecord);

    List<ScoreRecord> selectByFilter(Condition condition);

    void insertToInitRecords(Integer batchId, Integer personId);

    void insertToReInitRecords(Integer batchId, Integer personId, Integer indicatorId);

    void insertToAppendInitRecords(Integer batchId, Integer personId);

    void insertToGetScoreResult(Integer batchId, Integer personId);

    void updateToScore(Integer batchId, Integer personId, Integer indicatorId,Integer roleId, Integer itemId, BigDecimal scoreValue);

    ScoreRecord selectOne(ScoreRecord scoreRecord);

    PageInfo<ScoreRecord> selectIdentityInfoByPage(Map argMap, int pageNum, int pageSize);

    List<ScoreRecord> selectIndicatorIdsByIdentityInfoIdAndRoleIds(Integer identityInfoId, List<Integer> roles);

    List<ScoreRecord> selectIndicatorIdsByIdentityInfoIdAndRoleIds(Integer identityInfoId,Integer indicatorId, List<Integer> roles);

}
