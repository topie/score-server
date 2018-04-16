package com.orange.score.database.core.dao;

import com.orange.score.common.core.Mapper;
import com.orange.score.common.utils.Option;
import com.orange.score.database.core.model.Dict;

import java.util.List;

public interface DictMapper extends Mapper<Dict> {

    List<Option> selectDistinctAliasOptions();

}
