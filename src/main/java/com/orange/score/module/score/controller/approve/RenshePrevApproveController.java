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
import com.orange.score.database.score.model.*;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.*;
import com.orange.score.module.security.SecurityUser;
import com.orange.score.module.security.SecurityUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenJz1012 on 2018-04-02.
 */
@RestController
@RequestMapping("/api/score/approve/renshePrevApprove")
public class RenshePrevApproveController {

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IDictService iDictService;

    @Autowired
    private IPersonBatchStatusRecordService iPersonBatchStatusRecordService;

    @Autowired
    private IOnlinePersonMaterialService iOnlinePersonMaterialService;

    @Autowired
    private IHouseOtherService iHouseOtherService;

    @Autowired
    private IBatchConfService iBatchConfService;

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private IHouseMoveService iHouseMoveService;

    @Autowired
    private IHouseProfessionService iHouseProfessionService;

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_identity_info");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_identity_info");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        Map unionApproveStatus1 = iDictService.selectMapByAlias("unionApproveStatus1");
        result.put("unionApproveStatus1", unionApproveStatus1);
        Map unionApproveStatus2 = iDictService.selectMapByAlias("unionApproveStatus2");
        result.put("unionApproveStatus2", unionApproveStatus2);
        Map reservationStatus = iDictService.selectMapByAlias("reservationStatus");
        result.put("reservationStatus", reservationStatus);
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
        if (securityUser.getUserType() == 0) {
            identityInfo.setAcceptAddressId(1);
        } else if (securityUser.getUserType() == 1) {
            identityInfo.setAcceptAddressId(2);
        }
        identityInfo.setUnionApproveStatus2(1);
