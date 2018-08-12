package com.orange.score.module.score.controller.approve;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.orange.score.common.core.Result;
import com.orange.score.common.exception.AuthBusinessException;
import com.orange.score.common.tools.freemarker.FreeMarkerUtil;
import com.orange.score.common.tools.htmltoword.String2DocConverter;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.CamelUtil;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.date.DateStyle;
import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.database.core.model.Region;
import com.orange.score.database.score.model.*;
import com.orange.score.database.security.model.Role;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.core.service.IRegionService;
import com.orange.score.module.score.service.*;
import com.orange.score.module.score.ws.SOAP3Response;
import com.orange.score.module.score.ws.WebServiceClient;
import com.orange.score.module.security.SecurityUser;
import com.orange.score.module.security.SecurityUtil;
import com.orange.score.module.security.service.RoleService;
import com.orange.score.module.security.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by chenJz1012 on 2018-04-16.
 */
@RestController
@RequestMapping("/api/score/scoreRecord/identityInfo")
public class ScoreRecordIdentityInfoController {

    @Autowired
    private IScoreRecordService iScoreRecordService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IDictService iDictService;

    @Autowired
    private UserService userService;

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Autowired
    private IHouseOtherService iHouseOtherService;

    @Autowired
    private IHouseMoveService iHouseMoveService;

    @Autowired
    private IHouseProfessionService iHouseProfessionService;

    @Autowired
    private IHouseRelationshipService iHouseRelationshipService;

    @Autowired
    private IMaterialInfoService iMaterialInfoService;

    @Autowired
    private IIndicatorService iIndicatorService;

    @Autowired
    private IIndicatorItemService iIndicatorItemService;

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private IOnlinePersonMaterialService iOnlinePersonMaterialService;

    @Autowired
    private IPersonBatchStatusRecordService iPersonBatchStatusRecordService;

    @Autowired
    private IRegionService iRegionService;

    @Autowired
    private IOfficeService iOfficeService;

    @Autowired
    private IBatchConfService iBatchConfService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private IApplyCancelService iApplyCancelService;

