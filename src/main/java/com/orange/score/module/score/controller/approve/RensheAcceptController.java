package com.orange.score.module.score.controller.approve;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.exception.AuthBusinessException;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.SmsUtil;
import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.database.score.model.*;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.*;
import com.orange.score.module.security.SecurityUser;
import com.orange.score.module.security.SecurityUtil;
import com.orange.score.module.security.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenJz1012 on 2018-04-02.
 */
@RestController
@RequestMapping("/api/score/approve/rensheAccept")
public class RensheAcceptController {

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IDictService iDictService;

    @Autowired
    private IPersonBatchStatusRecordService iPersonBatchStatusRecordService;

    @Autowired
    private IScoreRecordService iScoreRecordService;

    @Autowired
    private IOnlinePersonMaterialService iOnlinePersonMaterialService;

    @Autowired
    private IBatchConfService iBatchConfService;

    @Autowired
    private UserService userService;

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private IHouseOtherService iHouseOtherService;

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_identity_info");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_identity_info");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        Map hallStatus = iDictService.selectMapByAlias("hallStatus");
        result.put("hallStatus", hallStatus);
        Map rensheAcceptStatus = iDictService.selectMapByAlias("rensheAcceptStatus");
        result.put("rensheAcceptStatus", rensheAcceptStatus);
        List<CompanyInfo> companyInfos = iCompanyInfoService.findAll();
        Map companyMap = new HashMap();
