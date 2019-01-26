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
@RequestMapping("/api/score/approve/policeApprove")
public class PoliceApproveController {

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
    private IHouseMoveService iHouseMoveService;

    @Autowired
    private ILuohuService iLuohuService;

    @Autowired
    private ILuohu2Service iLuohu2Service;

    @Autowired
    private ILuohu3Service iLuohu3Service;

    @Autowired
    private ILuohu4Service iLuohu4Service;

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
        Map policeApproveStatus = iDictService.selectMapByAlias("policeApproveStatus");
        result.put("policeApproveStatus", policeApproveStatus);
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
        identityInfo.setPoliceApproveStatus(1);
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
        identityInfo.setPoliceApproveStatus(2);
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
        identityInfo.setPoliceApproveStatus(3);
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
        identityInfo.setPoliceApproveStatus(4);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @PostMapping("/agree")
    public Result agree(@RequestParam Integer id) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo.getReservationStatus() == 10) {
            throw new AuthBusinessException("预约已取消");
        }
        if (identityInfo != null) {
            if (4 == identityInfo.getPoliceApproveStatus()) {
                return ResponseUtil.error("该申请人已被公安前置不通过，无法进行该操作");
            }
            identityInfo.setOpuser3(securityUser.getDisplayName());
            identityInfo.setHallStatus(2);
            identityInfo.setPoliceApproveStatus(3);
            identityInfo.setRensheAcceptStatus(1);
            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "hallStatus", 2);
            identityInfo.setHallStatus(3);
            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "hallStatus", 3);
            HouseMove houseMove = iHouseMoveService.findBy("identityInfoId", identityInfo.getId());
            Integer type = 1;
            if (identityInfo.getAcceptAddressId() == 1 && houseMove.getRegion() != 33) {
                type = 1;
                Luohu luohu = new Luohu();
                luohu.setPersonId(identityInfo.getId());
                luohu.setType(type);
                iLuohuService.save(luohu);
                String number = String.format("%07d", luohu.getId());
                identityInfo.setLuohuNumber(type + number);
            }

            if (identityInfo.getAcceptAddressId() == 2 && houseMove.getRegion() != 33) {
                type = 2;
                Luohu2 luohu2 = new Luohu2();
                luohu2.setPersonId(identityInfo.getId());
                luohu2.setType(type);
                iLuohu2Service.save(luohu2);
                String number = String.format("%07d", luohu2.getId());
                identityInfo.setLuohuNumber(type + number);
            }

            if (identityInfo.getAcceptAddressId() == 1 && houseMove.getRegion() == 33) {
                type = 3;
                Luohu3 luohu3 = new Luohu3();
                luohu3.setPersonId(identityInfo.getId());
                luohu3.setType(type);
                iLuohu3Service.save(luohu3);
                String number = String.format("%07d", luohu3.getId());
                identityInfo.setLuohuNumber(type + number);
            }

            if (identityInfo.getAcceptAddressId() == 2 && houseMove.getRegion() == 33) {
                type = 4;
                Luohu4 luohu4 = new Luohu4();
                luohu4.setPersonId(identityInfo.getId());
                luohu4.setType(type);
                iLuohu4Service.save(luohu4);
                String number = String.format("%07d", luohu4.getId());
                identityInfo.setLuohuNumber(type + number);
            }

            iIdentityInfoService.update(identityInfo);
        }
        return ResponseUtil.success();
    }

    @PostMapping("/supply")
    public Result supply(@RequestParam Integer id, @RequestParam("supplyArr") String supplyArr) throws IOException {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo.getReservationStatus() == 10) {
            throw new AuthBusinessException("预约已取消");
        }
        if (identityInfo != null) {
            if (3 == identityInfo.getPoliceApproveStatus()) {
                return ResponseUtil.error("该申请人已被公安前置通过，无法进行该操作");
            }
            if (4 == identityInfo.getPoliceApproveStatus()) {
                return ResponseUtil.error("该申请人已被公安前置不通过，无法进行该操作");
            }
            identityInfo.setOpuser3(securityUser.getDisplayName());
            Date now = new Date();
            Date epDate = DateUtil.addDay(now, 3);
            identityInfo.setPoliceApproveStatus(2);
            identityInfo.setPoliceApproveEt(epDate);
            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "policeApproveStatus", 2);
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
                if (materials.size() > 0) {
                    OnlinePersonMaterial onlinePersonMaterial = materials.get(0);
                    if(StringUtils.isNotEmpty(onlinePersonMaterial.getReason())){
                        onlinePersonMaterial.setReason(onlinePersonMaterial.getReason()+"<br/>"+"公安前置审核："+reason);
                    }else {
                        onlinePersonMaterial.setReason("公安前置审核："+reason);
                    }
                    onlinePersonMaterial.setStatus(1);
                    iOnlinePersonMaterialService.update(onlinePersonMaterial);
                } else {
                    OnlinePersonMaterial onlinePersonMaterial = new OnlinePersonMaterial();
                    onlinePersonMaterial.setMaterialInfoId(mId);
                    onlinePersonMaterial.setReason("公安前置审核："+reason);
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

    @PostMapping("/disAgree")
    public Result disAgree(@RequestParam Integer id) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        if (identityInfo.getReservationStatus() == 10) {
            throw new AuthBusinessException("预约已取消");
        }
        if (identityInfo != null) {
            if (3 == identityInfo.getPoliceApproveStatus()) {
                return ResponseUtil.error("该申请人已被公安前置通过，无法进行该操作");
            }
            identityInfo.setOpuser3(securityUser.getDisplayName());
            identityInfo.setPoliceApproveStatus(4);
            identityInfo.setHallStatus(1);
            iIdentityInfoService.update(identityInfo);
            iPersonBatchStatusRecordService
                    .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "hallStatus", 1);

        }
        return ResponseUtil.success();
    }

}
