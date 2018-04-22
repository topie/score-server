package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.QuestionAnswer;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-21.
*/
public interface IQuestionAnswerService extends IService<QuestionAnswer> {

    PageInfo<QuestionAnswer> selectByFilterAndPage(QuestionAnswer questionAnswer, int pageNum, int pageSize);

    List<QuestionAnswer> selectByFilter(QuestionAnswer questionAnswer);
}