//        identityInfo.setOrderByColumn("reservationDate");
//        identityInfo.setOrderBy("desc");
        identityInfo.setOrderByColumn("unionApprove2Et,id");
        identityInfo.setOrderBy("asc");
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

    @PostMapping("/lock")
    public Result lock(@RequestParam Integer id) throws IOException {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
//        if (1 != identityInfo.getUnionApproveStatus2() && 4 != identityInfo.getUnionApproveStatus2()) {
//            return ResponseUtil.error("该申请人不是待审核状态或待补件，锁定无效");
//        }
        if (StringUtils.isNotEmpty(identityInfo.getLockUser2()) && !identityInfo.getLockUser2()
                .equals(securityUser.getUsername())) {
            return ResponseUtil.error("该申请人的预审状态已被" + identityInfo.getLockUser2() + "锁定");
        }
        identityInfo.setLockUser2(securityUser.getUsername());
        iIdentityInfoService.update(identityInfo);
        return ResponseUtil.success();
    }


    /**
     * 2019年10月22日
     * 在审核中心——人社预审——已通过、未通过两个列表中，操作列加入“退回至待审核”按钮，功能为将对应申请人退回至“审核中心——人社预审——待审核”列表。此按钮权限分配给有“审核中心——人社预审”权限的用户。
     * @param id
     * @return
     * @throws IOException
     */
    @PostMapping("/backStart")
    public Result backStart(@RequestParam Integer id) throws IOException {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        int back = identityInfo.getUnionApproveStatus2();
        if (identityInfo.getReservationStatus()==8 ){
            identityInfo.setUnionApproveStatus2(3); // 3：把人社退回的申请人退回至“不通过”状态，把流程卡住
            identityInfo.setReservationStatus(12); // 12：表示窗口申请退回申请人至未审核的代码
            iIdentityInfoService.update(identityInfo);

        }
        if (identityInfo.getReservationStatus() == 9 ){
            identityInfo.setReservationStatus(12); // 12：表示窗口申请退回申请人至未审核的代码
            identityInfo.setUnionApproveStatus2(3); // 3：把人社退回的申请人退回至“不通过”状态，把流程卡住
            iIdentityInfoService.update(identityInfo);
        }
        if (identityInfo.getReservationStatus() == 10 && identityInfo.getPoliceApproveStatus() == 0){
            identityInfo.setReservationStatus(12); // 12：表示窗口申请退回申请人至未审核的代码
            identityInfo.setUnionApproveStatus2(3); // 3：把人社退回的申请人退回至“不通过”状态，把流程卡住
            iIdentityInfoService.update(identityInfo);
        }
        /*
        留痕记录
         */
        PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
        personBatchStatusRecord.setPersonId(identityInfo.getId());
        personBatchStatusRecord.setBatchId(identityInfo.getBatchId());
        personBatchStatusRecord.setPersonIdNumber(identityInfo.getIdNumber());
        personBatchStatusRecord.setStatusTypeDesc("退回至未审核-开始");
        personBatchStatusRecord.setStatusTime(new Date());
        personBatchStatusRecord.setStatusStr(securityUser.getLoginName()+"修改成功");
        if (back == 2){
            personBatchStatusRecord.setStatusReason("修改前的状态："+"人社预审已通过");
        }else if (back == 3){
            personBatchStatusRecord.setStatusReason("修改前的状态："+"人社预审未通过");
        }
        personBatchStatusRecord.setStatusInt(120);
        iPersonBatchStatusRecordService.save(personBatchStatusRecord);
        return ResponseUtil.success();
    }

    /**
     * 2019年10月22日
     * 在审核中心——人社预审——已通过、未通过两个列表中，操作列加入“退回至待审核”按钮，功能为将对应申请人退回至“审核中心——人社预审——待审核”列表。此按钮权限分配给有“审核中心——人社预审”权限的用户。
     * @param id
     * @return
     * @throws IOException
     */
    @PostMapping("/backFinish2")
    public Result backFinish(@RequestParam Integer id) throws IOException {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        identityInfo.setReservationStatus(8);
        identityInfo.setUnionApproveStatus2(1);
        iIdentityInfoService.update(identityInfo);

        /*
        留痕记录
         */
        PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
        personBatchStatusRecord.setPersonId(identityInfo.getId());
        personBatchStatusRecord.setBatchId(identityInfo.getBatchId());
        personBatchStatusRecord.setPersonIdNumber(identityInfo.getIdNumber());
        personBatchStatusRecord.setStatusTypeDesc("退回至未审核-结束");
        personBatchStatusRecord.setStatusTime(new Date());
        personBatchStatusRecord.setStatusStr(securityUser.getLoginName()+"修改成功");
        personBatchStatusRecord.setStatusReason("退回至未审核");
        personBatchStatusRecord.setStatusInt(121);
        iPersonBatchStatusRecordService.save(personBatchStatusRecord);
        return ResponseUtil.success();
    }

    /**
     * 2019年10月22日 退回至未审核，后台通过按钮
     * @param identityInfo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/backFinish")
    @ResponseBody
    public Result backFinish(IdentityInfo identityInfo,
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
//        if (securityUser.getUserType() == 0) {
//            identityInfo.setAcceptAddressId(1);
//        } else if (securityUser.getUserType() == 1) {
//            identityInfo.setAcceptAddressId(2);
//        }
        identityInfo.setReservationStatus(12);
//        identityInfo.setOrderByColumn("reservationDate");
//        identityInfo.setOrderBy("desc");
//        identityInfo.setOrderByColumn("unionApprove2Et,id");
//        identityInfo.setOrderBy("asc");
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
            info.setRegionName(sdf.format(info.getPreApprove()));

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

    @GetMapping(value = "/approved")
    @ResponseBody
    public Result approved(IdentityInfo identityInfo,
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
        if (securityUser.getUserType() == 0) {
            identityInfo.setAcceptAddressId(1);
        } else if (securityUser.getUserType() == 1) {
            identityInfo.setAcceptAddressId(2);
        }
        identityInfo.setUnionApproveStatus2(2);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        List<Integer> companyIds = iIdentityInfoService.selectApprovingRedCompanyId(identityInfo, 5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (IdentityInfo info : pageInfo.getList()) {
            /*
            2019年7月2日
            因浏览器解析时间有8个小时的时差，所以把preApprove 的字段值赋给 regionName
             */
            if (info.getPreApprove()==null){
                info.setRegionName("无");
            }else {
                info.setRegionName(sdf.format(info.getPreApprove()));
            }
            if (companyIds.contains(info.getCompanyId())) {
                info.setCompanyWarning(1);
            }
        }
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/rejected")
    @ResponseBody
    public Result rejected(IdentityInfo identityInfo,
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
        if (securityUser.getUserType() == 0) {
            identityInfo.setAcceptAddressId(1);
        } else if (securityUser.getUserType() == 1) {
            identityInfo.setAcceptAddressId(2);
        }
        identityInfo.setUnionApproveStatus2(3);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        List<Integer> companyIds = iIdentityInfoService.selectApprovingRedCompanyId(identityInfo, 5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (IdentityInfo info : pageInfo.getList()) {
            /*
            2019年7月2日
            因浏览器解析时间有8个小时的时差，所以把preApprove 的字段值赋给 regionName
             */
            if (info.getPreApprove()==null){
                info.setRegionName("无");
            }else {
                info.setRegionName(sdf.format(info.getPreApprove()));
            }
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
        identityInfo.setUnionApproveStatus2(4);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        List<Integer> companyIds = iIdentityInfoService.selectApprovingRedCompanyId(identityInfo, 5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (IdentityInfo info : pageInfo.getList()) {
            /*
            2019年7月2日
            因浏览器解析时间有8个小时的时差，所以把preApprove 的字段值赋给 regionName
             */
            if (info.getPreApprove()==null){
                info.setRegionName("无");
            }else {
                info.setRegionName(sdf.format(info.getPreApprove()));
            }
            if (companyIds.contains(info.getCompanyId())) {
                info.setCompanyWarning(1);
            }
        }
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @PostMapping("/agree")
    public Result agree(@RequestParam Integer id) throws IOException {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (3 == identityInfo.getUnionApproveStatus2()) {
            return ResponseUtil.error("该申请人人社预审驳回，无法进行此操作");
        }
        if (StringUtils.isEmpty(identityInfo.getLockUser2())) {
            return ResponseUtil.error("该申请人的预审状态未被锁定，请先锁定！");
        }
        if (StringUtils.isNotEmpty(identityInfo.getLockUser2()) && !identityInfo.getLockUser2()
                .equals(securityUser.getUsername())) {
            return ResponseUtil.error("该申请人的预审状态已被" + identityInfo.getLockUser2() + "锁定");
        }
        if (identityInfo != null) {
            identityInfo.setUnionApproveStatus2(2);
            identityInfo.setOpuser2(securityUser.getDisplayName());
            identityInfo.setLockUser2("");
            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "unionApproveStatus2", 2);
        }
        identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo.getUnionApproveStatus1() == 2 && identityInfo.getUnionApproveStatus2() == 2) {
            identityInfo.setReservationStatus(10);
            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "reservationStatus", 10);
            HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", identityInfo.getId());
            //            SmsUtil.send(houseOther.getSelfPhone(), "系统提示：" + identityInfo.getName() + "，恭喜您已通过网上预审，下一步可以进行网上预约。");

            /*
            2018年10月29日通过联合预审的申请人短信内容调整：
             */
            //            Condition condition = new Condition(PersonBatchStatusRecord.class);
            //            tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
            //            criteria = condition.createCriteria();
            //            criteria.andEqualTo("statusInt", 10);
            //            criteria.andEqualTo("personId", identityInfo.getId());
            //            List<PersonBatchStatusRecord> pbs = iPersonBatchStatusRecordService.findByCondition(condition);
            PersonBatchStatusRecord pbsr = new PersonBatchStatusRecord();
            pbsr.setStatusInt(10);
            pbsr.setPersonId(identityInfo.getId());
            PersonBatchStatusRecord pbs = iPersonBatchStatusRecordService.getPassPreCheck(pbsr);
            PersonBatchStatusRecord pbs2 = iPersonBatchStatusRecordService.findById(pbs.getId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String passDate = sdf.format(pbs2.getStatusTime());
            if (pbs2.getStatusInt() == 10 && passDate.equals("2018-10-29")) {
                SmsUtil.send(houseOther.getSelfPhone(), identityInfo.getName() + "，恭喜您已通过网上预审，请预约10月30日到窗口提交材料。");
            } else {
                //                SmsUtil.send(houseOther.getSelfPhone(), "系统提示：" + identityInfo.getName() + "，恭喜您已通过网上预审，下一步可以进行网上预约。");
            }
        }

        return ResponseUtil.success();
    }


    /**
     * 2019年6月25日
     * 人社部门申请人待审核阶段，后台窗口人员点击后下载word文档，文档中的内容是申请人的基本信息，用以核对申请人提交的数据；
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/downloadWord")
    public void downloadWord(@RequestParam Integer id, HttpServletResponse response) throws IOException {
        //清空缓存
        response.reset();

        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");

        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", id);
        HouseMove houseMove = iHouseMoveService.findBy("identityInfoId", id);
        HouseProfession profession = iHouseProfessionService.findBy("identityInfoId", id);

        Map<String,Object> datamap = new HashMap<String, Object>();
        datamap.put("idNumber",(identityInfo.getIdNumber()==null) ? "-" : identityInfo.getIdNumber());// 居住证号（身份证号）
        datamap.put("applicantTypeStr",(houseOther.getApplicantType()==null)? "-": houseOther.getApplicantType()); // 申请人类型
        datamap.put("applicationDate", (houseOther.getApplicationDate()==null)?"-":houseOther.getApplicationDate()); // 居住证申领日期
        datamap.put("name", identityInfo.getName()); //姓名
        datamap.put("sex", (identityInfo.getSex()==1)? "男":"女"); // 性别
        datamap.put("nation", identityInfo.getNation()); // 民族
        datamap.put("birthday", identityInfo.getBirthday()); // 出生日期
        datamap.put("politicalStatusStr", (houseOther.getPoliticalStatusStr()==null) ? "-" : houseOther.getPoliticalStatusStr()); // 政治面貌
        datamap.put("marriageStatusStr", (houseMove.getMarriageStatusStr()==null)?"-":houseMove.getMarriageStatusStr()); // 婚姻状况
        datamap.put("cultureDegreeStr", (houseOther.getCultureDegreeStr()==null)?"-":houseOther.getCultureDegreeStr()); // 文化程度
        datamap.put("degreeStr", (houseOther.getDegreeStr()==null)? "-": houseOther.getDegreeStr()); // 学位
        datamap.put("age", identityInfo.getAge());
        datamap.put("professionTypeStr", (profession.getProfessionTypeStr()==null)? "-": profession.getProfessionTypeStr());
        datamap.put("jobNameStr", (profession.getJobNameStr()==null)? "-" : profession.getJobNameStr());
        datamap.put("houseNature", (houseMove.getHouseNatureStr()==null)? "-":houseMove.getHouseNatureStr());
        datamap.put("settledNature", (houseMove.getSettledNatureStr()==null)?"-":houseMove.getSettledNatureStr());
        datamap.put("witnessPhone", (houseMove.getWitness()==null)?"-":houseMove.getWitness());
        datamap.put("companyName", (houseOther.getCompanyName()==null)?"-":houseOther.getCompanyName());
        datamap.put("companyPhone", (houseOther.getCompanyPhone()==null)?"-":houseOther.getCompanyPhone());
        datamap.put("companyAddress", (houseOther.getCompanyAddress()==null)?"-":houseOther.getCompanyAddress());
        datamap.put("selfPhone", (houseOther.getSelfPhone()==null)?"-":houseOther.getSelfPhone());
        datamap.put("socialSecurityPayStr", (houseOther.getSocialSecurityPayStr()==null)?"-":houseOther.getSocialSecurityPayStr());
        datamap.put("jobLevelStr", (profession.getJobLevelStr()==null)? "-" : profession.getJobLevelStr()); // 资格证书级别
        datamap.put("certificateCode", (profession.getCertificateCode()==null) ? "-" : profession.getCertificateCode()); // 证书编号
        datamap.put("issuingAuthority", (profession.getIssuingAuthority()==null)? "-" : profession.getIssuingAuthority()); // 发证机关
        datamap.put("issuingDate", (profession.getIssuingDate()==null)? "-" : profession.getIssuingDate());// 发证日期

        Configuration configuration = new Configuration(new Version("2.3.0"));
        configuration.setDefaultEncoding("utf-8");
        // 根据某个类的相对路径指定目录名
//        configuration.setClassForTemplateLoading(this.getClass(),"");

        configuration.setDirectoryForTemplateLoading(new File("I:/"));

        File outFile = new File("./申请人信息_特朗普_371482.doc");
        Template template = configuration.getTemplate("app_info.ftl","utf-8");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"utf-8"),10240);
        try {
            template.process(datamap,out);
            out.close();

            InputStream fin = null;
            ServletOutputStream out2 = null;
            fin = new FileInputStream(outFile);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");

            /*
            设置浏览器以下载的方式处理该文件名
            将旁边“打印申请人信息”按钮的打印内容保存成word文件下载，文件命名为“申请人信息”+申请人姓名+申请人身份证号码。
             */
            String fileName = URLEncoder.encode("申请人信息_"+identityInfo.getName()+"_"+identityInfo.getIdNumber()+".doc", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+fileName);


            out2 = response.getOutputStream();
            byte[] buffer = new byte[1024];  // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while((bytesToRead = fin.read(buffer)) != -1) {
                out2.write(buffer, 0, bytesToRead);
            }

            fin.close();
            out2.close();

        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }


    @PostMapping("/disAgree")
    public Result disAgree(@RequestParam Integer id,
                           @RequestParam(value = "reasonType", required = false, defaultValue = "其它") String reasonType,
                           @RequestParam(value = "rejectReason", required = false, defaultValue = "") String rejectReason)
            throws IOException {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (StringUtils.isNotEmpty(identityInfo.getLockUser2()) && !identityInfo.getLockUser2()
                .equals(securityUser.getUsername())) {
            return ResponseUtil.error("该申请人的预审状态已被" + identityInfo.getLockUser2() + "锁定");
        }
        if (2 == identityInfo.getUnionApproveStatus2()) {
            return ResponseUtil.error("该申请人人社预审已通过，无法进行此操作");
        }
        if (StringUtils.isEmpty(identityInfo.getLockUser2())) {
            return ResponseUtil.error("该申请人的预审状态未被锁定，请先锁定！");
        }
        if (rejectReason.length()>0){
            rejectReason = rejectReason.replace("\r\n","");
        }
        if (identityInfo != null) {
            identityInfo.setOpuser2(securityUser.getDisplayName());
            identityInfo.setUnionApproveStatus2(3);
            identityInfo.setReservationStatus(9);
            identityInfo.setLockUser2("");
            identityInfo.setRejectReason(reasonType + " " + rejectReason);
            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "unionApproveStatus2", 3);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "reservationStatus", 9);
            HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", identityInfo.getId());
            if (identityInfo.getUnionApproveStatus1() != 3) {
                SmsUtil.send(houseOther.getSelfPhone(), "系统提示：" + identityInfo.getName() + "，您的申请信息网上预审未通过。");
            }

        }
        return ResponseUtil.success();
    }

    @PostMapping("/disAgree2")
    public Result disAgree2(@RequestParam Integer id,
                           @RequestParam(value = "reasonType", required = false, defaultValue = "其它") String reasonType,
                           @RequestParam(value = "rejectReason", required = false, defaultValue = "") String rejectReason)
            throws IOException {

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
            identityInfo.setRejectReason((identityInfo.getRejectReason()==null ? "" : identityInfo.getRejectReason()+";")+reasonType + " " + rejectReason);
            identityInfo.setRensheSupplyDate(new Date());
            iIdentityInfoService.update(identityInfo);
            HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", identityInfo.getId());
            SmsUtil.send(houseOther.getSelfPhone(), "系统提示：您好，您不符合积分受理条件，不予受理，请登录申报单位用户查询本人具体信息。（人社）");
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "hallStatus", 4);
        }
        return ResponseUtil.success();
    }

    @PostMapping("/supply")
    public Result supply(@RequestParam Integer id, @RequestParam("supplyArr") String supplyArr) throws IOException {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo != null) {
            if (StringUtils.isNotEmpty(identityInfo.getLockUser2()) && !identityInfo.getLockUser2()
                    .equals(securityUser.getUsername())) {
                return ResponseUtil.error("该申请人的预审状态已被" + identityInfo.getLockUser2() + "锁定");
            }
            if (2 == identityInfo.getUnionApproveStatus2()) {
                return ResponseUtil.error("该申请人人社预审已通过，无法进行此操作");
            }
            if (3 == identityInfo.getUnionApproveStatus2()) {
                return ResponseUtil.error("该申请人人社预审驳回，无法进行此操作");
            }
            if (StringUtils.isEmpty(identityInfo.getLockUser2())) {
                return ResponseUtil.error("该申请人的预审状态未被锁定，请先锁定！");
            }

            identityInfo.setOpuser2(securityUser.getDisplayName());
            identityInfo.setLockUser2("");

            if (!(StringUtils.isNotEmpty(supplyArr) && JSONArray.parseArray(supplyArr).size() == 1 && ((JSONObject) JSONArray.parseArray(supplyArr).get(0)).getInteger("id") == -1)) {
                identityInfo.setUnionApproveStatus2(4);
            }

            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "unionApproveStatus2", 4, "人社");
        } else {
            return ResponseUtil.error("申请人不存在！");
        }
        if (StringUtils.isNotEmpty(supplyArr)) {
            JSONArray jsonArray = JSONArray.parseArray(supplyArr);
            for (Object o : jsonArray) {
                Integer mId = ((JSONObject) o).getInteger("id");
                //营业执照单独处理
                if (mId == -1) {
                    CompanyInfo companyInfo = iCompanyInfoService.findById(identityInfo.getCompanyId());
                    companyInfo.setBusinessLicenseSrc("");
                    iCompanyInfoService.update(companyInfo);
                    if (jsonArray.size() == 1) {
                        return ResponseUtil.success();
                    } else {
                        continue;
                    }
                }
                String reason = ((JSONObject) o).getString("reason");
                Condition condition = new Condition(OnlinePersonMaterial.class);
                tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
                criteria.andEqualTo("materialInfoId", mId);
                criteria.andEqualTo("personId", identityInfo.getId());
                criteria.andNotEqualTo("status", 2);
                condition.orderBy("id").desc();
                List<OnlinePersonMaterial> materials = iOnlinePersonMaterialService.findByCondition(condition);
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strDate = sdf.format(date);
                if (materials.size() > 0) {
                    OnlinePersonMaterial onlinePersonMaterial = materials.get(0);
                    if (StringUtils.isNotEmpty(onlinePersonMaterial.getReason())) {
                        onlinePersonMaterial.setReason(onlinePersonMaterial.getReason() + "<br/>" + strDate + "-人社预审：" + reason);
                    } else {
                        onlinePersonMaterial.setReason(strDate + "-人社预审：" + reason);
                    }

                    onlinePersonMaterial.setStatus(1);
                    iOnlinePersonMaterialService.update(onlinePersonMaterial);
                } else {
                    OnlinePersonMaterial onlinePersonMaterial = new OnlinePersonMaterial();
                    onlinePersonMaterial.setMaterialInfoId(mId);
                    onlinePersonMaterial.setReason(strDate + "-人社预审：" + reason);
                    onlinePersonMaterial.setStatus(1);
                    onlinePersonMaterial.setcTime(new Date());
                    onlinePersonMaterial.setPersonId(identityInfo.getId());
                    onlinePersonMaterial.setBatchId(identityInfo.getBatchId());
                    iOnlinePersonMaterialService.save(onlinePersonMaterial);
                }
            }
        }
        HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", identityInfo.getId());
        if (identityInfo.getUnionApproveStatus1() != 3) {
            SmsUtil.send(houseOther.getSelfPhone(), "系统提示：" + identityInfo.getName() + "，您所上传的材料未通过居住证积分网上预审，请根据提示尽快补正材料。");
        }

        return ResponseUtil.success();
    }

    @PostMapping("/sendCompanyMsg")
    public Result sendCompanyMsg(@RequestParam String phone, @RequestParam String name) throws IOException {
        //SmsUtil.send(phone, "您单位的两位经办人,只有一人符合条件,请经办人" + name + "于预约日期到窗口办理相关手续。");
        SmsUtil.send(phone, "系统提示,您好,用人单位及经办人信息有误，请及时修改信息。（人社）");
        return ResponseUtil.success();
    }
}
