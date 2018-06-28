package com.orange.score.module.score.controller.approve;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.SmsUtil;
import com.orange.score.database.score.model.HouseOther;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.database.score.model.OnlinePersonMaterial;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.IHouseOtherService;
import com.orange.score.module.score.service.IIdentityInfoService;
import com.orange.score.module.score.service.IOnlinePersonMaterialService;
import com.orange.score.module.score.service.IPersonBatchStatusRecordService;
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
        return ResponseUtil.success(result);
    }

    @GetMapping(value = "/approving")
    @ResponseBody
    public Result approving(IdentityInfo identityInfo,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        identityInfo.setUnionApproveStatus2(1);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/approved")
    @ResponseBody
    public Result approved(IdentityInfo identityInfo,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        identityInfo.setUnionApproveStatus2(2);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/rejected")
    @ResponseBody
    public Result rejected(IdentityInfo identityInfo,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        identityInfo.setUnionApproveStatus2(3);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/supply")
    @ResponseBody
    public Result supply(IdentityInfo identityInfo,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        identityInfo.setUnionApproveStatus2(4);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @PostMapping("/agree")
    public Result agree(@RequestParam Integer id) throws IOException {
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo != null) {
            identityInfo.setUnionApproveStatus2(2);
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
    public Result disAgree(@RequestParam Integer id) throws IOException {
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo != null) {
            identityInfo.setUnionApproveStatus2(3);
            identityInfo.setReservationStatus(9);
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
    public Result supply(@RequestParam Integer id, @RequestParam("supplyArr") String supplyArr) {
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo != null) {
            identityInfo.setUnionApproveStatus2(4);
            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "unionApproveStatus2", 4);
        }else{
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
        return ResponseUtil.success();
    }
}
