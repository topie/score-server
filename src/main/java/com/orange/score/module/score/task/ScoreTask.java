package com.orange.score.module.score.task;

import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.database.score.model.*;
import com.orange.score.module.score.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private IAcceptDateConfService iAcceptDateConfService;

    @Autowired
    private IScoreRecordService iScoreRecordService;

    @Autowired
    private ICompanyInfoService iCompanyInfoService;


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

    /**
     * 名单公示开始的定时任务
     */
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

    /**
     * 名单公示结束的定时任务
     */
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

    /**
     * 2019年1月11日
     * 自动接收材料，自动打分，每天凌晨执行任务
     * 应用场景：
     * “审核中心——人社受理审核——待审核”页面中的“审核”按钮中点击“通过”后的场景改为3天后自动接收材料；
     * 确认自动接收材料、自动打分功能的应用场景：
     * 在人社受理审核通过的第4个工作日，市教委、市税务、市知识产权局、民政、住建委自动接收材料并打分为0；
     */
    //@Scheduled(cron = "20 0 0 * * ? ")
    public void autoAcceptMaterialAndMark(){
        /*
        步骤
        1、从表 t_accept_date_conf 中获取工作日；
        2、从对象 IdentityInfo中得到字段 renshePassTime （人社受理审核通过的时间）；
        3、若通过后的第4个工作日申请人没有提交材料，则系统自动判定为接收材料、自动打分为0；到达指标打分-已打分流程
        4、进入此流程的所有操作，用户留痕为“系统自动操作”
         */


        Condition condition_bc = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria_bc = condition_bc.createCriteria();
        criteria_bc.andEqualTo("status", 1);
        //criteria_bc.andGreaterThanOrEqualTo("applyBegin",new Date());//大于身亲开始日期
        //criteria_bc.andLessThanOrEqualTo("closeFunctionTime",new Date());//关闭单位注册的时间
        List<BatchConf> list_bc = iBatchConfService.findByCondition(condition_bc);

        /*
        在取得批次信息后才能打分；
        为了预防
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if(list_bc.size()>0) {

            Condition condition = new Condition(AcceptDateConf.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
            condition.orderBy("acceptDate").asc();//受理日期升序排列
            criteria.andEqualTo("batchId", list_bc.get(0).getId());
            List<AcceptDateConf> list_ac = iAcceptDateConfService.findByCondition(condition);

            condition = new Condition(IdentityInfo.class);
            criteria = condition.createCriteria();
            criteria.andEqualTo("batchId", list_bc.get(0).getId());
            criteria.andEqualTo("hallStatus", 5);//人社受理审核通过状态
            criteria.andEqualTo("rensheAcceptStatus", 3);
            List<IdentityInfo> identityInfos = iIdentityInfoService.findByCondition(condition);

            for(IdentityInfo identityInfo : identityInfos){
                String renshePassTime = sdf.format(identityInfo.getRenshePassTime());
                String todayString = sdf.format(date);
                int index_renshe = 0;
                int index_today = 0;
                int index = 0;
                for(AcceptDateConf acceptDateConf : list_ac){
                    if(sdf.format(acceptDateConf.getAcceptDate()).equals(renshePassTime)){//
                        index_renshe = acceptDateConf.getId();
                    }
                    if(sdf.format(acceptDateConf.getAcceptDate()).equals(todayString)){//
                        index_today = acceptDateConf.getId();
                    }
                }
                if ((index_today-index_renshe)>=4){
                    List<Integer> roles = new ArrayList<Integer>();
                    roles.add(5);//民政
                    roles.add(6);//教委
                    roles.add(7);//知识产权
                    roles.add(8);//民政
                    roles.add(9);//住建委（国土房管局）
                    condition = new Condition(ScoreRecord.class);
                    criteria = condition.createCriteria();
                    criteria.andEqualTo("personId", identityInfo.getId());
                    criteria.andEqualTo("batchId", list_bc.get(0).getId());
                    criteria.andIn("opRoleId", roles);
                    criteria.andNotEqualTo("status",4);//未打分状态的记录才会被执行定时任务
                    condition.orderBy("id").asc();
                    List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);
                    for (ScoreRecord scoreRecord : scoreRecords){
                        scoreRecord.setStatus(4);//材料已送达，并打分为0；（3：材料已送达；4：已打分）
                        scoreRecord.setScoreValue(new BigDecimal(0));//材料已送达，并打分为0；（3：材料已送达；4：已打分）
                        scoreRecord.setOpUser("系统自动操作");//留痕要求：进入此流程的所有操作，用户留痕为“系统自动操作”
                        scoreRecord.setScoreDate(new Date());
                        if (scoreRecord.getOpRoleId()==5){
                            scoreRecord.setItemId(37);//婚姻状况，无
                        }
                        if (scoreRecord.getOpRoleId()==6){
                            scoreRecord.setItemId(1013);//市教委，无
                        }
                        if (scoreRecord.getOpRoleId()==7){
                            scoreRecord.setItemId(39);//知识产权局，无
                        }
                        if (scoreRecord.getOpRoleId()==8){
                            scoreRecord.setItemId(35);//市税务局，无
                        }
                        if (scoreRecord.getOpRoleId()==9){
                            scoreRecord.setItemId(26);//市国土房管局，无
                        }
                        iScoreRecordService.update(scoreRecord);
                    }
                    if (scoreRecords.size()>0){
                        /*
                        留痕记录
                         */
                        PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
                        personBatchStatusRecord.setPersonId(identityInfo.getId());
                        personBatchStatusRecord.setBatchId(identityInfo.getBatchId());
                        personBatchStatusRecord.setPersonIdNumber(identityInfo.getIdNumber());
                        personBatchStatusRecord.setStatusStr("自动接收材料、打分结束");
                        personBatchStatusRecord.setStatusTime(new Date());
                        personBatchStatusRecord.setStatusReason("申请人超过3个工作日没有提交材料");
                        personBatchStatusRecord.setStatusTypeDesc("超过3个工作日自动接收材料、打分（教委、税务、知识产权、民政、住建委）");
                        personBatchStatusRecord.setStatusInt(100);
                        iPersonBatchStatusRecordService.save(personBatchStatusRecord);
                    }
                }



            }
        }



    }


    /*
    2019年1月28日
    添加定时任务：每一期的积分开办时，申请人可以重新修改一次经办人的信息；
     */
    //@Scheduled(cron = "20 0 0 * * ? ")  //每天凌晨执行此任务
    @Scheduled(cron = "20 0 0 * * ? ") // 
    public void limitedChangeOperater(){
        /*
        1、选取正在开办的一期居住证积分；
        2、若有积分批次，获得关闭登录时间、重新打开登录时间，在此期间执行定时任务；把修改次数改为1；
           若无，不执行任务;
        3、把status 状态变为0
         */
        Condition condition = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("status", 1);
        Date today = DateUtil.getToday();
        criteria.andLessThanOrEqualTo("closeLoginTime",today);//大于关闭时间
        criteria.andGreaterThanOrEqualTo("openLoginTime",today);//小于再次打开时间
        List<BatchConf> list = iBatchConfService.findByCondition(condition);

        if(list.size()>0){
            Condition condition2 = new Condition(CompanyInfo.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria2 = condition2.createCriteria();
            List<CompanyInfo> list2 = iCompanyInfoService.findByCondition(condition2);

            for (CompanyInfo companyInfo : list2){
                companyInfo.setStatus(0);
                iCompanyInfoService.update(companyInfo);
            }
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
