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

    @Autowired
    private IIndicatorItemService iIndicatorItemService;


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

    /**
     * 2019年3月7日
     * 关闭此定时任务
     * 原因是需要手动操作名单公示，汇总发布
     */
//    @Scheduled(cron = "5 0 0 * * ? ")
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
     * 0 0/5 * * * ?  ---每五分钟执行一次
     */
    //@Scheduled(cron = "0 0/2 * * * ? ")//测试用，时间频率，每隔两分钟执行一次；
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
        //criteria_bc.andGreaterThanOrEqualTo("applyBegin",new Date());//大于在线申请开始日期
        criteria_bc.andLessThanOrEqualTo("applyBegin",new Date());//大于在线申请开始日期
        List<BatchConf> list_bc = iBatchConfService.findByCondition(condition_bc);

        /*
        在取得批次信息后才能打分；
        为了预防,只选中一个批次
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if(list_bc.size()==1) {

            Condition condition = new Condition(AcceptDateConf.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
            condition.orderBy("acceptDate").asc();//受理日期升序排列
            criteria.andEqualTo("batchId", list_bc.get(0).getId());
            criteria.andEqualTo("addressId", 1);
            List<AcceptDateConf> list_ac = iAcceptDateConfService.findByCondition(condition);
            /*
            重新排序下ID值，因为设置开办日期时，有跨度较大的ID，两者相减大于4；
            排除此情况，重新排下ID值；
             */
            int index_ac = 1;
            for(AcceptDateConf acceptDateConf : list_ac){
                acceptDateConf.setId(index_ac++);
            }

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
                    criteria.andNotEqualTo("status",3);//未接收材料的记录才会被执行定时任务
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

    /**
     * 2019年8月20日
     * 年龄、守法诚信【公安】
     * 以上两个打分项是每天凌晨自动打分：
     * 1、根据申请人的年龄：35岁以下（含35岁）为20分；36-45周岁为10分；46岁以上（含46岁）为0分；
     * 2、守法诚信【公安】为0分，因为大多数的人为0分，若需要修改的话，公安窗口会自己申请重新打分；
     */
    //@Scheduled(cron = "0 0/2 * * * ? ")//测试用，时间频率，每隔两分钟执行一次；
    //@Scheduled(cron = "20 0 0 * * ? ")
    public void policeSelfScore(){
        Condition condition_bc = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria_bc = condition_bc.createCriteria();
        criteria_bc.andEqualTo("status", 1);
        List<BatchConf> list_bc = iBatchConfService.findByCondition(condition_bc);
        if (list_bc.size()>0){
            Integer batch_id = list_bc.get(0).getId();

            List<Integer> roles = new ArrayList<>();
            roles.add(1); // 年龄
            roles.add(1011); // 守法诚信【公安】

            Condition condition = new Condition(ScoreRecord.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
            criteria.andEqualTo("batchId", batch_id);
            criteria.andEqualTo("opRoleId", 4);// 公安部门
            criteria.andIn("indicatorId",roles);
            criteria.andEqualTo("status", 3);//
            //criteria.andEqualTo("personId", 21564);
            List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);
            if (scoreRecords.size()>0){
                for (ScoreRecord scoreRecord : scoreRecords){
                    IdentityInfo person = iIdentityInfoService.findById(scoreRecord.getPersonId());
                    if (scoreRecord.getIndicatorId()==1){
                        if (person.getAge()>=46){ // 46岁以上（含46岁）
                            scoreRecord.setItemId(3);
                            IndicatorItem indicatorItem = iIndicatorItemService.findById(3);
                            scoreRecord.setScoreValue(new BigDecimal(indicatorItem.getScore()));// 根据数据库的分数获取分数，不写死，防止有人修改后台配置
                        }else if (person.getAge()>=36){ // 36至45周岁
                            scoreRecord.setItemId(2);
                            IndicatorItem indicatorItem = iIndicatorItemService.findById(2);
                            scoreRecord.setScoreValue(new BigDecimal(indicatorItem.getScore()));// 根据数据库的分数获取分数，不写死，防止有人修改后台配置
                        } else {
                            scoreRecord.setItemId(1); // 35岁以下（含35岁）
                            IndicatorItem indicatorItem = iIndicatorItemService.findById(1);
                            scoreRecord.setScoreValue(new BigDecimal(indicatorItem.getScore()));// 根据数据库的分数获取分数，不写死，防止有人修改后台配置
                        }
                    }
                    if (scoreRecord.getIndicatorId()==1011){ // 若是守法诚信【公安】
                        scoreRecord.setScoreValue(new BigDecimal(0));
                        scoreRecord.setItemId(0);
                    }
                    scoreRecord.setStatus(4);
                    scoreRecord.setScoreDate(new Date());
                    iScoreRecordService.update(scoreRecord);

                    /*
                    留痕
                     */
                    PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
                    personBatchStatusRecord.setPersonId(person.getId());
                    personBatchStatusRecord.setBatchId(person.getBatchId());
                    personBatchStatusRecord.setPersonIdNumber(person.getIdNumber());
                    personBatchStatusRecord.setStatusStr("公安部门年龄、守法诚信自动打分");
                    personBatchStatusRecord.setStatusTime(new Date());
                    personBatchStatusRecord.setStatusReason("公安部门年龄、守法诚信自动打分");
                    personBatchStatusRecord.setStatusTypeDesc("公安部门年龄、守法诚信自动打分");
                    personBatchStatusRecord.setStatusInt(200);
                    iPersonBatchStatusRecordService.save(personBatchStatusRecord);
                }
            }
        }
    }

    /**
     * 2020年7月28日
     * 年龄、守法诚信【公安】
     * 以上两个打分项是每天凌晨自动打分：
     * 1、根据申请人的年龄：35岁以下（含35岁）为20分；36-45周岁为10分；46岁以上（含46岁）为0分；
     * 2、有无刑事犯罪记录，为无时打0分；
     *
     * 取消材料接收环节，对前置审核通过的申请人直接进入打分环节；
     */
    //@Scheduled(cron = "0 0/2 * * * ? ")//测试用，时间频率，每隔两分钟执行一次；
    @Scheduled(cron = "20 0 0 * * ? ")
    public void policeSelfScore2(){
        Condition condition_bc = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria_bc = condition_bc.createCriteria();
        criteria_bc.andEqualTo("status", 1);
        List<BatchConf> list_bc = iBatchConfService.findByCondition(condition_bc);
        if (list_bc.size()>0){
            Integer batch_id = list_bc.get(0).getId();

            Condition condition = new Condition(ScoreRecord.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
            criteria.andEqualTo("batchId", batch_id);
            criteria.andEqualTo("opRoleId", 4);// 公安部门
            criteria.andLessThanOrEqualTo("status", 3);// 小于等于 3
            //criteria.andEqualTo("personId", 483454);
            List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);

            Condition condition2 = new Condition(IndicatorItem.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria2 = condition2.createCriteria();
            criteria2.andEqualTo("indicatorId", 1);
            condition2.orderBy("score").desc().orderBy("id").asc();
            List<IndicatorItem> indicatorItems = iIndicatorItemService.findByCondition(condition2);
            Map<Integer,Integer> map = new HashMap();
            for(IndicatorItem indicatorItem : indicatorItems){
                map.put(indicatorItem.getId(),indicatorItem.getScore());
            }

            IdentityInfo person;
            HouseOther houseOther;
            if (scoreRecords.size()>0){
                for (ScoreRecord scoreRecord : scoreRecords){
                    person = iIdentityInfoService.findById(scoreRecord.getPersonId());
                    houseOther = iHouseOtherService.findBy("identityInfoId", scoreRecord.getPersonId());
                    if (scoreRecord.getIndicatorId()==1){
                        if(person.getAge()>45){
                            scoreRecord.setItemId(3);
                            BigDecimal value = new BigDecimal(map.get(3));
                            scoreRecord.setScoreValue(value);
                        }else if(person.getAge()>36){
                            scoreRecord.setItemId(2);
                            BigDecimal value = new BigDecimal(map.get(2));
                            scoreRecord.setScoreValue(value);
                        }else if (person.getAge()<36){
                            scoreRecord.setItemId(1);
                            BigDecimal value = new BigDecimal(map.get(1));
                            scoreRecord.setScoreValue(value);
                        }
                        scoreRecord.setStatus(4);
                        scoreRecord.setScoreDate(new Date());
                    }else if (scoreRecord.getIndicatorId() == 902){
                        if (houseOther.getPenalty()!=null && houseOther.getPenalty()==2){
                            scoreRecord.setItemId(1031);
                            scoreRecord.setStatus(4);
                            scoreRecord.setScoreValue(new BigDecimal(0));
                            scoreRecord.setScoreDate(new Date());
                        }
                    }else{
                        scoreRecord.setStatus(3);
                    }
                    iScoreRecordService.update(scoreRecord);

                    /*
                    留痕
                     */
                    PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
                    personBatchStatusRecord.setPersonId(person.getId());
                    personBatchStatusRecord.setBatchId(person.getBatchId());
                    personBatchStatusRecord.setPersonIdNumber(person.getIdNumber());
                    personBatchStatusRecord.setStatusStr("公安部门年龄、5年内是否有刑事犯罪记录，材料送达;");
                    personBatchStatusRecord.setStatusTime(new Date());
                    personBatchStatusRecord.setStatusReason("公安部门年龄、5年内是否有刑事犯罪记录，材料送达");
                    personBatchStatusRecord.setStatusTypeDesc("公安部门年龄、5年内是否有刑事犯罪记录，材料送达");
                    personBatchStatusRecord.setStatusInt(201);
                    iPersonBatchStatusRecordService.save(personBatchStatusRecord);
                }
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