    @GetMapping(value = "/scoring")
    @ResponseBody
    public Result scoring(ScoreRecord scoreRecord, @RequestParam(value = "sort_", required = false) String sort_,
            @RequestParam(value = "dateSearch", required = false, defaultValue = "0") Integer dateSearch,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) throw new AuthBusinessException("用户没有任何部门角色");
        if (scoreRecord.getBatchId() == null) {
            BatchConf batchConf = new BatchConf();
            batchConf.setStatus(1);
            List<BatchConf> list = iBatchConfService.selectByFilter(batchConf);
            if (list.size() > 0) {
                scoreRecord.setBatchId(list.get(0).getId());
            }
        }
        Map argMap = new HashMap();
        argMap.put("status", Arrays.asList(1, 3));
        argMap.put("opRoleId", roles);
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser.getUserType() == 0) {
            argMap.put("acceptAddressId", 1);
        } else if (securityUser.getUserType() == 1) {
            argMap.put("acceptAddressId", 2);
        }
        if (StringUtils.isNotEmpty(scoreRecord.getPersonIdNum())) {
            argMap.put("personIdNum", scoreRecord.getPersonIdNum());
        }
        if (StringUtils.isNotEmpty(scoreRecord.getPersonName())) {
            argMap.put("personName", scoreRecord.getPersonName());
        }
        if (scoreRecord.getAcceptDate() != null && dateSearch == 1) {
            argMap.put("acceptDate", DateUtil.DateToString(scoreRecord.getAcceptDate(), DateStyle.YYYY_MM_DD));
        }
        if (scoreRecord.getBatchId() != null) {
            argMap.put("batchId", scoreRecord.getBatchId());
        }
        if (StringUtils.isNotEmpty(sort_)) {
            String[] arr = sort_.split("_");
            if (arr.length == 2) {
                if ("desc".endsWith(arr[1])) {
                    argMap.put("orderBy", CamelUtil.camelToUnderline(arr[0]));
                    argMap.put("orderType", "desc");
                } else {
                    argMap.put("orderBy", CamelUtil.camelToUnderline(arr[0]));
                    argMap.put("orderType", "asc");
                }
            }
        }
        PageInfo<ScoreRecord> pageInfo = iScoreRecordService.selectIdentityInfoByPage(argMap, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));

    }

    @GetMapping(value = "/scored")
    @ResponseBody
    public Result scored(ScoreRecord scoreRecord, @RequestParam(value = "sort_", required = false) String sort_,
            @RequestParam(value = "dateSearch", required = false, defaultValue = "0") Integer dateSearch,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) throw new AuthBusinessException("用户没有任何部门角色");
        if (scoreRecord.getBatchId() == null) {
            BatchConf batchConf = new BatchConf();
            batchConf.setStatus(1);
            List<BatchConf> list = iBatchConfService.selectByFilter(batchConf);
            if (list.size() > 0) {
                scoreRecord.setBatchId(list.get(0).getId());
            }
        }
        Map argMap = new HashMap();
        argMap.put("status", Arrays.asList(4));
        argMap.put("opRoleId", roles);
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser.getUserType() == 0) {
            argMap.put("acceptAddressId", 1);
        } else if (securityUser.getUserType() == 1) {
            argMap.put("acceptAddressId", 2);
        }
        if (StringUtils.isNotEmpty(scoreRecord.getPersonIdNum())) {
            argMap.put("personIdNum", scoreRecord.getPersonIdNum());
        }
        if (StringUtils.isNotEmpty(scoreRecord.getPersonName())) {
            argMap.put("personName", scoreRecord.getPersonName());
        }
        if (scoreRecord.getBatchId() != null) {
            argMap.put("batchId", scoreRecord.getBatchId());
        }
        if (scoreRecord.getAcceptDate() != null && dateSearch == 1) {
            argMap.put("acceptDate", DateUtil.DateToString(scoreRecord.getAcceptDate(), DateStyle.YYYY_MM_DD));
        }
        if (StringUtils.isNotEmpty(sort_)) {
            String[] arr = sort_.split("_");
            if (arr.length == 2) {
                if ("desc".endsWith(arr[1])) {
                    argMap.put("orderBy", CamelUtil.camelToUnderline(arr[0]));
                    argMap.put("orderType", "desc");
                } else {
                    argMap.put("orderBy", CamelUtil.camelToUnderline(arr[0]));
                    argMap.put("orderType", "asc");
                }
            }
        }
        PageInfo<ScoreRecord> pageInfo = iScoreRecordService.selectIdentityInfoByPage(argMap, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    /*审核打分*/
    @GetMapping("/detailAll")
    public Result detailAll(@RequestParam Integer identityInfoId,
            @RequestParam(value = "view", required = false, defaultValue = "0") Integer view)
            throws FileNotFoundException {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(userId);
        Map params = new HashMap();
        params.put("view", view);
        List<Map> scoreList = new ArrayList<>();
        List<ScoreRecord> indicatorIdList = iScoreRecordService
                .selectIndicatorIdsByIdentityInfoIdAndRoleIds(identityInfoId, roles);
        for (ScoreRecord scoreRecord : indicatorIdList) {
            Map msMap = new HashMap();
            msMap.put("scoreRecordId", scoreRecord.getId());
            if (view != 1 && scoreRecord.getStatus() != null && scoreRecord.getStatus() == 4) {
                continue;
            }
            Indicator indicator = iIndicatorService.findById(scoreRecord.getIndicatorId());
            if (indicator == null) {
                continue;
            }
            msMap.put("indicator", indicator);
            Condition condition = new Condition(IndicatorItem.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
            criteria.andEqualTo("indicatorId", scoreRecord.getIndicatorId());
            condition.orderBy("score").desc().orderBy("id").asc();
            List<IndicatorItem> indicatorItems = iIndicatorItemService.findByCondition(condition);
            msMap.put("indicatorItems", indicatorItems);
            msMap.put("roleId", scoreRecord.getOpRoleId());
            msMap.put("opRole", scoreRecord.getOpRole());
            scoreList.add(msMap);
        }
        params.put("scoreList", scoreList);
        IdentityInfo person = iIdentityInfoService.findById(identityInfoId);
        if (person == null) {
            person = new IdentityInfo();
        }
        params.put("person", person);
        CompanyInfo companyInfo = iCompanyInfoService.findById(person.getCompanyId());
        if (companyInfo == null) {
            companyInfo = new CompanyInfo();
        }
        params.put("company", companyInfo);
        HouseOther other = iHouseOtherService.findBy("identityInfoId", identityInfoId);
        if (other == null) {
            other = new HouseOther();
        }
        params.put("other", other);
        HouseProfession profession = iHouseProfessionService.findBy("identityInfoId", identityInfoId);
        if (profession == null) {
            profession = new HouseProfession();
        }
        params.put("profession", profession);
        HouseMove houseMove = iHouseMoveService.findBy("identityInfoId", identityInfoId);
        if (houseMove == null) {
            houseMove = new HouseMove();
        }
        params.put("move", houseMove);
        Condition condition = new Condition(HouseRelationship.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("identityInfoId", identityInfoId);
        List<HouseRelationship> relationshipList = iHouseRelationshipService.findByCondition(condition);
        if (CollectionUtils.isEmpty(relationshipList)) {
            relationshipList = new ArrayList<>();
        }
        params.put("relation", relationshipList);
        condition = new Condition(Region.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("level", 1);
        List<Region> provinceList = iRegionService.findByCondition(condition);
        params.put("provinceList", provinceList);
        condition = new Condition(Region.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("level", 2);
        List<Region> cityList = iRegionService.findByCondition(condition);
        params.put("cityList", cityList);
        condition = new Condition(Region.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("level", 3);
        List<Region> areaList = iRegionService.findByCondition(condition);
        params.put("areaList", areaList);

        condition = new Condition(Office.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("regionLevel", 1);
        List<Office> officeList1 = iOfficeService.findByCondition(condition);
        params.put("officeList1", officeList1);

        condition = new Condition(Office.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("regionLevel", 2);
        List<Office> officeList2 = iOfficeService.findByCondition(condition);
        params.put("officeList2", officeList2);
        List<MaterialInfo> materialInfos = iMaterialInfoService.findAll();
        Map mMap = new HashMap();
        for (MaterialInfo materialInfo : materialInfos) {
            mMap.put(materialInfo.getId() + "", materialInfo.getName());
        }
        List<Integer> indicatorIds = new ArrayList<>();
        if (roles.contains(1)) {
            List<Indicator> indicators = iIndicatorService.findAll();
            for (Indicator item : indicators) {
                indicatorIds.add(item.getId());
            }
        } else {
            indicatorIds = iIndicatorService.selectIndicatorIdByRoleIds(roles);
        }
        Set<Integer> roleMidSet = new HashSet<>();
        for (Integer itemId : indicatorIds) {
            List<Integer> iIds = iIndicatorService.selectBindMaterialIds(itemId);
            roleMidSet.addAll(iIds);
        }
        List<MaterialInfo> materialInfoList = iMaterialInfoService.findAll();
        List<MaterialInfo> roleMaterialInfoList = new ArrayList<>();
        for (MaterialInfo materialInfo : materialInfoList) {
            if (roles.contains(3) || roles.contains(4)) {
                if (materialInfo.getIsUpload() == 1) {
                    if (roleMidSet.contains(materialInfo.getId())) {
                        roleMaterialInfoList.add(materialInfo);
                    }
                }
                //公安单独处理随迁信息
                if (roles.contains(4) && Arrays.asList(1011, 1017, 1013, 1014, 17).contains(materialInfo.getId())) {
                    roleMaterialInfoList.add(materialInfo);
                }
            }
        }
        condition = new Condition(OnlinePersonMaterial.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("personId", person.getId());
        criteria.andEqualTo("batchId", person.getBatchId());
        List<OnlinePersonMaterial> uploadMaterialList = iOnlinePersonMaterialService.findByCondition(condition);
        List<OnlinePersonMaterial> roleUploadMaterialList = new ArrayList<>();
        for (OnlinePersonMaterial onlinePersonMaterial : uploadMaterialList) {
            onlinePersonMaterial.setMaterialInfoName((String) mMap.get(onlinePersonMaterial.getMaterialInfoId() + ""));
            if (roles.contains(3) || roles.contains(4)) {
                if (roleMidSet.contains(onlinePersonMaterial.getMaterialInfoId())) {
                    onlinePersonMaterial
                            .setMaterialInfoName((String) mMap.get(onlinePersonMaterial.getMaterialInfoId() + ""));
                    roleUploadMaterialList.add(onlinePersonMaterial);
                }
                //公安单独处理随迁信息
                if (roles.contains(4) && Arrays.asList(1011, 1017, 1013, 1014, 17)
                        .contains(onlinePersonMaterial.getId())) {
                    roleUploadMaterialList.add(onlinePersonMaterial);
                }
            }
        }
        params.put("onlinePersonMaterials", roleUploadMaterialList);
        for (MaterialInfo materialInfo : roleMaterialInfoList) {
            for (OnlinePersonMaterial onlinePersonMaterial : roleUploadMaterialList) {
                if (materialInfo.getId().intValue() == onlinePersonMaterial.getMaterialInfoId().intValue()) {
                    materialInfo.setOnlinePersonMaterial(onlinePersonMaterial);
                }
            }
        }
        params.put("materialInfos", roleMaterialInfoList);

        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "score_record_identity.ftl", params);
        Map result = new HashMap();
        List<String> sCheckList = new ArrayList<>();
        List<String> sTextList = new ArrayList<>();
        condition = new Condition(ScoreRecord.class);
        criteria = condition.createCriteria();
        criteria.andIn("opRoleId", roles);
        criteria.andEqualTo("personId", identityInfoId);
        criteria.andEqualTo("batchId", person.getBatchId());
        List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);
        for (ScoreRecord item : scoreRecords) {
            if (item.getStatus() == 4) {
                Indicator indicator1 = iIndicatorService.findById(item.getIndicatorId());
                if (indicator1.getItemType() == 0) {
                    sCheckList.add(item.getIndicatorId() + "_" + item.getItemId() + "_" + item.getOpRoleId());
                } else {
                    sTextList.add(item.getIndicatorId() + "_" + item.getScoreValue() + "_" + item.getOpRoleId());
                }
            }
        }
        result.put("sCheckList", sCheckList);
        result.put("sTextList", sTextList);
        result.put("html", html);
        return ResponseUtil.success(result);
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_pb_score_record");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_pb_score_record");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        Map scoreRecordStatus = iDictService.selectMapByAlias("scoreRecordStatus");
        result.put("scoreRecordStatus", scoreRecordStatus);
        return ResponseUtil.success(result);
    }

    @PostMapping("/score")
    public Result score(@RequestParam("personId") Integer personId,
            @RequestParam(value = "sIds", required = false) String[] sIds,
            @RequestParam(value = "sAns", required = false) String[] sAns,
            @RequestParam(value = "sDetails", required = false) String[] sDetails) {
        SecurityUser user = SecurityUtil.getCurrentSecurityUser();
        Integer userId = user.getId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) throw new AuthBusinessException("用户未设置角色");
        IdentityInfo person = iIdentityInfoService.findById(personId);
        if (person.getHallStatus() == 8) {
            throw new AuthBusinessException("资格已取消");
        }
        Map<String, String> detailMap = new HashMap<>();
        if (sDetails != null) {
            for (String sDetail : sDetails) {
                String[] arr = sDetail.split("_");
                if (arr.length != 3) continue;
                Integer indicatorId = Integer.valueOf(arr[0]);
                Integer roleId = Integer.valueOf(arr[2]);
                detailMap.put(indicatorId + "_" + roleId, arr[1]);
            }
        }
        if (sAns != null) {
            for (String sAn : sAns) {
                String[] sAnArr = sAn.split("_");
                if (sAnArr.length != 3) continue;
                Integer indicatorId = Integer.valueOf(sAnArr[0]);
                BigDecimal scoreValue = BigDecimal.valueOf(Float.valueOf(sAnArr[1]));
                Integer roleId = Integer.valueOf(sAnArr[2]);
                Condition condition = new Condition(ScoreRecord.class);
                tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
                criteria.andEqualTo("indicatorId", indicatorId);
                criteria.andEqualTo("personId", personId);
                criteria.andEqualTo("batchId", person.getBatchId());
                criteria.andEqualTo("opRoleId", roleId);
                List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);
                for (ScoreRecord scoreRecord : scoreRecords) {
                    scoreRecord.setItemId(0);
                    scoreRecord.setScoreDate(new Date());
                    scoreRecord.setOpUserId(userId);
                    scoreRecord.setOpUser(user.getDisplayName());
                    scoreRecord.setScoreValue(scoreValue);
                    scoreRecord.setStatus(4);
                    if (StringUtils.isNotEmpty(
                            detailMap.get(scoreRecord.getIndicatorId() + "_" + scoreRecord.getOpRoleId()))) {
                        scoreRecord.setScoreDetail(
                                detailMap.get(scoreRecord.getIndicatorId() + "_" + scoreRecord.getOpRoleId()));
                    }
                    iScoreRecordService.update(scoreRecord);
                }
            }
        }
        if (sIds != null) {
            for (String sId : sIds) {
                String[] sIdArr = sId.split("_");
                if (sIdArr.length != 3) continue;
                Integer indicatorId = Integer.valueOf(sIdArr[0]);
                Integer itemId = Integer.valueOf(sIdArr[1]);
                Integer roleId = Integer.valueOf(sIdArr[2]);
                Condition condition = new Condition(ScoreRecord.class);
                tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
                criteria.andEqualTo("indicatorId", indicatorId);
                criteria.andEqualTo("personId", personId);
                criteria.andEqualTo("batchId", person.getBatchId());
                criteria.andEqualTo("opRoleId", roleId);
                List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);
                for (ScoreRecord scoreRecord : scoreRecords) {
                    scoreRecord.setItemId(itemId);
                    scoreRecord.setScoreDate(new Date());
                    scoreRecord.setOpUserId(userId);
                    scoreRecord.setOpUser(user.getDisplayName());
                    if (StringUtils.isNotEmpty(
                            detailMap.get(scoreRecord.getIndicatorId() + "_" + scoreRecord.getOpRoleId()))) {
                        scoreRecord.setScoreDetail(
                                detailMap.get(scoreRecord.getIndicatorId() + "_" + scoreRecord.getOpRoleId()));
                    }
                    if (itemId == 0) {
                        scoreRecord.setScoreValue(BigDecimal.ZERO);
                    } else {
                        IndicatorItem item = iIndicatorItemService.findById(itemId);
                        scoreRecord.setScoreValue(BigDecimal.valueOf(item.getScore()));
                    }
                    scoreRecord.setStatus(4);
                    iScoreRecordService.update(scoreRecord);
                    if (scoreRecord.getIndicatorId() == 1003 && scoreRecord.getOpRoleId() == 12
                            && scoreRecord.getItemId() == 1018) {
                        IdentityInfo identityInfo = iIdentityInfoService.findById(scoreRecord.getPersonId());
                        ApplyCancel applyCancel = new ApplyCancel();
                        applyCancel.setBatchId(identityInfo.getBatchId());
                        applyCancel.setPersonId(scoreRecord.getPersonId());
                        applyCancel.setPersonIdNumber(identityInfo.getIdNumber());
                        applyCancel.setApplyUserId(user.getId());
                        applyCancel.setApplyUserType(user.getUserType());
                        applyCancel.setApplyUser(user.getDisplayName());
                        applyCancel.setApplyRoleId(scoreRecord.getOpRoleId());
                        applyCancel.setApplyRole(scoreRecord.getOpRole());
                        applyCancel.setApproveStatus(0);
                        if (StringUtils.isNotEmpty(
                                detailMap.get(scoreRecord.getIndicatorId() + "_" + scoreRecord.getOpRoleId()))) {
                            applyCancel.setApplyReason(
                                    detailMap.get(scoreRecord.getIndicatorId() + "_" + scoreRecord.getOpRoleId()));
                        } else {
                            applyCancel.setApplyReason(
                                    scoreRecord.getOpRole() + " 指标[" + scoreRecord.getIndicatorName() + "]打分自动申请取消资格");
                        }
                        iApplyCancelService.save(applyCancel);
                    }
                }

            }
        }

        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("personId", personId);
        criteria.andEqualTo("batchId", person.getBatchId());
        criteria.andNotEqualTo("status", 4);
        List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);
        if (scoreRecords.size() == 0) {
            person.setHallStatus(9);
            iIdentityInfoService.update(person);
            iPersonBatchStatusRecordService.insertStatus(person.getBatchId(), person.getId(), "hallStatus", 9);
        }
        return ResponseUtil.success();
    }

    @PostMapping("/rensheAutoScore")
    public Result rensheAutoScore(@RequestParam(value = "personId", required = false) Integer personId)
            throws DocumentException, SOAPException, IOException {
        IdentityInfo identityInfo = iIdentityInfoService.findById(personId);
        Integer lessThan35 = 1;
        if (identityInfo.getAge() > 35) {
            lessThan35 = 0;
        }
        String req = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" \n"
                + "xmlns:ser=\"http://service.webinterface.yzym.si.sl.neusoft.com/\">\n" + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n" + "      <ser:RsResidentJFRDBusinessRev>\n" + "         <!--ticket:-->\n"
                + "         <ser:arg0>NEUSERVICE_GGFW_TICKET_12</ser:arg0>\n" + "         <!--buzzNumb:-->\n"
                + "         <ser:arg1>TJZSYL_JFRDXT_003</ser:arg1>\n" + "         <!--sender:-->\n"
                + "         <ser:arg2>JFRDXT</ser:arg2>\n" + "         <!--reciver:-->\n"
                + "         <ser:arg3>TJZSYL</ser:arg3>\n" + "         <!--operatorName:-->\n"
                + "         <ser:arg4>自动打分操作员</ser:arg4>\n" + "         <!--content:-->\n"
                + "         <ser:arg5><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<ROOT><QUERY_PRAMS><idNumber>" + identityInfo.getIdNumber() + "</idNumber>"
                + "<partnerIdNnumber></partnerIdNnumber>" + "<lessThan35>" + lessThan35 + "</lessThan35>"
                + "<canAdd>1</canAdd>" + "<busType>3</busType>" + "</QUERY_PRAMS></ROOT>]]></ser:arg5>\n"
                + "</ser:RsResidentJFRDBusinessRev></soapenv:Body></soapenv:Envelope>";
        SOAP3Response soapResponse = WebServiceClient.action(req);
        return ResponseUtil.success(soapResponse);
    }

    @GetMapping(value = "/approveDoc")
    @ResponseBody
    public Result approveDoc(@RequestParam Integer identityInfoId) throws FileNotFoundException {
        Map params = new HashMap();
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo person = iIdentityInfoService.findById(identityInfoId);
        if (person == null) {
            person = new IdentityInfo();
        }
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(userId);

        params.put("person", person);
        CompanyInfo companyInfo = iCompanyInfoService.findById(person.getCompanyId());
        if (companyInfo == null) {
            companyInfo = new CompanyInfo();
        }
        params.put("company", companyInfo);
        HouseOther other = iHouseOtherService.findBy("identityInfoId", identityInfoId);
        if (other == null) {
            other = new HouseOther();
        }
        params.put("other", other);
        HouseProfession profession = iHouseProfessionService.findBy("identityInfoId", identityInfoId);
        if (profession == null) {
            profession = new HouseProfession();
        }
        params.put("profession", profession);
        HouseMove houseMove = iHouseMoveService.findBy("identityInfoId", identityInfoId);
        if (houseMove == null) {
            houseMove = new HouseMove();
        }
        params.put("move", houseMove);
        Condition condition = new Condition(HouseRelationship.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("identityInfoId", identityInfoId);
        List<HouseRelationship> relationshipList = iHouseRelationshipService.findByCondition(condition);
        if (CollectionUtils.isEmpty(relationshipList)) {
            relationshipList = new ArrayList<>();
        }
        params.put("relation", relationshipList);
        List<Map> scoreList = new ArrayList<>();
        List sCheckList = new ArrayList<>();
        List sTextList = new ArrayList<>();
        condition = new Condition(ScoreRecord.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("personId", identityInfoId);
        criteria.andEqualTo("batchId", person.getBatchId());
        criteria.andIn("opRoleId", roles);
        List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);
        Map scoreRecordStatus = iDictService.selectMapByAlias("scoreRecordStatus");
        for (ScoreRecord item : scoreRecords) {
            Map itemMap = new HashMap();
            Indicator indicator = iIndicatorService.findById(item.getIndicatorId());
            itemMap.put("indicator", indicator);
            condition = new Condition(IndicatorItem.class);
            criteria = condition.createCriteria();
            criteria.andEqualTo("indicatorId", item.getIndicatorId());
            List<IndicatorItem> indicatorItems = iIndicatorItemService.findByCondition(condition);
            itemMap.put("indicatorItems", indicatorItems);
            itemMap.put("opUser", item.getOpUser());
            itemMap.put("opRole", item.getOpRole());
            itemMap.put("opRoleId", item.getOpRoleId());
            itemMap.put("scoreValue", item.getScoreValue());
            itemMap.put("scoreStatus", scoreRecordStatus.get(item.getStatus()));
            itemMap.put("submitDate", item.getSubmitDate());
            scoreList.add(itemMap);
            if (item.getStatus() == 4) {
                Indicator indicator1 = iIndicatorService.findById(item.getIndicatorId());
                if (indicator1.getItemType() == 0) {
                    sCheckList.add(item.getOpRoleId() + "_" + item.getIndicatorId() + "_" + item.getItemId());
                } else {
                    sTextList.add(item.getOpRoleId() + "_" + item.getIndicatorId() + "_" + item.getScoreValue());
                }
            }
        }
        params.put("scoreList", scoreList);
        List<String> departmentNames = new ArrayList<>();
        for (Integer roleId : roles) {
            Role role = roleService.findRoleById(roleId);
            departmentNames.add(role.getRoleName());
        }
        params.put("department", Joiner.on("、").join(departmentNames));
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "approve_doc.ftl", params);
        Map result = new HashMap<>();
        result.put("html", html);
        return ResponseUtil.success(result);
    }

    @GetMapping(value = "/export/approveDoc")
    @ResponseBody
    public void exportApproveDoc(HttpServletResponse response, @RequestParam Integer identityInfoId) throws Exception {
        Map params = new HashMap();
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo person = iIdentityInfoService.findById(identityInfoId);
        if (person == null) {
            person = new IdentityInfo();
        }
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(userId);

        params.put("person", person);
        CompanyInfo companyInfo = iCompanyInfoService.findById(person.getCompanyId());
        if (companyInfo == null) {
            companyInfo = new CompanyInfo();
        }
        params.put("company", companyInfo);
        HouseOther other = iHouseOtherService.findBy("identityInfoId", identityInfoId);
        if (other == null) {
            other = new HouseOther();
        }
        params.put("other", other);
        HouseProfession profession = iHouseProfessionService.findBy("identityInfoId", identityInfoId);
        if (profession == null) {
            profession = new HouseProfession();
        }
        params.put("profession", profession);
        HouseMove houseMove = iHouseMoveService.findBy("identityInfoId", identityInfoId);
        if (houseMove == null) {
            houseMove = new HouseMove();
        }
        params.put("move", houseMove);
        Condition condition = new Condition(HouseRelationship.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("identityInfoId", identityInfoId);
        List<HouseRelationship> relationshipList = iHouseRelationshipService.findByCondition(condition);
        if (CollectionUtils.isEmpty(relationshipList)) {
            relationshipList = new ArrayList<>();
        }
        params.put("relation", relationshipList);
        List<Map> scoreList = new ArrayList<>();
        List sCheckList = new ArrayList<>();
        List sTextList = new ArrayList<>();
        condition = new Condition(ScoreRecord.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("personId", identityInfoId);
        criteria.andEqualTo("batchId", person.getBatchId());
        criteria.andIn("opRoleId", roles);
        List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);
        Map scoreRecordStatus = iDictService.selectMapByAlias("scoreRecordStatus");
        for (ScoreRecord item : scoreRecords) {
            Map itemMap = new HashMap();
            Indicator indicator = iIndicatorService.findById(item.getIndicatorId());
            itemMap.put("indicator", indicator);
            condition = new Condition(IndicatorItem.class);
            criteria = condition.createCriteria();
            criteria.andEqualTo("indicatorId", item.getIndicatorId());
            List<IndicatorItem> indicatorItems = iIndicatorItemService.findByCondition(condition);
            itemMap.put("indicatorItems", indicatorItems);
            itemMap.put("opUser", item.getOpUser());
            itemMap.put("opRole", item.getOpRole());
            itemMap.put("opRoleId", item.getOpRoleId());
            itemMap.put("scoreValue", item.getScoreValue());
            itemMap.put("scoreStatus", scoreRecordStatus.get(item.getStatus()));
            itemMap.put("submitDate", item.getSubmitDate());
            scoreList.add(itemMap);
            if (item.getStatus() == 4) {
                Indicator indicator1 = iIndicatorService.findById(item.getIndicatorId());
                if (indicator1.getItemType() == 0) {
                    sCheckList.add(item.getOpRoleId() + "_" + item.getIndicatorId() + "_" + item.getItemId());
                } else {
                    sTextList.add(item.getOpRoleId() + "_" + item.getIndicatorId() + "_" + item.getScoreValue());
                }
            }
        }
        params.put("scoreList", scoreList);
        List<String> departmentNames = new ArrayList<>();
        for (Integer roleId : roles) {
            Role role = roleService.findRoleById(roleId);
            departmentNames.add(role.getRoleName());
        }
        params.put("department", Joiner.on("、").join(departmentNames));
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "approve_doc.ftl", params);
        String tmpName = System.currentTimeMillis() + ".doc";
        String2DocConverter string2DocConverter = new String2DocConverter(html, "/tmp/" + tmpName);
        string2DocConverter.writeWordFile();
        string2DocConverter.download(response, tmpName);
    }

    @PostMapping("/insert")
    public Result insert(ScoreRecord scoreRecord) {
        iScoreRecordService.save(scoreRecord);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iScoreRecordService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(ScoreRecord scoreRecord) {
        iScoreRecordService.update(scoreRecord);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        ScoreRecord scoreRecord = iScoreRecordService.findById(id);
        return ResponseUtil.success(scoreRecord);
    }

    @PostMapping("/complete")
    public Result complete(@RequestParam Integer id) {
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        identityInfo.setHallStatus(9);
        iIdentityInfoService.update(identityInfo);
        return ResponseUtil.success();
    }
}
