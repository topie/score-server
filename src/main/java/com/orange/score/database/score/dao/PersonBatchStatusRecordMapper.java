package com.orange.score.database.score.dao;

import com.orange.score.common.core.Mapper;
import com.orange.score.database.score.model.PersonBatchStatusRecord;
import org.apache.ibatis.annotations.Param;

public interface PersonBatchStatusRecordMapper extends Mapper<PersonBatchStatusRecord> {
    PersonBatchStatusRecord getPassPreCheck(@Param("p")  PersonBatchStatusRecord personBatchStatusRecord);
}