package com.orange.score.module.score.controller.approve;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.exception.AuthBusinessException;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.database.score.model.OnlinePersonMaterial;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.IIdentityInfoService;
import com.orange.score.module.score.service.IOnlinePersonMaterialService;
import com.orange.score.module.score.service.IPersonBatchStatusRecordService;
import com.orange.score.module.score.service.IScoreRecordService;
import com.orange.score.module.security.SecurityUser;
import com.orange.score.module.security.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return ResponseUtil.success(result);
    }

    @GetMapping(value = "/approving")
    @ResponseBody
    public Result approving(IdentityInfo identityInfo,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        if (securityUser.getUserType() == 0) {
            identityInfo.setAcceptAddressId(1);
        } else if (securityUser.getUserType() == 1) {
            identityInfo.setAcceptAddressId(2);
        }
        identityInfo.setRensheAcceptStatus(1);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/supply")
    @ResponseBody
    public Result supply(IdentityInfo identityInfo,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        if (securityUser.getUserType() == 0) {
            identityInfo.setAcceptAddressId(1);
        } else if (securityUser.getUserType() == 1) {
            identityInfo.setAcceptAddressId(2);
        }
        identityInfo.setRensheAcceptStatus(2);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/approved")
    @ResponseBody
    public Result approved(IdentityInfo identityInfo,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        if (securityUser.getUserType() == 0) {
            identityInfo.setAcceptAddressId(1);
        } else if (securityUser.getUserType() == 1) {
            identityInfo.setAcceptAddressId(2);
        }
        identityInfo.setRensheAcceptStatus(3);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/rejected")
    @ResponseBody
    public Result rejected(IdentityInfo identityInfo,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        if (securityUser.getUserType() == 0) {
            identityInfo.setAcceptAddressId(1);
        } else if (securityUser.getUserType() == 1) {
            identityInfo.setAcceptAddressId(2);
        }
        identityInfo.setRensheAcceptStatus(4);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @PostMapping("/agree")
    public Result agree(@RequestParam Integer id) {
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo.getReservationStatus() == 10) {
            throw new AuthBusinessException("预约已取消");
        }
        if (identityInfo != null) {
            identityInfo.setHallStatus(5);
            identityInfo.setRensheAcceptStatus(3);
            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "hallStatus", 5);
            iScoreRecordService.insertToInitRecords(identityInfo.getBatchId(), identityInfo.getId());
        }
        return ResponseUtil.success();
    }

    @PostMapping("/supply")
    public Result supply(@RequestParam Integer id, @RequestParam("supplyArr") String supplyArr) {
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo.getReservationStatus() == 10) {
            throw new AuthBusinessException("预约已取消");
        }
        if (identityInfo != null) {
            Date now = new Date();
            Date epDate = DateUtil.addDay(now, 3);
            identityInfo.setRensheAcceptSupplyEt(epDate);
            identityInfo.setRensheAcceptStatus(2);
            iIdentityInfoService.update(identityInfo);
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

    @PostMapping("/disAgree")
    public Result disAgree(@RequestParam Integer id) {
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo.getReservationStatus() == 10) {
            throw new AuthBusinessException("预约已取消");
        }
        if (identityInfo != null) {
            identityInfo.setHallStatus(4);
            identityInfo.setRensheAcceptStatus(4);
            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "hallStatus", 4);
        }
        return ResponseUtil.success();
    }

}
