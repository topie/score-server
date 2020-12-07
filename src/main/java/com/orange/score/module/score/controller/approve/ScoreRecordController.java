package com.orange.score.module.score.controller.approve;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.exception.AuthBusinessException;
import com.orange.score.common.tools.excel.ExcelFileUtil;
import com.orange.score.common.tools.freemarker.FreeMarkerUtil;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.CollectionUtil;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.database.core.model.Region;
import com.orange.score.database.score.model.*;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.core.service.IRegionService;
import com.orange.score.module.score.service.*;
import com.orange.score.module.score.ws.SOAP3Response;
import com.orange.score.module.score.ws.WebServiceClient;
import com.orange.score.module.security.SecurityUser;
import com.orange.score.module.security.SecurityUtil;
import com.orange.score.module.security.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenJz1012 on 2018-04-16.
 */
@RestController
@RequestMapping("/api/score/scoreRecord")
public class ScoreRecordController {

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
    private IApplyCancelService iApplyCancelService;

    @Value("${upload.folder}")
    private String uploadPath;

    @GetMapping(value = "/scoring")
    @ResponseBody
    public Result scoring(ScoreRecord scoreRecord, @RequestParam(value = "sort_", required = false) String sort_,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        Condition condition = new Condition(IdentityInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("acceptAddressId", 2);
        if (scoreRecord.getBatchId() != null) {
            criteria.andEqualTo("batchId", scoreRecord.getBatchId());
        }
        List<IdentityInfo> identityInfoList = iIdentityInfoService.findByCondition(condition);
        Set<Integer> binhaiIds = new HashSet<>();
        for (IdentityInfo identityInfo : identityInfoList) {
            binhaiIds.add(identityInfo.getId());
        }
        condition = new Condition(ScoreRecord.class);
        criteria = condition.createCriteria();
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
        criteria.andIn("opRoleId", roles);
        Integer[] integers = new Integer[] { 1, 3 };
        criteria.andIn("status", CollectionUtils.arrayToList(integers));
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser.getUserType() == 0) {
            criteria.andEqualTo("acceptAddressId", 1);
        } else if (securityUser.getUserType() == 1) {
            criteria.andEqualTo("acceptAddressId", 2);
        }
        if (StringUtils.isNotEmpty(scoreRecord.getPersonIdNum())) {
            criteria.andEqualTo("personIdNum", scoreRecord.getPersonIdNum());
        }
        if (scoreRecord.getBatchId() != null) {
            criteria.andEqualTo("batchId", scoreRecord.getBatchId());
        }
        if (scoreRecord.getIndicatorId() != null) {
            criteria.andEqualTo("indicatorId", scoreRecord.getIndicatorId());
        }
        if (StringUtils.isNotEmpty(sort_)) {
            String[] arr = sort_.split("_");
            if (arr.length == 2) {
                if ("desc".endsWith(arr[1])) {
                    condition.orderBy(arr[0]).desc();
                } else {
                    condition.orderBy(arr[0]).asc();
                }
            }
        } else {
            condition.orderBy("scoreDate").desc();
        }
        PageInfo<ScoreRecord> pageInfo = iScoreRecordService.selectByFilterAndPage(condition, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/scoringForRenshe")
    @ResponseBody
    public Result scoringForRenshe(ScoreRecord scoreRecord,
                                   @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        Condition condition = new Condition(IdentityInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("acceptAddressId", 2);
        if (scoreRecord.getBatchId() != null) {
            criteria.andEqualTo("batchId", scoreRecord.getBatchId());
        }
        List<IdentityInfo> identityInfoList = iIdentityInfoService.findByCondition(condition);
        Set<Integer> binhaiIds = new HashSet<>();
        for (IdentityInfo identityInfo : identityInfoList) {
            binhaiIds.add(identityInfo.getId());
        }
        condition = new Condition(ScoreRecord.class);
        criteria = condition.createCriteria();
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
        criteria.andIn("opRoleId", roles);
        Integer[] integers = new Integer[] { 1, 3 };
        criteria.andIn("status", CollectionUtils.arrayToList(integers));
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser.getUserType() == 0) {
            criteria.andEqualTo("acceptAddressId", 1);
        } else if (securityUser.getUserType() == 1) {
            criteria.andEqualTo("acceptAddressId", 2);
        }
        if (StringUtils.isNotEmpty(scoreRecord.getPersonIdNum())) {
            criteria.andEqualTo("personIdNum", scoreRecord.getPersonIdNum());
        }
        if (scoreRecord.getBatchId() != null) {
            criteria.andEqualTo("batchId", scoreRecord.getBatchId());
        }
        if (scoreRecord.getIndicatorId() != null) {
            criteria.andEqualTo("indicatorId", scoreRecord.getIndicatorId());
        }
        PageInfo<ScoreRecord> pageInfo = iScoreRecordService.selectByFilterAndPage(condition, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/scored")
    @ResponseBody
    public Result scored(ScoreRecord scoreRecord, @RequestParam(value = "sort_", required = false) String sort_,
                         @RequestParam(value = "dateSearch") int dateSearch,
                         @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {

        Date date = scoreRecord.getScoreDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String scoreRecordStr = sdf.format(date);// 字符串格式的打分日期字段值
        Date date2 = DateUtil.addDay(date,1);
        String scoreRecordStr2 = sdf.format(date2);

        Condition condition = new Condition(IdentityInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("acceptAddressId", 2);
        if (scoreRecord.getBatchId() != null) {
            criteria.andEqualTo("batchId", scoreRecord.getBatchId());
        }
        List<IdentityInfo> identityInfoList = iIdentityInfoService.findByCondition(condition);
        Set<Integer> binhaiIds = new HashSet<>();
        for (IdentityInfo identityInfo : identityInfoList) {
            binhaiIds.add(identityInfo.getId());
        }
        condition = new Condition(ScoreRecord.class);
        criteria = condition.createCriteria();
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
        criteria.andIn("opRoleId", roles);
        criteria.andEqualTo("status", 4);
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser.getUserType() == 0) {
            criteria.andEqualTo("acceptAddressId", 1);
        } else if (securityUser.getUserType() == 1) {
            criteria.andEqualTo("acceptAddressId", 2);
        }
        if (StringUtils.isNotEmpty(scoreRecord.getPersonIdNum())) {
            criteria.andEqualTo("personIdNum", scoreRecord.getPersonIdNum());
        }
        if (scoreRecord.getBatchId() != null) {
            criteria.andEqualTo("batchId", scoreRecord.getBatchId());
        }
        if (scoreRecord.getIndicatorId() != null) {
            criteria.andEqualTo("indicatorId", scoreRecord.getIndicatorId());
        }
        /*
        如果开启日期查询，就添加打分日期的查询条件
         */
        if (dateSearch==1){
            criteria.andBetween("scoreDate",date,date2);
        }
        if (StringUtils.isNotEmpty(sort_)) {
            String[] arr = sort_.split("_");
            if (arr.length == 2) {
                if ("desc".endsWith(arr[1])) {
                    condition.orderBy(arr[0]).desc();
                } else {
                    condition.orderBy(arr[0]).asc();
                }
            }
        } else {
            condition.orderBy("scoreDate").desc();
        }
        PageInfo<ScoreRecord> pageInfo = iScoreRecordService.selectByFilterAndPage(condition, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    /**
     * 2020年5月25日
     * 查询申请复核的申请人
     * @return
     */
    @GetMapping(value = "/toReview")
    @ResponseBody
    public Result toReview(ScoreRecord scoreRecord,@RequestParam(value = "isDone", required = false) String isDone,
                           @RequestParam(value = "guizijuOrZhujianwei", required = false) String guizijuOrZhujianwei,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize){

        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        SecurityUser user = SecurityUtil.getCurrentSecurityUser();
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(userId);
        if (roles.contains(1060)){
            roles.add(9);
        }
        // 查询申请时间不为空，申请审核理由不为空
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        BatchConf batchConf = new BatchConf();
        batchConf.setStatus(1);
        List<BatchConf> list = iBatchConfService.selectByFilter(batchConf);
        if (list.size() > 0) {
            criteria.andEqualTo("batchId",list.get(0).getId());
        }
        criteria.andIsNotNull("toreviewreason"); // 申请复核时间不为空
        if (scoreRecord.getPersonIdNum()!=null && scoreRecord.getPersonIdNum()!=""){
            criteria.andLike("personIdNum",scoreRecord.getPersonIdNum());
        }
        if (scoreRecord.getIndicatorId()!=null && scoreRecord.getIndicatorId()!=0){
            criteria.andEqualTo("indicatorId",scoreRecord.getIndicatorId());
        }
        if (isDone!=null && isDone!="" && isDone.equals("0")){
            criteria.andIsNull("idreviewend");
        }
        if (isDone!=null && isDone!="" && isDone.equals("1")){
            criteria.andEqualTo("idreviewend",isDone);
        }
        //市区用以区分规自局/住建委
        // 1：规自局；2：住建委
        int aa = Integer.parseInt(guizijuOrZhujianwei);
        if(aa>0){
            criteria.andEqualTo("guizijuOrZhujianwei", aa);
        }
        if (user.getUserType() == 0) {
            criteria.andEqualTo("acceptAddressId", 1);
        } else if (user.getUserType() == 1) {
            criteria.andEqualTo("acceptAddressId", 2);
        }
        criteria.andIn("opRoleId",roles);
        condition.orderBy("toreviewtime");
        condition.orderBy("personIdNum");


        PageInfo<ScoreRecord> pageInfo = iScoreRecordService.selectByFilterAndPage(condition, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }


    /**
     * 2020年5月25日
     * @param id
     * @return
     * @throws IOException
     */
    @PostMapping("/clickHandle")
    public Result rensheBackFinish2(@RequestParam Integer id) throws IOException {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        ScoreRecord scoreRecord = iScoreRecordService.findById(id);
        scoreRecord.setIdreviewend(1);
        iScoreRecordService.update(scoreRecord);

        /*
        留痕记录
         */
        PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
        personBatchStatusRecord.setPersonId(scoreRecord.getPersonId());
        personBatchStatusRecord.setBatchId(scoreRecord.getBatchId());
        personBatchStatusRecord.setPersonIdNumber(scoreRecord.getPersonIdNum());
        personBatchStatusRecord.setStatusTypeDesc("窗口点击开始复核打分指标项");
        personBatchStatusRecord.setStatusTime(new Date());
        personBatchStatusRecord.setStatusStr(securityUser.getLoginName()+"点击");
        personBatchStatusRecord.setStatusReason("窗口点击开始复核打分指标项");
        personBatchStatusRecord.setStatusInt(1240);
        iPersonBatchStatusRecordService.save(personBatchStatusRecord);
        return ResponseUtil.success();
    }

    /*审核打分*/
    @GetMapping("/detailAll")
    public Result detailAll(@RequestParam Integer identityInfoId, @RequestParam Integer indicatorId,
                            @RequestParam Integer opRoleId,
                            @RequestParam(value = "view", required = false, defaultValue = "0") Integer view)
            throws FileNotFoundException {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) throw new AuthBusinessException("用户未设置角色");
        if (!roles.contains(opRoleId)) throw new AuthBusinessException("当前部门无权限打该指标的分");
        Map params = new HashMap();
        params.put("view", view);
        List<Map> scoreList = new ArrayList<>();
        List<ScoreRecord> indicatorIdList = iScoreRecordService
                .selectIndicatorIdsByIdentityInfoIdAndRoleIds(identityInfoId, indicatorId,
                        Collections.singletonList(opRoleId));
        for (ScoreRecord scoreRecord : indicatorIdList) {
            Map msMap = new HashMap();
            msMap.put("scoreRecordId", scoreRecord.getId());
            Indicator indicator = iIndicatorService.findById(scoreRecord.getIndicatorId());
            if (indicator == null) {
                //System.out.println(scoreRecord.getIndicatorId());
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
            msMap.put("jiaowei", -2);
            if (scoreRecord.getIndicatorId() == 3 && scoreRecord.getOpRoleId() == 3) {
                condition = new Condition(ScoreRecord.class);
                criteria = condition.createCriteria();
                criteria.andEqualTo("indicatorId", 3);
                criteria.andEqualTo("opRoleId", 6);
                criteria.andEqualTo("personId", identityInfoId);
                List<ScoreRecord> minzhenList = iScoreRecordService.findByCondition(condition);
                if (minzhenList != null && minzhenList.size() > 0 && minzhenList.get(0).getScoreValue() != null) {
                    msMap.put("jiaowei", minzhenList.get(0).getScoreValue());
                } else {
                    msMap.put("jiaowei", -1);
                }
            }
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
        condition = new Condition(MaterialInfo.class);
        condition.setOrderByClause("sortColumns");
        List<MaterialInfo> materialInfoList = iMaterialInfoService.findByCondition(condition);
        List<MaterialInfo> roleMaterialInfoList = new ArrayList<>();

        //添加营业执照,只有人社添加
        if (roles.contains(3)) {
            MaterialInfo businessLicenseMaterialInfo = new MaterialInfo();
            businessLicenseMaterialInfo.setId(-1);
            businessLicenseMaterialInfo.setName("经办人身份证");
            OnlinePersonMaterial businessLicenseMaterial = new OnlinePersonMaterial();
            businessLicenseMaterial.setMaterialUri(companyInfo.getBusinessLicenseSrc());
            businessLicenseMaterial.setId(-1);
            businessLicenseMaterial.setPersonId(-1);
            businessLicenseMaterial.setMaterialInfoName("经办人身份证");
            businessLicenseMaterialInfo.setOnlinePersonMaterial(businessLicenseMaterial);
            roleMaterialInfoList.add(0, businessLicenseMaterialInfo);
        }

        //该权限可以查看的所有材料
        Set<Integer> rolesSet = new HashSet<>(roles);
        for (MaterialInfo materialInfo : materialInfoList) {
            if (CollectionUtil.isHaveUnionBySet(rolesSet, materialInfo.getMaterialInfoRoleSet())) {
                if (materialInfo.getIsUpload() == 1) {
                    if (roleMidSet.contains(materialInfo.getId())) {
                        roleMaterialInfoList.add(materialInfo);
                    }
                }
            }
        }
        condition = new Condition(OnlinePersonMaterial.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("personId", person.getId());
        criteria.andEqualTo("batchId", person.getBatchId());
        criteria.andNotEqualTo("status", 2);
        List<OnlinePersonMaterial> uploadMaterialList = iOnlinePersonMaterialService.findByCondition(condition);
        List<OnlinePersonMaterial> roleUploadMaterialList = new ArrayList<>();
        //用户上传的材料
        for (OnlinePersonMaterial onlinePersonMaterial : uploadMaterialList) {
            if (roleMidSet.contains(onlinePersonMaterial.getMaterialInfoId())) {
                onlinePersonMaterial.setMaterialInfoName((String) mMap.get(onlinePersonMaterial.getMaterialInfoId() + ""));
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

        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "score_record.ftl", params);
        Map result = new HashMap();
        List<String> sCheckList = new ArrayList<>();
        List<String> sTextList = new ArrayList<>();
        condition = new Condition(ScoreRecord.class);
        criteria = condition.createCriteria();
        criteria.andIn("opRoleId", roles);
        criteria.andEqualTo("personId", identityInfoId);
        criteria.andEqualTo("batchId", person.getBatchId());
        criteria.andEqualTo("indicatorId", indicatorId);
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
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(userId);
        if (roles.contains(4) || roles.contains(6)) {
            result.put("edit", true);
        }
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
                Integer indicatorId = Integer.valueOf(sAnArr[0]);
                Integer opRoleId = Integer.valueOf(sAnArr[2]);
                BigDecimal scoreValue = BigDecimal.valueOf(Float.valueOf(sAnArr[1]));
                Condition condition = new Condition(ScoreRecord.class);
                tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
                criteria.andEqualTo("indicatorId", indicatorId);
                criteria.andEqualTo("personId", personId);
                criteria.andEqualTo("batchId", person.getBatchId());
                criteria.andEqualTo("opRoleId", opRoleId);
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
                Integer indicatorId = Integer.valueOf(sIdArr[0]);
                Integer itemId = Integer.valueOf(sIdArr[1]);
                Integer opRoleId = Integer.valueOf(sIdArr[2]);
                Condition condition = new Condition(ScoreRecord.class);
                tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
                criteria.andEqualTo("indicatorId", indicatorId);
                criteria.andEqualTo("personId", personId);
                criteria.andEqualTo("batchId", person.getBatchId());
                criteria.andEqualTo("opRoleId", opRoleId);
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
