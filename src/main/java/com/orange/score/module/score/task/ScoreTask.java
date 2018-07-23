package com.orange.score.module.score.task;

import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.database.score.model.BatchConf;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.database.score.model.Indicator;
import com.orange.score.module.score.service.*;
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

    @Autowired
    private IPersonBatchStatusRecordService iPersonBatchStatusRecordService;

    @Autowired
    private IHouseOtherService iHouseOtherService;

    @Scheduled(cron = "3 0 0 * * ? ")
    public void batchStartTask() {
        Date today = DateUtil.getToday();
        Condition condition = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("status", 0);
        criteria.andLessThanOrEqualTo("applyBegin", today);
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
        criteria.andLessThanOrEqualTo("publishBegin", today);
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
            criteria.andEqualTo("reservationStatus", 11);
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
        criteria.andLessThanOrEqualTo("noticeBegin", today);
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

//    @Scheduled(cron = "0/30 * * * * ? ")
    //    public void supplyEpTask() {
    //        Date now = new Date();
    //        Condition condition = new Condition(IdentityInfo.class);
    //        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
    //        criteria.andEqualTo("unionApproveStatus1", 4);
    //        criteria.andGreaterThanOrEqualTo("unionApprove1Et", now);
    //        List<IdentityInfo> list = iIdentityInfoService.findByCondition(condition);
    //        for (IdentityInfo identityInfo : list) {
    //            identityInfo.setUnionApproveStatus1(3);
    //            identityInfo.setReservationStatus(9);
    //            iIdentityInfoService.update(identityInfo);
    //            iPersonBatchStatusRecordService
    //                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "unionApproveStatus1", 3);
    //            iPersonBatchStatusRecordService
    //                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "reservationStatus", 9);
    //            HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", identityInfo.getId());
    //            try {
    //                SmsUtil.send(houseOther.getSelfPhone(), "系统提示：" + identityInfo.getName() + "，您的申请信息网上预审未通过。");
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //        }
    //
    //        criteria = condition.createCriteria();
    //        criteria.andEqualTo("unionApproveStatus2", 4);
    //        criteria.andGreaterThanOrEqualTo("unionApprove2Et", now);
    //        for (IdentityInfo identityInfo : list) {
    //            identityInfo.setUnionApproveStatus2(3);
    //            identityInfo.setReservationStatus(9);
    //            iIdentityInfoService.update(identityInfo);
    //            iPersonBatchStatusRecordService
    //                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "unionApproveStatus2", 3);
    //            iPersonBatchStatusRecordService
    //                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "reservationStatus", 9);
    //            HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", identityInfo.getId());
    //            try {
    //                SmsUtil.send(houseOther.getSelfPhone(), "系统提示：" + identityInfo.getName() + "，您的申请信息网上预审未通过。");
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //        }
    //
    //        criteria = condition.createCriteria();
    //        criteria.andEqualTo("rensheAcceptStatus", 2);
    //        criteria.andGreaterThanOrEqualTo("rensheAcceptSupplyEt", now);
    //        for (IdentityInfo identityInfo : list) {
    //            identityInfo.setHallStatus(4);
    //            identityInfo.setRensheAcceptStatus(4);
    //            iIdentityInfoService.update(identityInfo);
    //            iPersonBatchStatusRecordService
    //                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "hallStatus", 4);
    //
    //        }
    //
    //        criteria = condition.createCriteria();
    //        criteria.andEqualTo("policeApproveStatus", 2);
    //        criteria.andGreaterThanOrEqualTo("policeApproveEt", now);
    //        for (IdentityInfo identityInfo : list) {
    //            identityInfo.setPoliceApproveStatus(4);
    //            identityInfo.setHallStatus(1);
    //            iIdentityInfoService.update(identityInfo);
    //            iPersonBatchStatusRecordService
    //                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "hallStatus", 1);
    //
    //        }
    //
    //    }
}