//        for (CompanyInfo companyInfo : companyInfos) {
//            companyMap.put(companyInfo.getId(), companyInfo.getCompanyName());
//        }
        result.put("companyNames", companyMap);
        return ResponseUtil.success(result);
    }

    @GetMapping(value = "/approving")
    @ResponseBody
    public Result approving(IdentityInfo identityInfo,
                            @RequestParam(value = "dateSearch", required = false, defaultValue = "0") Integer dateSearch,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        /**
         * 2020年4月29日
         * 增加企业信息查询的字段，因为 IdentityInfo 对象中没有公司名字的字段值，用 rentHouseAddress 来代替
         * 先通过名字查询到公司ID，再把公司ID作为查询条件开始查询
         */
        if (identityInfo.getRentHouseAddress()!=null && identityInfo.getRentHouseAddress()!=""){
            CompanyInfo companyInfo = new CompanyInfo();
            companyInfo.setCompanyName(identityInfo.getRentHouseAddress());
            List<CompanyInfo> list = iCompanyInfoService.selectByFilter(companyInfo);
            if (list.size()>0){
                identityInfo.setCompanyId(list.get(list.size()-1).getId());
            }
        }
        if (identityInfo.getBatchId() == null) {
            BatchConf batchConf = new BatchConf();
            batchConf.setStatus(1);
            List<BatchConf> list = iBatchConfService.selectByFilter(batchConf);
            if (list.size() > 0) {
                identityInfo.setBatchId(list.get(0).getId());
            }
        }
        if (securityUser.getUserType() == 0) {
            identityInfo.setAcceptAddressId(1);
        } else if (securityUser.getUserType() == 1) {
            identityInfo.setAcceptAddressId(2);
        }
        identityInfo.setRensheAcceptStatus(1);
        identityInfo.setRensheOrGongan(2);// 公安不通过的件，人社不再审核
        if(identityInfo.getRentHouseAddress()!=null && identityInfo.getRentHouseAddress()!=""){
            identityInfo.setRentIdNumber(identityInfo.getRentHouseAddress());
        }
        identityInfo.setRentHouseAddress(dateSearch.toString()); // 借用 RentHouseAddress 字段
        List<Integer> companyIds = iIdentityInfoService.selectApprovingRedCompanyId(identityInfo, 5);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        for (IdentityInfo info : pageInfo.getList()) {
            if (companyIds.contains(info.getCompanyId())) {
                info.setCompanyWarning(1);
            }
        }
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/supply")
    @ResponseBody
    public Result supply(IdentityInfo identityInfo,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        /**
         * 2020年4月29日
         * 增加企业信息查询的字段，因为 IdentityInfo 对象中没有公司名字的字段值，用 rentHouseAddress 来代替
         * 先通过名字查询到公司ID，再把公司ID作为查询条件开始查询
         */
        if (identityInfo.getRentHouseAddress()!=null && identityInfo.getRentHouseAddress()!=""){
            CompanyInfo companyInfo = new CompanyInfo();
            companyInfo.setCompanyName(identityInfo.getRentHouseAddress());
            List<CompanyInfo> list = iCompanyInfoService.selectByFilter(companyInfo);
            if (list.size()>0){
                identityInfo.setCompanyId(list.get(list.size()-1).getId());
            }
        }
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        if (identityInfo.getBatchId() == null) {
            BatchConf batchConf = new BatchConf();
            batchConf.setStatus(1);
            List<BatchConf> list = iBatchConfService.selectByFilter(batchConf);
            if (list.size() > 0) {
                identityInfo.setBatchId(list.get(0).getId());
            }
        }
        if (securityUser.getUserType() == 0) {
            identityInfo.setAcceptAddressId(1);
        } else if (securityUser.getUserType() == 1) {
            identityInfo.setAcceptAddressId(2);
        }
        identityInfo.setRensheAcceptStatus(2);
        if(identityInfo.getRentHouseAddress()!=null && identityInfo.getRentHouseAddress()!=""){
            identityInfo.setRentIdNumber(identityInfo.getRentHouseAddress());
        }
        List<Integer> companyIds = iIdentityInfoService.selectApprovingRedCompanyId(identityInfo, 5);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        for (IdentityInfo info : pageInfo.getList()) {
            if (companyIds.contains(info.getCompanyId())) {
                info.setCompanyWarning(1);
            }
        }
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/approved")
    @ResponseBody
    public Result approved(IdentityInfo identityInfo,
                           @RequestParam(value = "dateSearch", required = false, defaultValue = "0") Integer dateSearch,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        /**
         * 2020年4月29日
         * 增加企业信息查询的字段，因为 IdentityInfo 对象中没有公司名字的字段值，用 rentHouseAddress 来代替
         * 先通过名字查询到公司ID，再把公司ID作为查询条件开始查询
         */
        if (identityInfo.getRentHouseAddress()!=null && identityInfo.getRentHouseAddress()!=""){
            CompanyInfo companyInfo = new CompanyInfo();
            companyInfo.setCompanyName(identityInfo.getRentHouseAddress());
            List<CompanyInfo> list = iCompanyInfoService.selectByFilter(companyInfo);
            if (list.size()>0){
                identityInfo.setCompanyId(list.get(list.size()-1).getId());
            }
        }
        if (identityInfo.getBatchId() == null) {
            BatchConf batchConf = new BatchConf();
            batchConf.setStatus(1);
            List<BatchConf> list = iBatchConfService.selectByFilter(batchConf);
            if (list.size() > 0) {
                identityInfo.setBatchId(list.get(0).getId());
            }
        }
        if (securityUser.getUserType() == 0) {
            identityInfo.setAcceptAddressId(1);
        } else if (securityUser.getUserType() == 1) {
            identityInfo.setAcceptAddressId(2);
        }
        identityInfo.setRensheAcceptStatus(3);
        if(identityInfo.getRentHouseAddress()!=null && identityInfo.getRentHouseAddress()!=""){
            identityInfo.setRentIdNumber(identityInfo.getRentHouseAddress());
        }
        identityInfo.setRentHouseAddress(dateSearch.toString()); // 借用 RentHouseAddress 字段
        List<Integer> companyIds = iIdentityInfoService.selectApprovingRedCompanyId(identityInfo, 5);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        for (IdentityInfo info : pageInfo.getList()) {
            if (companyIds.contains(info.getCompanyId())) {
                info.setCompanyWarning(1);
            }
            info.setRensheOrGongan(4);
        }
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/rejected")
    @ResponseBody
    public Result rejected(IdentityInfo identityInfo,
                           @RequestParam(value = "dateSearch", required = false, defaultValue = "0") Integer dateSearch,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        String str2 = securityUser.getAuthorities().toString().replace("[","").replace("]","").replace(" ","");
        String[] strArr = str2.split(",");
        int a = 0;
        for(int i=0;i<strArr.length;i++){
            if (strArr[i].equals("3")){
                a=1;
            }
        }
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        /**
         * 2020年4月29日
         * 增加企业信息查询的字段，因为 IdentityInfo 对象中没有公司名字的字段值，用 rentHouseAddress 来代替
         * 先通过名字查询到公司ID，再把公司ID作为查询条件开始查询
         */
        if (identityInfo.getRentHouseAddress()!=null && identityInfo.getRentHouseAddress()!=""){
            CompanyInfo companyInfo = new CompanyInfo();
            companyInfo.setCompanyName(identityInfo.getRentHouseAddress());
            List<CompanyInfo> list = iCompanyInfoService.selectByFilter(companyInfo);
            if (list.size()>0){
                identityInfo.setCompanyId(list.get(list.size()-1).getId());
            }
        }
        if (identityInfo.getBatchId() == null) {
            BatchConf batchConf = new BatchConf();
            batchConf.setStatus(1);
            List<BatchConf> list = iBatchConfService.selectByFilter(batchConf);
            if (list.size() > 0) {
                identityInfo.setBatchId(list.get(0).getId());
            }
        }
        if (securityUser.getUserType() == 0) {
            identityInfo.setAcceptAddressId(1);
        } else if (securityUser.getUserType() == 1) {
            identityInfo.setAcceptAddressId(2);
        }
        identityInfo.setRensheAcceptStatus(4);
        if(identityInfo.getRentHouseAddress()!=null && identityInfo.getRentHouseAddress()!=""){
            identityInfo.setRentIdNumber(identityInfo.getRentHouseAddress());
        }
        identityInfo.setRentHouseAddress(dateSearch.toString()); // 借用 RentHouseAddress 字段
        List<Integer> companyIds = iIdentityInfoService.selectApprovingRedCompanyId(identityInfo, 5);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        for (IdentityInfo info : pageInfo.getList()) {
            if (companyIds.contains(info.getCompanyId())) {
                info.setCompanyWarning(1);
            }
            if (a==1){
                info.setRensheOrGongan(4);// 人社不通过
            }
        }
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    /**
     * 2020年5月26日
     * 取消资格的申请人积分查询后，发现自己被取消资格了，就申请复核
     * @return
     */
    @GetMapping(value = "/toReview")
    @ResponseBody
    public Result toReview(IdentityInfo identityInfo,
                           @RequestParam(value = "dateSearch", required = false, defaultValue = "0") Integer dateSearch,
                           @RequestParam(value = "isDone", required = false) String isDone,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize){
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        if (isDone!=null && isDone!="" && Integer.parseInt(isDone)==1){
            identityInfo.setIstoreview(1); // 未处理
        }
        if (isDone!=null && isDone!="" && Integer.parseInt(isDone)==2){
            identityInfo.setIstoreview(2); // 窗口受理申请人的复核
        }
        if (isDone!=null && isDone!="" && Integer.parseInt(isDone)==12){
            identityInfo.setIstoreview(12); // 窗口受理申请人的复核
        }
        if (securityUser.getUserType() == 0) {
            identityInfo.setAcceptAddressId(1);
        } else if (securityUser.getUserType() == 1) {
            identityInfo.setAcceptAddressId(2);
        }
        if (identityInfo.getBatchId() == null) {
            BatchConf batchConf = new BatchConf();
            batchConf.setStatus(1);
            List<BatchConf> list = iBatchConfService.selectByFilter(batchConf);
            if (list.size() > 0) {
                identityInfo.setBatchId(list.get(0).getId());
            }
        }
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);

        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    /**
     * 2020年5月25日
     * @param id
     * @return
     * @throws IOException
     */
    @PostMapping("/clickHandle")
    public Result clickHandle(@RequestParam Integer id) throws IOException {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        identityInfo.setIstoreview(2); // 申请复核完毕
        iIdentityInfoService.update(identityInfo);

        /*
        留痕记录
         */
        PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
        personBatchStatusRecord.setPersonId(identityInfo.getId());
        personBatchStatusRecord.setBatchId(identityInfo.getBatchId());
        personBatchStatusRecord.setPersonIdNumber(identityInfo.getIdNumber());
        personBatchStatusRecord.setStatusTypeDesc("窗口点击开始复核取消资格的申请人");
        personBatchStatusRecord.setStatusTime(new Date());
        personBatchStatusRecord.setStatusStr(securityUser.getLoginName()+"点击");
        personBatchStatusRecord.setStatusReason("窗口点击开始复核取消资格的申请人");
        personBatchStatusRecord.setStatusInt(1241);
        iPersonBatchStatusRecordService.save(personBatchStatusRecord);
        return ResponseUtil.success();
    }

    /*
    人社受理审核-待审核-通过 “按钮”
     */
    @PostMapping("/agree")
    public Result agree(@RequestParam Integer id)  throws IOException  {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo.getReservationStatus() == 10) {
            throw new AuthBusinessException("预约已取消");
        }
        if (identityInfo != null) {
            if (4 == identityInfo.getRensheAcceptStatus()) {
                return ResponseUtil.error("该申请人已被人社受理不通过，无法进行该操作");
            }
            if (3 == identityInfo.getRensheAcceptStatus()) {
                return ResponseUtil.error("该申请人已被人社受理通过，无法进行该操作");
            }
            identityInfo.setOpuser4(securityUser.getDisplayName());
            identityInfo.setHallStatus(5);
            identityInfo.setRensheAcceptStatus(3);
            identityInfo.setRenshePassTime(new Date());//人社受理审核通过时间
            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "hallStatus", 5);

            /*
            2019年12月30日
            “公安前置审核”、“人社受理审核”
             */
            IdentityInfo identityInfo2 = iIdentityInfoService.findById(id);
            if (identityInfo2.getPoliceApproveStatus()==3 && identityInfo2.getRensheAcceptStatus()==3){
                iScoreRecordService.insertToInitRecords(identityInfo.getBatchId(), identityInfo.getId());
            }
            HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", identityInfo.getId());
            SmsUtil.send(houseOther.getSelfPhone(), "系统提示：您好，您已通过积分人社审核，予以受理，请登录申报单位用户查询本人具体信息。");
        }
        return ResponseUtil.success();
    }

    @PostMapping("/reAgree")
    public Result reAgree(@RequestParam Integer id, @RequestParam Integer indicatorId) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserRoleByUserId(securityUser.getId());
        if (!roles.contains(1)) return ResponseUtil.error("只有管理员有该操作权限");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo.getReservationStatus() == 10) {
            throw new AuthBusinessException("预约已取消");
        }
        if (identityInfo != null) {
            if (4 == identityInfo.getRensheAcceptStatus()) {
                return ResponseUtil.error("该申请人已被人社受理不通过，无法进行该操作");
            }
            identityInfo.setOpuser4(securityUser.getDisplayName());
            identityInfo.setHallStatus(5);
            identityInfo.setRensheAcceptStatus(3);
            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "hallStatus", 5);
            iScoreRecordService.insertToReInitRecords(identityInfo.getBatchId(), identityInfo.getId(), indicatorId);
        }
        return ResponseUtil.success();
    }

    @PostMapping("/appendAgree")
    public Result appendAgree(@RequestParam Integer id) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserRoleByUserId(securityUser.getId());
        if (!roles.contains(1)) return ResponseUtil.error("只有管理员有该操作权限");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo.getReservationStatus() == 10) {
            throw new AuthBusinessException("预约已取消");
        }
        if (identityInfo != null) {
            if (4 == identityInfo.getRensheAcceptStatus()) {
                return ResponseUtil.error("该申请人已被人社受理不通过，无法进行该操作");
            }
            identityInfo.setOpuser4(securityUser.getDisplayName());
            identityInfo.setHallStatus(5);
            identityInfo.setRensheAcceptStatus(3);
            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "hallStatus", 5);
            iScoreRecordService.insertToAppendInitRecords(identityInfo.getBatchId(), identityInfo.getId());
        }
        return ResponseUtil.success();
    }

    /*
    2019年8月15日
    添加恢复申请人到测评前的状态
     */
    @PostMapping("/selfTest")
    public Result selfTest(@RequestParam Integer id) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserRoleByUserId(securityUser.getId());
        //if (!roles.contains(1)) return ResponseUtil.error("只有管理员有该操作权限");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
