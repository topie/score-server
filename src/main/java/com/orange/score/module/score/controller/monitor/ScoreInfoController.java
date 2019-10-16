package com.orange.score.module.score.controller.monitor;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.exception.AuthBusinessException;
import com.orange.score.common.tools.freemarker.FreeMarkerUtil;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.core.model.Region;
import com.orange.score.database.score.model.*;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.core.service.IRegionService;
import com.orange.score.module.score.service.*;
import com.orange.score.module.security.SecurityUtil;
import com.orange.score.module.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenJz1012 on 2018-04-08.
 */
@RestController
@RequestMapping("/api/score/monitor/scoreInfo")
public class ScoreInfoController {

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IDictService iDictService;

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
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private IOnlinePersonMaterialService iOnlinePersonMaterialService;

    @Autowired
    private IScoreRecordService iScoreRecordService;

    @Autowired
    private IMaterialAcceptRecordService iMaterialAcceptRecordService;

    @Autowired
    private IIndicatorService iIndicatorService;

    @Autowired
    private IIndicatorItemService iIndicatorItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private IRegionService iRegionService;

    @Autowired
    private IOfficeService iOfficeService;

    @Autowired
    private IPersonBatchStatusRecordService iPersonBatchStatusRecordService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(IdentityInfo identityInfo,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        identityInfo.setHallStatus(9);
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/score/list")
    @ResponseBody
    public Result scored(ScoreRecord scoreRecord,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (scoreRecord.getPersonId() != null) {
            IdentityInfo identityInfo = iIdentityInfoService.findById(scoreRecord.getPersonId());
            criteria.andEqualTo("personId", identityInfo.getId());
            criteria.andEqualTo("batchId", identityInfo.getBatchId());
        }
        PageInfo<ScoreRecord> pageInfo = iScoreRecordService.selectByFilterAndPage(condition, pageNum, pageSize);
        for (ScoreRecord sr : pageInfo.getList()){
            sr.setScoreValue(sr.getScoreValue().setScale(2));
        }
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    /*
    根据person_id获取当前人的总分
     */
    @GetMapping(value =  "/score/SumScoreValue")
    @ResponseBody
    public Result SumScoreValue(ScoreRecord scoreRecord,
                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "100") int pageSize){
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (scoreRecord.getPersonId() != null) {
            IdentityInfo identityInfo = iIdentityInfoService.findById(scoreRecord.getPersonId());
            criteria.andEqualTo("personId", identityInfo.getId());
            criteria.andEqualTo("batchId", identityInfo.getBatchId());
        }
        List<ScoreRecord> scoreRecs = iScoreRecordService.selectByFilter(condition);
        BigDecimal sumScore = new BigDecimal(0);
        for(ScoreRecord sr : scoreRecs){
//            System.out.println(sr.getScoreValue());
            sumScore = sumScore.add(sr.getScoreValue().setScale(2));
        }
        sumScore.setScale(2);
        Map result = new HashMap<>();
        result.put("sumScore",sumScore);
        return ResponseUtil.success(result);
    }

    @GetMapping(value = "/score/formItems")
    @ResponseBody
    public Result scoreFormItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_pb_score_record");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_pb_score_record");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        Map scoreRecordStatus = iDictService.selectMapByAlias("scoreRecordStatus");
        result.put("scoreRecordStatus", scoreRecordStatus);
        return ResponseUtil.success(result);
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_identity_info");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_identity_info");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        Map reservationStatus = iDictService.selectMapByAlias("reservationStatus");
        result.put("reservationStatus", reservationStatus);
        List<CompanyInfo> companyInfos = iCompanyInfoService.findAll();
        Map companyMap = new HashMap();
