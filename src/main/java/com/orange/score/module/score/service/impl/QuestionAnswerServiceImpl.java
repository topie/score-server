package com.orange.score.module.score.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.SqlUtil;
import com.orange.score.database.score.dao.QuestionAnswerMapper;
import com.orange.score.database.score.model.QuestionAnswer;
import com.orange.score.module.score.service.IQuestionAnswerService;
import com.orange.score.common.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import com.orange.score.common.utils.MethodUtil;
import com.orange.score.common.utils.SearchItem;
import com.orange.score.common.utils.SearchUtil;
import com.orange.score.database.core.model.ColumnJson;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.orange.score.module.core.service.IColumnJsonService;
import org.apache.commons.lang3.StringUtils;



/**
 * Created by chenJz1012 on 2018-04-21.
 */
@Service
@Transactional
public class QuestionAnswerServiceImpl extends BaseService<QuestionAnswer> implements IQuestionAnswerService {

    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;

    @Autowired
    private IColumnJsonService iColumnJsonService;

    @Override
    public PageInfo<QuestionAnswer> selectByFilterAndPage(QuestionAnswer questionAnswer, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<QuestionAnswer> list = selectByFilter(questionAnswer);
        return new PageInfo<>(list);
    }

    @Override
    public List<QuestionAnswer> selectByFilter(QuestionAnswer questionAnswer) {
        Page<QuestionAnswer> tmp = SqlUtil.getLocalPage();
        SqlUtil.clearLocalPage();
        Condition condition = new Condition(QuestionAnswer.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (questionAnswer != null) {
            ColumnJson columnJson = new ColumnJson();
            columnJson.setTableName("t_common_question");
            List<ColumnJson> list = iColumnJsonService.selectByFilter(columnJson);
            if (list.size() > 0) {
                List<SearchItem> searchItems = new ArrayList<>();
                columnJson = list.get(0);
                JSONArray jsonArray = JSONArray.parseArray(columnJson.getSearchConf());
                if(StringUtils.isNotEmpty(columnJson.getSearchConf())){
                    for (Object o : jsonArray) {
                        o = (JSONObject) o;
                        SearchItem searchItem = new SearchItem();
                        searchItem.setLabel(((JSONObject) o).getString("label"));
                        searchItem.setName(((JSONObject) o).getString("name"));
                        searchItem.setType(((JSONObject) o).getString("type"));
                        searchItem.setSearchType(((JSONObject) o).getString("searchType"));
                        if (StringUtils.isNotEmpty(searchItem.getName())) {
                            Object value = MethodUtil.invokeGet(questionAnswer, searchItem.getName());
                            if (value != null) {
                                if (value instanceof String) {
                                    if (StringUtils.isNotEmpty((String) value)) searchItem.setValue(value);
                                } else {
                                    searchItem.setValue(value);
                                }
                            }
                        }
                        searchItems.add(searchItem);
                    }
                }
                SearchUtil.convert(criteria, searchItems);
            }
        }
        if (tmp != null) SqlUtil.setLocalPage(tmp);
        return questionAnswerMapper.selectByCondition(condition);
    }
}

