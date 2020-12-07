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

    @Autowired
    private IHouseRelationshipService iHouseRelationshipService;

    @Autowired
    private IHouseMoveService iHouseMoveService;


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
            //criteria.andIsNull("scoreValue"); // 分数为空
            //criteria.andEqualTo("personId", 493713);
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
                        }else if(person.getAge()>=36){
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
                        iScoreRecordService.update(scoreRecord);

                        /*
                        留痕
                         */
                        PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
                        personBatchStatusRecord.setPersonId(person.getId());
                        personBatchStatusRecord.setBatchId(person.getBatchId());
                        personBatchStatusRecord.setPersonIdNumber(person.getIdNumber());
                        personBatchStatusRecord.setStatusStr("公安部门年龄，材料送达;");
                        personBatchStatusRecord.setStatusTime(new Date());
                        personBatchStatusRecord.setStatusReason("公安部门年龄，材料送达");
                        personBatchStatusRecord.setStatusTypeDesc("公安部门年龄，材料送达");
                        personBatchStatusRecord.setStatusInt(201);
                        iPersonBatchStatusRecordService.save(personBatchStatusRecord);
                    }else if (scoreRecord.getIndicatorId() == 902){
                        if (houseOther.getPenalty()!=null && houseOther.getPenalty()==2){
                            scoreRecord.setItemId(1031);
                            scoreRecord.setStatus(4);
                            scoreRecord.setScoreValue(new BigDecimal(0));
                            scoreRecord.setScoreDate(new Date());

                            iScoreRecordService.update(scoreRecord);

                            /*
                            留痕
                             */
                            PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
                            personBatchStatusRecord.setPersonId(person.getId());
                            personBatchStatusRecord.setBatchId(person.getBatchId());
                            personBatchStatusRecord.setPersonIdNumber(person.getIdNumber());
                            personBatchStatusRecord.setStatusStr("公安部门5年内是否有刑事犯罪记录，材料送达;");
                            personBatchStatusRecord.setStatusTime(new Date());
                            personBatchStatusRecord.setStatusReason("公安部门5年内是否有刑事犯罪记录，材料送达");
                            personBatchStatusRecord.setStatusTypeDesc("公安部门5年内是否有刑事犯罪记录，材料送达");
                            personBatchStatusRecord.setStatusInt(201);
                            iPersonBatchStatusRecordService.save(personBatchStatusRecord);
                        }
                    }

                }
            }
        }
    }


    /**
     * 2020年9月7日
     * 只做市区的件
     *
     * 1、市民政局：填写申请人信息页面第1页，“配偶是否在天津就业且用人单位依法缴纳社会保险累计满24个月”、“配偶是否是现役军人”两项都选择“否”，
     * 自动接收文件并打为0分。
     *
     * 2、市退役军人事务局：填写申请人信息页面第6页，“服役期间立功情况”选择“无”，
     * 自动接收文件并打为0分。
     */
    //@Scheduled(cron = "0 0/2 * * * ? ")//测试用，时间频率，每隔两分钟执行一次；
    @Scheduled(cron = "20 0 0 * * ? ")
    public void minzhegAndTuiyijunren(){
        Condition condition_bc = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria_bc = condition_bc.createCriteria();
        criteria_bc.andEqualTo("status", 1);
        List<BatchConf> list_bc = iBatchConfService.findByCondition(condition_bc);
        if (list_bc.size()>0){
            Integer batch_id = list_bc.get(0).getId();

            List<Integer> roles = new ArrayList<Integer>();
            roles.add(5); // 民政
            roles.add(1050); // 退役军人事务局
            Condition condition = new Condition(ScoreRecord.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
            criteria.andEqualTo("batchId", batch_id);
            criteria.andIn("opRoleId",roles);
            criteria.andIsNull("scoreValue"); // 分数为空
            criteria.andEqualTo("acceptAddressId", 1); // 市区
            //criteria.andEqualTo("personId", 484843);
            List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);



            IdentityInfo person;
            HouseOther houseOther;
            HouseRelationship houseRelationship;
            HouseMove houseMove;
            if (scoreRecords.size()>0){
                for (ScoreRecord scoreRecord : scoreRecords){
                    person = iIdentityInfoService.findById(scoreRecord.getPersonId());
                    houseOther = iHouseOtherService.findBy("identityInfoId", scoreRecord.getPersonId());
                    houseMove = iHouseMoveService.findBy("identityInfoId", scoreRecord.getPersonId());

                    condition = new Condition(HouseRelationship.class);
                    criteria = condition.createCriteria();
                    criteria.andEqualTo("identityInfoId",scoreRecord.getPersonId());
                    criteria.andEqualTo("relationship","配偶");
                    criteria.andEqualTo("inTianjin","2");
                    criteria.andEqualTo("isSpousesoldier","2");

                    //criteria.andEqualTo("personId", 483454);
                    List<HouseRelationship> houseRelationships = iHouseRelationshipService.findByCondition(condition);


                    if (scoreRecord.getOpRoleId()==5){
                        /*
                        2020年10月10日
                        条件再加上，婚姻状况是如下的，打0分
                            “未婚 2 ”、
                            “丧偶 3”、
                            “离异 4 ”
                         */
                        if(houseRelationships.size()>0 || houseMove.getMarriageStatus()<5){
                            scoreRecord.setItemId(1031);
                            scoreRecord.setStatus(4);
                            scoreRecord.setScoreValue(new BigDecimal(0));
                            scoreRecord.setScoreDate(new Date());
                            iScoreRecordService.update(scoreRecord);

                            /*
                            留痕
                             */
                            PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
                            personBatchStatusRecord.setPersonId(person.getId());
                            personBatchStatusRecord.setBatchId(person.getBatchId());
                            personBatchStatusRecord.setPersonIdNumber(person.getIdNumber());
                            personBatchStatusRecord.setStatusStr("民政，系统打0分");
                            personBatchStatusRecord.setStatusTime(new Date());
                            personBatchStatusRecord.setStatusReason("民政，系统打0分");
                            personBatchStatusRecord.setStatusTypeDesc("民政，系统打0分");
                            personBatchStatusRecord.setStatusInt(203);
                            iPersonBatchStatusRecordService.save(personBatchStatusRecord);
                        }
                    }else if (scoreRecord.getOpRoleId() == 1050){
                        if (houseOther.getSoldierMeritorious()!=null && houseOther.getSoldierMeritorious()==50){
                            scoreRecord.setItemId(1031);
                            scoreRecord.setStatus(4);
                            scoreRecord.setScoreValue(new BigDecimal(0));
                            scoreRecord.setScoreDate(new Date());
                            iScoreRecordService.update(scoreRecord);

                            /*
                            留痕
                             */
                            PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
                            personBatchStatusRecord.setPersonId(person.getId());
                            personBatchStatusRecord.setBatchId(person.getBatchId());
                            personBatchStatusRecord.setPersonIdNumber(person.getIdNumber());
                            personBatchStatusRecord.setStatusStr("退役军人事务局，系统打0分");
                            personBatchStatusRecord.setStatusTime(new Date());
                            personBatchStatusRecord.setStatusReason("退役军人事务局，系统打0分");
                            personBatchStatusRecord.setStatusTypeDesc("退役军人事务局，系统打0分");
                            personBatchStatusRecord.setStatusInt(204);
                            iPersonBatchStatusRecordService.save(personBatchStatusRecord);
                        }
                    }

                }
            }
        }
    }

    /**
     * 2020年8月5日
     * 将人社部门的守法诚信打分项自动设置为0分
     */
    //@Scheduled(cron = "0 0/2 * * * ? ")//测试用，时间频率，每隔两分钟执行一次；
    @Scheduled(cron = "20 0 0 * * ? ")
    public void shoufachengxin(){
        Condition condition_bc = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria_bc = condition_bc.createCriteria();
        criteria_bc.andEqualTo("status", 1);
        List<BatchConf> list_bc = iBatchConfService.findByCondition(condition_bc);
        if (list_bc.size()>0){
            Integer batch_id = list_bc.get(0).getId();

            Condition condition = new Condition(ScoreRecord.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
            criteria.andEqualTo("batchId", batch_id);
            criteria.andEqualTo("opRoleId", 3);// 人社部门
            criteria.andEqualTo("indicatorId", 1010);// 守法诚信
            criteria.andIsNull("isDeducted"); //
            criteria.andLessThanOrEqualTo("status", 3);// 小于等于 3
            //criteria.andEqualTo("personId", 483454);
            List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);

            IdentityInfo person;
            if (scoreRecords.size()>0){
                for (ScoreRecord scoreRecord : scoreRecords){
                    person = iIdentityInfoService.findById(scoreRecord.getPersonId());
                    scoreRecord.setStatus(4);
                    scoreRecord.setScoreValue(new BigDecimal(0));
                    scoreRecord.setItemId(1031);
                    scoreRecord.setScoreDate(new Date());
                    iScoreRecordService.update(scoreRecord);

                    /*
                    留痕
                     */
                    PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
                    personBatchStatusRecord.setPersonId(person.getId());
                    personBatchStatusRecord.setBatchId(person.getBatchId());
                    personBatchStatusRecord.setPersonIdNumber(person.getIdNumber());
                    personBatchStatusRecord.setStatusStr("守法诚信自动打0分;");
                    personBatchStatusRecord.setStatusTime(new Date());
                    personBatchStatusRecord.setStatusReason("守法诚信自动打0分");
                    personBatchStatusRecord.setStatusTypeDesc("守法诚信自动打0分");
                    personBatchStatusRecord.setStatusInt(202);
                    iPersonBatchStatusRecordService.save(personBatchStatusRecord);
                }
            }
        }
    }

    /**
     * 2020年10月15日
     * 市教委窗口要求，申请人填写信息第4页，“文化程度”选项，选择“高级技工学校高级班”或“无”的申请人，每日定时做自动接收。
        信息流转与市民政局、市退役军人局一致，只修改市区，不修改滨海新区。
     */
    //@Scheduled(cron = "0 0/2 * * * ? ")//测试用，时间频率，每隔两分钟执行一次；
    @Scheduled(cron = "20 0 0 * * ? ")
    public void wenhuachengdu(){
        Condition condition_bc = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria_bc = condition_bc.createCriteria();
        criteria_bc.andEqualTo("status", 1);
        List<BatchConf> list_bc = iBatchConfService.findByCondition(condition_bc);
        if (list_bc.size()>0){
            Integer batch_id = list_bc.get(0).getId();

            Condition condition = new Condition(ScoreRecord.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
            criteria.andEqualTo("batchId", batch_id);
            criteria.andEqualTo("opRoleId", 6);// 市教委
            criteria.andEqualTo("indicatorId", 3);// 受教育程度
            criteria.andIsNull("scoreValue"); // 分数为空
            criteria.andEqualTo("acceptAddressId", 1); // 市区
            //criteria.andEqualTo("personId", 483480);
            List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);

            IdentityInfo person;
            HouseOther houseOther;
            if (scoreRecords.size()>0){
                for (ScoreRecord scoreRecord : scoreRecords){
                    person = iIdentityInfoService.findById(scoreRecord.getPersonId());
                    houseOther = iHouseOtherService.findBy("identityInfoId", scoreRecord.getPersonId());
                    if(houseOther.getCultureDegree()==1011 || houseOther.getCultureDegree()==1013){ // 1011,1013
                        scoreRecord.setStatus(4);
                        scoreRecord.setScoreValue(new BigDecimal(0));
                        scoreRecord.setItemId(1013);
                        scoreRecord.setScoreDate(new Date());
                        iScoreRecordService.update(scoreRecord);

                        /*
                        留痕
                         */
                        PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
                        personBatchStatusRecord.setPersonId(person.getId());
                        personBatchStatusRecord.setBatchId(person.getBatchId());
                        personBatchStatusRecord.setPersonIdNumber(person.getIdNumber());
                        personBatchStatusRecord.setStatusStr("文化程度自动打0分;");
                        personBatchStatusRecord.setStatusTime(new Date());
                        personBatchStatusRecord.setStatusReason("文化程度自动打0分");
                        personBatchStatusRecord.setStatusTypeDesc("文化程度自动打0分");
                        personBatchStatusRecord.setStatusInt(205);
                        iPersonBatchStatusRecordService.save(personBatchStatusRecord);
                    }

                }
            }
        }
    }

    /**
     * 2020年12月7日
     * 因为市区的规自局、住建委分家的原因，但是滨海新区不分家还是一个部门处理件，
     * 最后给申请人呈现的是4个打分项合并为一个指标项：住房，当申请人复核分数时无法区分把申请的理由分发给哪个部门（规自局、住建委）
     * 这个定时任务就是解决这个问题的
     */
    //@Scheduled(cron = "0 0/2 * * * ? ")//测试用，时间频率，每隔两分钟执行一次；
    @Scheduled(cron = "20 0 0 * * ? ")
    public void toreviewGuizijuAndZhujianwei(){
        Condition condition_bc = new Condition(BatchConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria_bc = condition_bc.createCriteria();
        criteria_bc.andEqualTo("status", 1);
        List<BatchConf> list_bc = iBatchConfService.findByCondition(condition_bc);
        if (list_bc.size()>0){
            Integer batch_id = list_bc.get(0).getId();

            List<Integer> roles = new ArrayList<Integer>();
            roles.add(9); // 住建委
            roles.add(1060); // 规自局

            Condition condition = new Condition(ScoreRecord.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
            criteria.andEqualTo("batchId", batch_id);
            criteria.andIn("opRoleId",roles);
            criteria.andIsNull("guizijuOrZhujianwei"); // 规自局住建委的状态为空
            criteria.andEqualTo("acceptAddressId", 1); // 市区
            //criteria.andEqualTo("personId", 483480);
            List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);

            IdentityInfo person;
            HouseOther houseOther;
            HouseMove houseMove;
            if (scoreRecords.size()>0){
                for (ScoreRecord scoreRecord : scoreRecords){
                    person = iIdentityInfoService.findById(scoreRecord.getPersonId());
                    houseOther = iHouseOtherService.findBy("identityInfoId", scoreRecord.getPersonId());
                    houseMove = iHouseMoveService.findBy("identityInfoId", scoreRecord.getPersonId());
                    if((person.getRightProperty()!=null && Integer.parseInt(person.getRightProperty())==1)
                            && (houseMove.getRentHouseAddress()==null || (houseMove.getRentHouseAddress()!=null &&  houseMove.getRentHouseAddress().length()<10))){
                        scoreRecord.setGuizijuOrZhujianwei(1);
                        iScoreRecordService.update(scoreRecord);
                    } else{
                        scoreRecord.setGuizijuOrZhujianwei(2);
                        iScoreRecordService.update(scoreRecord);
                    }
                    /*
                        留痕
                         */
                    PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
                    personBatchStatusRecord.setPersonId(person.getId());
                    personBatchStatusRecord.setBatchId(person.getBatchId());
                    personBatchStatusRecord.setPersonIdNumber(person.getIdNumber());
                    personBatchStatusRecord.setStatusStr("区分规自局1-住建委-2");
                    personBatchStatusRecord.setStatusTime(new Date());
                    personBatchStatusRecord.setStatusReason("区分规自局1-住建委-2");
                    personBatchStatusRecord.setStatusTypeDesc("区分规自局1-住建委-2");
                    personBatchStatusRecord.setStatusInt(206);
                    iPersonBatchStatusRecordService.save(personBatchStatusRecord);
                }
            }
        }
    }



}