//        for (CompanyInfo companyInfo : companyInfos) {
//            companyMap.put(companyInfo.getId(), companyInfo.getCompanyName());
//        }
        result.put("companyNames", companyMap);
        /*
        2018年10月12日，xgr
        添加“受理日期”列（通过人社受理审核日期）
         */
        Condition condition = new Condition(PersonBatchStatusRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria = condition.createCriteria();
        criteria.andEqualTo("statusInt", 5);
        List<PersonBatchStatusRecord> personBatchStatusRecords = iPersonBatchStatusRecordService.findByCondition(condition);
        Map personBatcheStatusRecordsMap = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = null;
        for(PersonBatchStatusRecord personBatchStatusRecord : personBatchStatusRecords){
            if(personBatchStatusRecord.getStatusInt() == 5){
                strDate = sdf.format(personBatchStatusRecord.getStatusTime());
                personBatcheStatusRecordsMap.put(personBatchStatusRecord.getPersonId(), strDate);
            }
        }
        result.put("personBatchStatusRecords", personBatcheStatusRecordsMap);
        return ResponseUtil.success(result);
    }

    @GetMapping("/detailAll")
    public Result detailAll(@RequestParam Integer identityInfoId) throws FileNotFoundException {
        Map params = new HashMap();
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) throw new AuthBusinessException("用户未设置角色");
        List<MaterialInfo> materialInfos = iMaterialInfoService.findAll();
        params.put("materialInfos", materialInfos);
        Map mMap = new HashMap();
        for (MaterialInfo materialInfo : materialInfos) {
            mMap.put(materialInfo.getId() + "", materialInfo.getName());
        }
        if (userId == null) throw new AuthBusinessException("用户未登录");
        IdentityInfo person = iIdentityInfoService.findById(identityInfoId);
        if (person == null) {
            person = new IdentityInfo();
        }
        List<Integer> indicatorIds = new ArrayList<>();
        if (roles.contains(1) || roles.contains(3)) {
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
            for (Integer iId : iIds) {
                if (!roleMidSet.contains(iId)) {
                    roleMidSet.add(iId);
                }
            }
        }
        List<MaterialInfo> materialInfoList = iMaterialInfoService.findAll();
        List<MaterialInfo> roleMaterialInfoList = new ArrayList<>();
        for (MaterialInfo materialInfo : materialInfoList) {
            if (roleMidSet.contains(materialInfo.getId())) {
                roleMaterialInfoList.add(materialInfo);
            }
        }
        Condition condition = new Condition(OnlinePersonMaterial.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("personId", person.getId());
        criteria.andEqualTo("batchId", person.getBatchId());
        criteria.andNotEqualTo("status", 2);
        List<OnlinePersonMaterial> uploadMaterialList = iOnlinePersonMaterialService.findByCondition(condition);
        List<OnlinePersonMaterial> roleUploadMaterialList = new ArrayList<>();
        for (OnlinePersonMaterial onlinePersonMaterial : uploadMaterialList) {
            if (roleMidSet.contains(onlinePersonMaterial.getMaterialInfoId())) {
                onlinePersonMaterial
                        .setMaterialInfoName((String) mMap.get(onlinePersonMaterial.getMaterialInfoId() + ""));
                roleUploadMaterialList.add(onlinePersonMaterial);
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
        condition = new Condition(HouseRelationship.class);
        criteria = condition.createCriteria();
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
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "score_info.ftl", params);
        Map result = new HashMap();
        result.put("html", html);
        condition = new Condition(MaterialAcceptRecord.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("personId", identityInfoId);
        criteria.andEqualTo("batchId", person.getBatchId());
        List<MaterialAcceptRecord> materials = iMaterialAcceptRecordService.findByCondition(condition);
        List<Integer> submittedMids = new ArrayList<>();
        Set<Integer> hSet = new HashSet<>();
        for (MaterialAcceptRecord item : materials) {
            if (!hSet.contains(item.getMaterialId())) {
                submittedMids.add(item.getMaterialId());
                hSet.add(item.getMaterialId());
            }
        }
        result.put("cIds", submittedMids);
        return ResponseUtil.success(result);
    }

    @GetMapping("/scoreDetail")
    public Result scoreDetail(@RequestParam Integer identityInfoId, @RequestParam Integer indicatorId)
            throws FileNotFoundException {
        Map params = new HashMap();
        IdentityInfo person = iIdentityInfoService.findById(identityInfoId);
        if (person == null) {
            person = new IdentityInfo();
        }
        params.put("person", person);
        Map result = new HashMap();
        List<Map> scoreList = new ArrayList<>();
        List sCheckList = new ArrayList<>();
        List sTextList = new ArrayList<>();
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("personId", identityInfoId);
        criteria.andEqualTo("batchId", person.getBatchId());
        criteria.andEqualTo("indicatorId", indicatorId);
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
        result.put("sCheckList", sCheckList);
        result.put("sTextList", sTextList);
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "score_detail_info.ftl", params);
        result.put("html", html);
        return ResponseUtil.success(result);
    }
}