//        if (identityInfo.getReservationStatus() == 10) {
//            throw new AuthBusinessException("预约已取消");
//        }
        if (identityInfo != null) {
//            if (4 == identityInfo.getRensheAcceptStatus()) {
//                return ResponseUtil.error("该申请人已被人社受理不通过，无法进行该操作");
//            }
            identityInfo.setReservationStatus(1);// 信息已保存
            identityInfo.setHallStatus(0);// 公安前置预审待审核
            identityInfo.setUnionApproveStatus1(0);
            identityInfo.setUnionApproveStatus2(0);
            identityInfo.setPoliceApproveStatus(0);
            identityInfo.setRensheAcceptStatus(0);
            iIdentityInfoService.update(identityInfo);

            /*
            留痕记录
             */
            PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
            personBatchStatusRecord.setPersonId(identityInfo.getId());
            personBatchStatusRecord.setBatchId(identityInfo.getBatchId());
            personBatchStatusRecord.setPersonIdNumber(identityInfo.getIdNumber());
            personBatchStatusRecord.setStatusStr("恢复申请人到测评前的状态");
            personBatchStatusRecord.setStatusTime(new Date());
            personBatchStatusRecord.setStatusReason("恢复申请人到测评前的状态");
            personBatchStatusRecord.setStatusTypeDesc("恢复申请人到测评前的状态");
            personBatchStatusRecord.setStatusInt(119);
            iPersonBatchStatusRecordService.save(personBatchStatusRecord);
        }
        return ResponseUtil.success();
    }

    @PostMapping("/supply")
    public Result supply(@RequestParam Integer id, @RequestParam("supplyArr") String supplyArr) throws IOException  {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo.getReservationStatus() == 10) {
            throw new AuthBusinessException("预约已取消");
        }
        if (identityInfo != null) {
            if (3 == identityInfo.getRensheAcceptStatus()) {
                return ResponseUtil.error("该申请人已被人社受理通过，无法进行该操作");
            }
            if (4 == identityInfo.getRensheAcceptStatus()) {
                return ResponseUtil.error("该申请人已被人社受理不通过，无法进行该操作");
            }
            identityInfo.setOpuser4(securityUser.getDisplayName());
            Date now = new Date();
            Date epDate = DateUtil.addDay(now, 3);
            identityInfo.setRensheAcceptSupplyEt(epDate);
            identityInfo.setRensheAcceptStatus(2);
            iIdentityInfoService.update(identityInfo);
            HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", identityInfo.getId());
            SmsUtil.send(houseOther.getSelfPhone(), "系统提示：您好，您上传的材料需要补正，请在三个工作日内尽快补正材料。（人社）");
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "rensheAcceptStatus", 2);
        }
        if (StringUtils.isNotEmpty(supplyArr)) {
            JSONArray jsonArray = JSONArray.parseArray(supplyArr);
            for (Object o : jsonArray) {
                Integer mId = ((JSONObject) o).getInteger("id");
                String reason = ((JSONObject) o).getString("reason");
                Condition condition = new Condition(OnlinePersonMaterial.class);
                tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
                criteria.andEqualTo("materialInfoId", mId);
                criteria.andEqualTo("personId", identityInfo.getId());
                criteria.andNotEqualTo("status", 2);
                condition.orderBy("id").desc();
                List<OnlinePersonMaterial> materials = iOnlinePersonMaterialService.findByCondition(condition);
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String strDate = sdf.format(date);
                if (materials.size() > 0) {
                    OnlinePersonMaterial onlinePersonMaterial = materials.get(0);
                    if(StringUtils.isNotEmpty(onlinePersonMaterial.getReason())){
                        onlinePersonMaterial.setReason(onlinePersonMaterial.getReason()+"<br/>"+strDate+"-人社受理审核："+reason);
                    }else {
                        onlinePersonMaterial.setReason(strDate+"-人社受理审核："+reason);
                    }
                    onlinePersonMaterial.setStatus(1);
                    iOnlinePersonMaterialService.update(onlinePersonMaterial);
                } else {
                    OnlinePersonMaterial onlinePersonMaterial = new OnlinePersonMaterial();
                    onlinePersonMaterial.setMaterialInfoId(mId);
                    onlinePersonMaterial.setReason(strDate+"-人社受理审核："+reason);
                    onlinePersonMaterial.setStatus(1);
                    onlinePersonMaterial.setcTime(new Date());
                    onlinePersonMaterial.setPersonId(identityInfo.getId());
                    onlinePersonMaterial.setBatchId(identityInfo.getBatchId());
                    iOnlinePersonMaterialService.save(onlinePersonMaterial);
                }
            }
        }
        return ResponseUtil.success();
    }

    @PostMapping("/disAgree")
    public Result disAgree(@RequestParam Integer id) throws IOException  {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo.getReservationStatus() == 10) {
            throw new AuthBusinessException("预约已取消");
        }
        if (identityInfo != null) {
            if (3 == identityInfo.getRensheAcceptStatus()) {
                return ResponseUtil.error("该申请人已被人社受理通过，无法进行该操作");
            }
            identityInfo.setOpuser4(securityUser.getDisplayName());
            identityInfo.setHallStatus(4);
            identityInfo.setRensheAcceptStatus(4);
            identityInfo.setRenshePassTime(new Date());
            iIdentityInfoService.update(identityInfo);
            HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", identityInfo.getId());
            SmsUtil.send(houseOther.getSelfPhone(), "系统提示：您好，您不符合积分受理条件，不予受理，请登录申报单位用户查询本人具体信息。（人社）");
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "hallStatus", 4);
        }
        return ResponseUtil.success();
    }

    /*
    2019年11月19日
    人社受理审核“已通过、未通过”退回至 待审核
    将表 t_identity_info 的字段 RENSHE_ACCEPT_STATUS 改为5，等人社管理点击同意后，改为1（待审核）；
     */
    @PostMapping("/backRensheApprove")
    public Result backRensheApprove(@RequestParam Integer id) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        identityInfo.setRensheAcceptStatusBak(identityInfo.getRensheAcceptStatus()); // 保存退回时的状态
        identityInfo.setRensheAcceptStatus(5);// 退回至待审核中
        iIdentityInfoService.update(identityInfo);

        /*
        留痕记录
         */
        PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
        personBatchStatusRecord.setPersonId(identityInfo.getId());
        personBatchStatusRecord.setBatchId(identityInfo.getBatchId());
        personBatchStatusRecord.setPersonIdNumber(identityInfo.getIdNumber());
        personBatchStatusRecord.setStatusTypeDesc("人社受理审核退回至未审核-开始");
        personBatchStatusRecord.setStatusTime(new Date());
        personBatchStatusRecord.setStatusStr(securityUser.getLoginName()+"修改成功");
        personBatchStatusRecord.setStatusReason("退回至未审核进行中");
        personBatchStatusRecord.setStatusInt(122);
        iPersonBatchStatusRecordService.save(personBatchStatusRecord);

        return ResponseUtil.success();
    }


    @GetMapping(value = "/rensheBackFinish")
    @ResponseBody
    public Result rensheBackFinish(IdentityInfo identityInfo,
                             @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        if (identityInfo.getBatchId() == null) {
            BatchConf batchConf = new BatchConf();
            batchConf.setStatus(1);
            List<BatchConf> list = iBatchConfService.selectByFilter(batchConf);
            if (list.size() > 0) {
                identityInfo.setBatchId(list.get(0).getId());
            }
        }
        identityInfo.setRensheAcceptStatus(5);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);

        List<Integer> companyIds = iIdentityInfoService.selectApprovingRedCompanyId(identityInfo, 5);
        Iterator<IdentityInfo> iterator = pageInfo.getList().iterator();
        IdentityInfo info;
        CompanyInfo companyInfo;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        while (iterator.hasNext()) {
            info = iterator.next();
            /*
            2019年6月24日
            因浏览器解析时间有8个小时的时差，所以把preApprove 的字段值赋给 regionName
             */
            //info.setRegionName(sdf.format(info.getPreApprove()));

            companyInfo = iCompanyInfoService.findById(info.getCompanyId());
            if (StringUtils.isEmpty(companyInfo.getBusinessLicenseSrc())) {
                iterator.remove();
                continue;
            }
            if (companyIds.contains(info.getCompanyId())) {
                info.setCompanyWarning(1);
            }
        }

        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }



    /*
    2019年11月20日
    人社受理审核阶段退回至未审核，并删除20条打分项
     */
    @PostMapping("/rensheBackFinish2")
    public Result rensheBackFinish2(@RequestParam Integer id) throws IOException {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        identityInfo.setRensheAcceptStatus(1);
        iIdentityInfoService.update(identityInfo);

//        ScoreRecord scoreRecord = new ScoreRecord();
//        scoreRecord.setPersonId(id);
//        List<ScoreRecord> scoreRecords = iScoreRecordService.selectByFilter(scoreRecord);

        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("personId", id);
        List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);

        for (ScoreRecord scoreRecord1 : scoreRecords){
            iScoreRecordService.deleteById(scoreRecord1.getId());
        }
        /*
        留痕记录
         */
        PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
        personBatchStatusRecord.setPersonId(identityInfo.getId());
        personBatchStatusRecord.setBatchId(identityInfo.getBatchId());
        personBatchStatusRecord.setPersonIdNumber(identityInfo.getIdNumber());
        personBatchStatusRecord.setStatusTypeDesc("人社受理审核退回至未审核-结束");
        personBatchStatusRecord.setStatusTime(new Date());
        personBatchStatusRecord.setStatusStr(securityUser.getLoginName()+"修改成功");
        personBatchStatusRecord.setStatusReason("人社受理审核退回至未审核-结束");
        personBatchStatusRecord.setStatusInt(124);
        iPersonBatchStatusRecordService.save(personBatchStatusRecord);
        return ResponseUtil.success();
    }

    @PostMapping("/rensheBackFinish3")
    public Result rensheBackFinish3(@RequestParam Integer id) throws IOException {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        identityInfo.setRensheAcceptStatus(identityInfo.getRensheAcceptStatusBak());
        iIdentityInfoService.update(identityInfo);


        /*
        留痕记录
         */
        PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
        personBatchStatusRecord.setPersonId(identityInfo.getId());
        personBatchStatusRecord.setBatchId(identityInfo.getBatchId());
        personBatchStatusRecord.setPersonIdNumber(identityInfo.getIdNumber());
        personBatchStatusRecord.setStatusTypeDesc("驳回");
        personBatchStatusRecord.setStatusTime(new Date());
        personBatchStatusRecord.setStatusStr(securityUser.getLoginName()+"驳回成功");
        personBatchStatusRecord.setStatusReason("驳回");
        personBatchStatusRecord.setStatusInt(1250);
        iPersonBatchStatusRecordService.save(personBatchStatusRecord);
        return ResponseUtil.success();
    }

}
