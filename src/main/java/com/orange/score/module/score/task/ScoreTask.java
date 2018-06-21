package com.orange.score.module.score.task;

import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.database.score.model.BatchConf;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.database.score.model.Indicator;
import com.orange.score.module.score.service.IBatchConfService;
import com.orange.score.module.score.service.IIdentityInfoService;
import com.orange.score.module.score.service.IIndicatorService;
import com.orange.score.module.score.service.IScoreResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScoreTask {

    @Autowired
    private IIndicatorService iIndicatorService;

    @Autowired
    private IBatchConfService iBatchConfService;

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Autowired
    private IScoreResultService iScoreResultService;

    @Scheduled(cron = "3 0 0 * * ? ")
    public void batchStartTask() {
        Date today = DateUtil.getToday();
        Condition condition = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("status", 0);
        criteria.andGreaterThanOrEqualTo("applyBegin", today);
        List<BatchConf> list = iBatchConfService.findByCondition(condition);
        for (BatchConf batchConf : list) {
            batchConf.setStatus(1);
            batchConf.setProcess(1);
            iBatchConfService.update(batchConf);
        }
    }

    @Scheduled(cron = "5 0 0 * * ? ")
    public void checkStartTask() {
        Date today = DateUtil.getToday();
        Condition condition = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("status", 1);
        criteria.andGreaterThanOrEqualTo("publishBegin", today);
        List<BatchConf> list = iBatchConfService.findByCondition(condition);
        for (BatchConf batchConf : list) {
            List<Indicator> indicators = iIndicatorService.findAll();
            Map<Integer, Integer> iMap = new HashMap();
            Map<Integer, Indicator> indicatorMap = new HashMap();
            for (Indicator indicator : indicators) {
                iMap.put(indicator.getId(), indicator.getScoreRule());
                indicatorMap.put(indicator.getId(), indicator);
            }
            condition = new Condition(IdentityInfo.class);
            criteria = condition.createCriteria();
            criteria.andEqualTo("batchId", batchConf.getId());
            criteria.andEqualTo("hallStatus", 5);
            criteria.andEqualTo("reservationStatus",11);
            List<IdentityInfo> identityInfos = iIdentityInfoService.findByCondition(condition);
            for (IdentityInfo identityInfo : identityInfos) {
                iScoreResultService.insertToCheckIdentity(identityInfo.getId(), iMap, indicatorMap);
            }
            batchConf.setProcess(2);
            iBatchConfService.update(batchConf);
        }
    }

    @Scheduled(cron = "10 0 0 * * ? ")
    public void checkEndTask() {
        Date nextDay = DateUtil.addDay(DateUtil.getToday(), 1);
        Condition condition = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("process", 2);
        criteria.andLessThan("publishEnd", nextDay);
        List<BatchConf> list = iBatchConfService.findByCondition(condition);
        for (BatchConf batchConf : list) {
            batchConf.setProcess(3);
            iBatchConfService.update(batchConf);
        }
    }

    @Scheduled(cron = "15 0 0 * * ? ")
    public void listBeginTask() {
        Date today = DateUtil.getToday();
        Condition condition = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("process", 3);
        criteria.andGreaterThanOrEqualTo("noticeBegin", today);
        List<BatchConf> list = iBatchConfService.findByCondition(condition);
        for (BatchConf batchConf : list) {
            batchConf.setProcess(4);
            iBatchConfService.update(batchConf);
        }
    }

    @Scheduled(cron = "20 0 0 * * ? ")
    public void listEndTask() {
        Date nextDay = DateUtil.addDay(DateUtil.getToday(), 1);
        Condition condition = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("process", 4);
        criteria.andLessThan("noticeEnd", nextDay);
        List<BatchConf> list = iBatchConfService.findByCondition(condition);
        for (BatchConf batchConf : list) {
            batchConf.setProcess(5);
            iBatchConfService.update(batchConf);
        }
    }
}
