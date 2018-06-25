package com.orange.score.module.score.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.SqlUtil;
import com.orange.score.common.core.BaseService;
import com.orange.score.common.utils.MethodUtil;
import com.orange.score.common.utils.SearchItem;
import com.orange.score.common.utils.SearchUtil;
import com.orange.score.database.core.model.ColumnJson;
import com.orange.score.database.score.dao.ScoreResultMapper;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.database.score.model.Indicator;
import com.orange.score.database.score.model.ScoreRecord;
import com.orange.score.database.score.model.ScoreResult;
import com.orange.score.module.core.service.IColumnJsonService;
import com.orange.score.module.score.service.IIdentityInfoService;
import com.orange.score.module.score.service.IScoreRecordService;
import com.orange.score.module.score.service.IScoreResultService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by chenJz1012 on 2018-04-18.
 */
@Service
@Transactional
public class ScoreResultServiceImpl extends BaseService<ScoreResult> implements IScoreResultService {

    @Autowired
    private ScoreResultMapper scoreResultMapper;

    @Autowired
    private IColumnJsonService iColumnJsonService;

    @Autowired
    private IScoreRecordService iScoreRecordService;

    @Autowired
    private IScoreResultService iScoreResultService;

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Override
    public PageInfo<ScoreResult> selectByFilterAndPage(ScoreResult scoreResult, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScoreResult> list = selectByFilter(scoreResult);
        return new PageInfo<>(list);
    }

    @Override
    public List<ScoreResult> selectByFilter(ScoreResult scoreResult) {
        Page<ScoreResult> tmp = SqlUtil.getLocalPage();
        SqlUtil.clearLocalPage();
        Condition condition = new Condition(ScoreResult.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (scoreResult != null) {
            ColumnJson columnJson = new ColumnJson();
            columnJson.setTableName("t_pb_score_result");
            List<ColumnJson> list = iColumnJsonService.selectByFilter(columnJson);
            if (list.size() > 0) {
                List<SearchItem> searchItems = new ArrayList<>();
                columnJson = list.get(0);
                JSONArray jsonArray = JSONArray.parseArray(columnJson.getSearchConf());
                if (StringUtils.isNotEmpty(columnJson.getSearchConf())) {
                    for (Object o : jsonArray) {
                        o = (JSONObject) o;
                        SearchItem searchItem = new SearchItem();
                        searchItem.setLabel(((JSONObject) o).getString("label"));
                        searchItem.setName(((JSONObject) o).getString("name"));
                        searchItem.setType(((JSONObject) o).getString("type"));
                        searchItem.setSearchType(((JSONObject) o).getString("searchType"));
                        if (StringUtils.isNotEmpty(searchItem.getName())) {
                            Object value = MethodUtil.invokeGet(scoreResult, searchItem.getName());
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
        return scoreResultMapper.selectByCondition(condition);
    }

    @Override
    public void insertToCheckIdentity(Integer identityInfoId, Map<Integer, Integer> iMap,
            Map<Integer, Indicator> indicatorMap) {
        IdentityInfo identityInfo = iIdentityInfoService.findById(identityInfoId);
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("personId", identityInfo.getId());
        criteria.andEqualTo("batchId", identityInfo.getBatchId());
        criteria.andEqualTo("status", 4);
        List<ScoreRecord> list = iScoreRecordService.selectByFilter(condition);
        Map tmpMap = new LinkedHashMap();
        for (ScoreRecord scoreRecord : list) {
            if (tmpMap.get(scoreRecord.getIndicatorId()) == null) {
                tmpMap.put(scoreRecord.getIndicatorId(), new ArrayList());
            }
            List scoreList = (List) tmpMap.get(scoreRecord.getIndicatorId());
            scoreList.add(scoreRecord.getScoreValue() == null ? BigDecimal.ZERO : scoreRecord.getScoreValue());
        }
        Iterator iterator = tmpMap.entrySet().iterator();
        while (iterator.hasNext()) {
            BigDecimal finalValue = BigDecimal.ZERO;
            Map.Entry entry = (Map.Entry) iterator.next();
            Integer iId = (Integer) entry.getKey();
            Indicator indicator = indicatorMap.get(iId);
            List<BigDecimal> sList = (List<BigDecimal>) entry.getValue();
            if (sList.size() == 0) continue;
            Integer rule = iMap.get(iId);
            switch (rule) {
                case 0:
                    if (sList.size() > 0) finalValue = sList.get(0);
                    break;
                case 1:
                    for (BigDecimal bigDecimal : sList) {
                        if (bigDecimal == null) continue;
                        if (bigDecimal.floatValue() > finalValue.floatValue()) finalValue = bigDecimal;
                    }
                    break;
                case 2:
                    BigDecimal i = finalValue = sList.get(0);
                    for (BigDecimal bigDecimal : sList) {
                        if (bigDecimal.floatValue() != i.floatValue()) {
                            finalValue = BigDecimal.ZERO;
                        }
                    }
                    break;
                case 3:
                    for (BigDecimal bigDecimal : sList) {
                        finalValue = finalValue.add(bigDecimal);
                    }
                    break;
                default:
                    break;
            }
            ScoreResult scoreResult = new ScoreResult();
            scoreResult.setScoreValue(finalValue);
            scoreResult.setIndicatorId(indicator.getId());
            scoreResult.setIndicatorName(indicator.getName());
            scoreResult.setPersonId(identityInfo.getId());
            scoreResult.setPersonName(identityInfo.getName());
            scoreResult.setPersonIdNum(identityInfo.getIdNumber());
            scoreResult.setBatchId(identityInfo.getBatchId());
            scoreResult.setcTime(new Date());
            iScoreResultService.save(scoreResult);
            identityInfo.setResultStatus(1);
            iIdentityInfoService.update(identityInfo);
        }
    }

    @Override
    public PageInfo<ScoreResult> selectRankByBatchId(Integer batchId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScoreResult> list = selectRankByBatchId(batchId);
        return new PageInfo<>(list);
    }

    @Override
    public int selectCountByFilter(ScoreRecord scoreRecord) {
        return scoreResultMapper.selectCountByFilter(scoreRecord);
    }

    @Override
    public List<ScoreResult> selectRankByBatchId(Integer batchId) {
        return scoreResultMapper.selectRankByBatchId(batchId);
    }
}

