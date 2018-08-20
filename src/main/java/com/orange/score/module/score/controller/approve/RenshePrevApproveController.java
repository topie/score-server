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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for (CompanyInfo companyInfo : companyInfos) {
            companyMap.put(companyInfo.getId(), companyInfo.getCompanyName());
        }
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
        identityInfo.setOrderByColumn("reservationDate");
        identityInfo.setOrderBy("desc");
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);

        List<Integer> companyIds = iIdentityInfoService.selectApprovingRedCompanyId(identityInfo, 5);
        for (IdentityInfo info : pageInfo.getList()) {
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
        if (1 != identityInfo.getUnionApproveStatus2() && 4 != identityInfo.getUnionApproveStatus2()) {
            return ResponseUtil.error("该申请人不是待审核状态或待补件，锁定无效");
        }
        if (StringUtils.isNotEmpty(identityInfo.getLockUser2()) && !identityInfo.getLockUser2()
                .equals(securityUser.getUsername())) {
            return ResponseUtil.error("该申请人的预审状态已被" + identityInfo.getLockUser2() + "锁定");
        }
        identityInfo.setLockUser2(securityUser.getUsername());
        iIdentityInfoService.update(identityInfo);
        return ResponseUtil.success();
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
        for (IdentityInfo info : pageInfo.getList()) {
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
        for (IdentityInfo info : pageInfo.getList()) {
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
            SmsUtil.send(houseOther.getSelfPhone(), "系统提示：" + identityInfo.getName() + "，恭喜您已通过网上预审，下一步可以进行网上预约。");
        }

        return ResponseUtil.success();
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
            SmsUtil.send(houseOther.getSelfPhone(), "系统提示：" + identityInfo.getName() + "，您的申请信息网上预审未通过。");
        }
        return ResponseUtil.success();
    }

    @PostMapping("/supply")
    public Result supply(@RequestParam Integer id, @RequestParam("supplyArr") String supplyArr) throws IOException {
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
        if (3 == identityInfo.getUnionApproveStatus2()) {
            return ResponseUtil.error("该申请人人社预审驳回，无法进行此操作");
        }
        if (StringUtils.isEmpty(identityInfo.getLockUser2())) {
            return ResponseUtil.error("该申请人的预审状态未被锁定，请先锁定！");
        }
        if (identityInfo != null) {
            identityInfo.setOpuser2(securityUser.getDisplayName());
            identityInfo.setLockUser2("");
            identityInfo.setUnionApproveStatus2(4);
            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "unionApproveStatus2", 4);
        } else {
            return ResponseUtil.error("申请人不存在！");
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
                condition.orderBy("id").desc();
                List<OnlinePersonMaterial> materials = iOnlinePersonMaterialService.findByCondition(condition);
                if (materials.size() > 0) {
                    OnlinePersonMaterial onlinePersonMaterial = materials.get(0);
                    onlinePersonMaterial.setReason(reason);
                    onlinePersonMaterial.setStatus(1);
                    iOnlinePersonMaterialService.update(onlinePersonMaterial);
                } else {
                    OnlinePersonMaterial onlinePersonMaterial = new OnlinePersonMaterial();
                    onlinePersonMaterial.setMaterialInfoId(mId);
                    onlinePersonMaterial.setReason(reason);
                    onlinePersonMaterial.setStatus(1);
                    onlinePersonMaterial.setcTime(new Date());
                    onlinePersonMaterial.setPersonId(identityInfo.getId());
                    onlinePersonMaterial.setBatchId(identityInfo.getBatchId());
                    iOnlinePersonMaterialService.save(onlinePersonMaterial);
                }
            }
        }
        HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", identityInfo.getId());
        SmsUtil.send(houseOther.getSelfPhone(), "系统提示：" + identityInfo.getName() + "，您所上传的材料未通过居住证积分网上预审，请根据提示尽快补正材料。");
        return ResponseUtil.success();
    }

    @PostMapping("/sendCompanyMsg")
    public Result sendCompanyMsg(@RequestParam String phone, @RequestParam String name) throws IOException {
        SmsUtil.send(phone, "您单位的两位经办人,只有一人符合条件,请经办人" + name + "于预约日期到窗口办理相关手续。");
        return ResponseUtil.success();
    }
}
