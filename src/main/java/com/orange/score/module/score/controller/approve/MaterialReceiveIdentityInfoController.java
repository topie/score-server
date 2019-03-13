package com.orange.score.module.score.controller.approve;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.exception.AuthBusinessException;
import com.orange.score.common.tools.excel.ExcelFileUtil;
import com.orange.score.common.tools.excel.ExcelUtil;
import com.orange.score.common.tools.freemarker.FreeMarkerUtil;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.*;
import com.orange.score.common.utils.date.DateStyle;
import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.database.core.model.Region;
import com.orange.score.database.score.model.*;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.core.service.IRegionService;
import com.orange.score.module.score.service.*;
import com.orange.score.module.security.SecurityUser;
import com.orange.score.module.security.SecurityUtil;
import com.orange.score.module.security.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenJz1012 on 2018-04-16.
 */
@RestController
@RequestMapping("/api/score/materialReceive/identityInfo")
public class MaterialReceiveIdentityInfoController {

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
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private IMaterialAcceptRecordService iMaterialAcceptRecordService;

    @Autowired
    private IOnlinePersonMaterialService iOnlinePersonMaterialService;

    @Autowired
    private IRegionService iRegionService;

    @Autowired
    private IOfficeService iOfficeService;

    @Autowired
    private IBatchConfService iBatchConfService;

    @Autowired
    private IPersonBatchStatusRecordService iPersonBatchStatusRecordService;

    @Value("${upload.folder}")
    private String uploadPath;

    /*
    材料送达-未送达
     */
    @GetMapping(value = "/receiving")
    @ResponseBody
    public Result receiving(ScoreRecord scoreRecord, @RequestParam(value = "sort_", required = false) String sort_,
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
        argMap.put("status", Collections.singletonList(2));
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
        if (scoreRecord.getAcceptDate() != null && dateSearch == 1) {
            argMap.put("acceptDate", DateUtil.DateToString(scoreRecord.getAcceptDate(), DateStyle.YYYY_MM_DD));
        }
        if (StringUtils.isNotEmpty(scoreRecord.getPersonName())) {
            argMap.put("personName", scoreRecord.getPersonName());
        }
        if (scoreRecord.getBatchId() != null) {
            argMap.put("batchId", scoreRecord.getBatchId());
        }
        if (scoreRecord.getCompanyId() != null) {
            argMap.put("companyId", scoreRecord.getCompanyId());
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
        IdentityInfo identityInfo;
        Set<Integer> rolesSet = new HashSet<>(roles);
        Iterator<ScoreRecord> it = pageInfo.getList().iterator();
        boolean isAdmin = userService.findUserRoleByUserId(userId).contains(1);
        while (it.hasNext()) {
            ScoreRecord record = it.next();
            if (roles.contains(4) || roles.contains(6)) {
                record.setEdit(1);
            }
            identityInfo = iIdentityInfoService.findById(record.getPersonId());
            //如果被该部门驳回，就删除该申请人
            if (identityInfo.getMaterialStatus() != null && identityInfo.getMaterialStatus() == 1 && !isAdmin) {
                if (CollectionUtil.isHaveUnionBySet(rolesSet, identityInfo.getOpuser6RoleSet())) {
                    it.remove();
                }
            }
        }

        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/received")
    @ResponseBody
    public Result received(ScoreRecord scoreRecord, @RequestParam(value = "sort_", required = false) String sort_,
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
        argMap.put("status", Arrays.asList(3, 4));
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
        if (scoreRecord.getCompanyId() != null) {
            argMap.put("companyId", scoreRecord.getCompanyId());
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
        for (ScoreRecord record : pageInfo.getList()) {
            if (roles.contains(4) || roles.contains(6)) {
                record.setEdit(1);
            }
        }
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping("/detailAll")
    public Result detailAll(@RequestParam Integer identityInfoId) throws FileNotFoundException {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) throw new AuthBusinessException("用户未设置角色");
        Map params = new HashMap();
        List<Map> mlist = new ArrayList<>();
        List<ScoreRecord> indicatorIdList = iScoreRecordService
                .selectIndicatorIdsByIdentityInfoIdAndRoleIds(identityInfoId, roles);
        for (ScoreRecord item : indicatorIdList) {
            Map msMap = new HashMap();
            Indicator indicator = iIndicatorService.findById(item.getIndicatorId());
            msMap.put("indicator", indicator);
            List<MaterialInfo> materialInfos = iMaterialInfoService.findByIndicatorId(item.getIndicatorId());
            if (materialInfos.size() == 0) continue;
            msMap.put("materialInfos", materialInfos);
            msMap.put("roleId", item.getOpRoleId());
            msMap.put("opRole", item.getOpRole());
            mlist.add(msMap);
        }
        params.put("mlist", mlist);
        IdentityInfo person = iIdentityInfoService.findById(identityInfoId);
        if (person == null) {
            person = new IdentityInfo();
        }
        params.put("person", person);
        List<MaterialInfo> allMaterialInfos = iMaterialInfoService.findAll();
        Map mMap = new HashMap();
        for (MaterialInfo materialInfo : allMaterialInfos) {
            mMap.put(materialInfo.getId() + "", materialInfo.getName());
        }
        params.put("allMaterialInfos", allMaterialInfos);

        List<Integer> indicatorIds = iIndicatorService.selectIndicatorIdByRoleIds(roles);
        Set<Integer> roleMidSet = new HashSet<>();
        for (Integer itemId : indicatorIds) {
            List<Integer> iIds = iIndicatorService.selectBindMaterialIds(itemId);
            for (Integer iId : iIds) {
                if (!roleMidSet.contains(iId)) {
                    roleMidSet.add(iId);
                }
            }
        }
        Condition condition = new Condition(MaterialInfo.class);
        condition.setOrderByClause("sortColumns");
        List<MaterialInfo> materialInfoList = iMaterialInfoService.findByCondition(condition);
        List<MaterialInfo> roleMaterialInfoList = new ArrayList<>();
        Set<Integer> rolesSet = new HashSet<>(roles);
        for (MaterialInfo materialInfo : materialInfoList) {
            if (CollectionUtil.isHaveUnionBySet(rolesSet, materialInfo.getMaterialInfoRoleSet())) {
                if (materialInfo.getIsUpload() == 1) {
                    if (roleMidSet.contains(materialInfo.getId())) {
                        roleMaterialInfoList.add(materialInfo);
                    }
                }
               /* //公安单独处理随迁信息
                if (roles.contains(4) && Arrays.asList(1011, 1017, 1013, 1014, 17).contains(materialInfo.getId())) {
                    roleMaterialInfoList.add(materialInfo);
                }*/
            }
        }
        condition = new Condition(OnlinePersonMaterial.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("personId", person.getId());
        criteria.andEqualTo("batchId", person.getBatchId());
        criteria.andNotEqualTo("status", 2);
        List<OnlinePersonMaterial> uploadMaterialList = iOnlinePersonMaterialService.findByCondition(condition);
        List<OnlinePersonMaterial> roleUploadMaterialList = new ArrayList<>();


        for (OnlinePersonMaterial onlinePersonMaterial : uploadMaterialList) {

            //if (roles.contains(3) || roles.contains(4)) {
            if (roleMidSet.contains(onlinePersonMaterial.getMaterialInfoId())) {
                onlinePersonMaterial.setMaterialInfoName((String) mMap.get(onlinePersonMaterial.getMaterialInfoId() + ""));
                roleUploadMaterialList.add(onlinePersonMaterial);
            }
            //公安单独处理随迁信息
                /*if (roles.contains(4) && Arrays.asList(1011, 1017, 1013, 1014, 17)
                        .contains(onlinePersonMaterial.getId())) {
                    roleUploadMaterialList.add(onlinePersonMaterial);
                }*/
            //}
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

        CompanyInfo companyInfo = iCompanyInfoService.findById(person.getCompanyId());
        if (companyInfo == null) {
            companyInfo = new CompanyInfo();
        }
        params.put("company", companyInfo);
        //添加营业执照,只有人社添加
        if (roles.contains(3)) {
            MaterialInfo businessLicenseMaterialInfo = new MaterialInfo();
            businessLicenseMaterialInfo.setId(-1);
            businessLicenseMaterialInfo.setName("营业执照");
            OnlinePersonMaterial businessLicenseMaterial = new OnlinePersonMaterial();
            businessLicenseMaterial.setMaterialUri(companyInfo.getBusinessLicenseSrc());
            businessLicenseMaterial.setId(-1);
            businessLicenseMaterial.setPersonId(-1);
            businessLicenseMaterial.setMaterialInfoName("营业执照");
            businessLicenseMaterialInfo.setOnlinePersonMaterial(businessLicenseMaterial);
            roleMaterialInfoList.add(0, businessLicenseMaterialInfo);
        }

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
        Map result = new HashMap();
        List<String> mCheckList = new ArrayList<>();
        condition = new Condition(MaterialAcceptRecord.class);
        criteria = condition.createCriteria();
        criteria.andIn("roleId", roles);
        criteria.andEqualTo("personId", identityInfoId);
        criteria.andEqualTo("batchId", person.getBatchId());
        List<MaterialAcceptRecord> materials = iMaterialAcceptRecordService.findByCondition(condition);
        for (MaterialAcceptRecord item : materials) {
            mCheckList.add(item.getIndicatorId() + "_" + item.getMaterialId() + "_" + item.getRoleId());
        }
        result.put("mCheckList", mCheckList);
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "material_info_identity.ftl", params);
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

    @PostMapping("/confirmReceived")
    public Result update(@RequestParam("personId") Integer personId, String[] mIds) {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) throw new AuthBusinessException("用户未设置角色");
        IdentityInfo person = iIdentityInfoService.findById(personId);
        if (person.getHallStatus() == 8) throw new AuthBusinessException("用户资格已取消");
        for (String mId : mIds) {
            String[] mIdArr = mId.split("_");
            if (mIdArr.length != 3) {
                continue;
            }
            Integer indicatorId = Integer.valueOf(mIdArr[0]);
            Integer materialId = Integer.valueOf(mIdArr[1]);
            Integer roleId = Integer.valueOf(mIdArr[2]);
            MaterialInfo materialInfo = iMaterialInfoService.findById(materialId);
            MaterialAcceptRecord materialAcceptRecord = new MaterialAcceptRecord();
            materialAcceptRecord.setIndicatorId(indicatorId);
            materialAcceptRecord.setBatchId(person.getBatchId());
            materialAcceptRecord.setPersonId(personId);
            materialAcceptRecord.setMaterialId(materialId);
            materialAcceptRecord.setRoleId(roleId);
            List<MaterialAcceptRecord> materialAcceptRecords = iMaterialAcceptRecordService
                    .findByT(materialAcceptRecord);
            if (materialAcceptRecords.size() > 0) {
                continue;
            }
            materialAcceptRecord.setMaterialName(materialInfo.getName());
            materialAcceptRecord.setMaterialId(materialInfo.getId());
            materialAcceptRecord.setStatus(1);
            iMaterialAcceptRecordService.save(materialAcceptRecord);
        }
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("personId", personId);
        criteria.andEqualTo("batchId", person.getBatchId());
        criteria.andIn("opRoleId", roles);
        List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);
        for (ScoreRecord scoreRecord : scoreRecords) {
            scoreRecord.setStatus(3);
            scoreRecord.setSubmitDate(new Date());
            iScoreRecordService.update(scoreRecord);
        }
        return ResponseUtil.success();
    }

    @PostMapping("/supply")
    public Result supply(@RequestParam Integer id, @RequestParam("supplyArr") String supplyArr) throws IOException {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        if (!"[]".equals(supplyArr)) {
            IdentityInfo identityInfo = iIdentityInfoService.findById(id);
            if (identityInfo != null) {
                identityInfo.setMaterialStatus(1);
                identityInfo.setOpuser5(securityUser.getDisplayName());
                Set<Integer> set = identityInfo.getOpuser6RoleSet();
                if (set != null) {
                    set.addAll(userService.findUserDepartmentRoleByUserId(securityUser.getId()));
                } else {
                    identityInfo.setOpuser6RoleSet(new HashSet<>(userService.findUserDepartmentRoleByUserId(securityUser.getId())));
                }
                iIdentityInfoService.update(identityInfo);
                iPersonBatchStatusRecordService
                        .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "materialStatus", 4);
            } else {
                return ResponseUtil.error("申请人不存在！");
            }
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
                    if (StringUtils.isNotEmpty(onlinePersonMaterial.getReason())) {
                        onlinePersonMaterial.setReason(onlinePersonMaterial.getReason() + "<br/>" + strDate + "：" + reason);
                    } else {
                        onlinePersonMaterial.setReason(strDate + "：" + reason);
                    }

                    onlinePersonMaterial.setStatus(1);
                    iOnlinePersonMaterialService.update(onlinePersonMaterial);
                } else {
                    OnlinePersonMaterial onlinePersonMaterial = new OnlinePersonMaterial();
                    onlinePersonMaterial.setMaterialInfoId(mId);
                    onlinePersonMaterial.setReason(strDate + "：" + reason);
                    onlinePersonMaterial.setStatus(1);
                    onlinePersonMaterial.setcTime(new Date());
                    onlinePersonMaterial.setPersonId(identityInfo.getId());
                    onlinePersonMaterial.setBatchId(identityInfo.getBatchId());
                    iOnlinePersonMaterialService.save(onlinePersonMaterial);
                }
            }
            HouseOther houseOther = iHouseOtherService.findBy("identityInfoId", identityInfo.getId());
            SmsUtil.send(houseOther.getSelfPhone(), "系统提示：" + identityInfo.getName() + "，您所上传的材料需要补正，请根据提示尽快补正材料。");
        } else {
            return ResponseUtil.error("没有勾选待补正材料！");
        }

        return ResponseUtil.success();
    }

    //心情烦躁,随便写的,有大神有时间可以优化一下,懒得想了
    @GetMapping(value = "/getIdentityInfoExcelWJW")
    @ResponseBody
    public void getIdentityInfoExcelWJW(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(value = "identityInfoId", required = false) Integer identityInfoId) throws Exception {
        IdentityInfo identityInfo = iIdentityInfoService.findById(identityInfoId);
        Condition condition = new Condition(HouseRelationship.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("identityInfoId", identityInfoId);
        List<HouseRelationship> relationshipList = iHouseRelationshipService.findByCondition(condition);
        if (CollectionUtils.isEmpty(relationshipList)) {
            relationshipList = new ArrayList<>();
        }
        HouseMove houseMove = iHouseMoveService.findBy("identityInfoId", identityInfoId);
        if (houseMove == null) {
            houseMove = new HouseMove();
        }
        HouseOther other = iHouseOtherService.findBy("identityInfoId", identityInfoId);
        if (other == null) {
            other = new HouseOther();
        }

        String savePath = request.getSession().getServletContext().getRealPath("/") + uploadPath + System.currentTimeMillis() + ".xlsx";
        File tempfile = createTempFile();
        if (!tempfile.exists()) {
            tempfile.createNewFile();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(tempfile);
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet();
            //合并单元格
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 17));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 12));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 13, 17));
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 7, 12));
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 13, 17));
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 7, 12));
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 13, 17));
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 1));
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 4));
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 5, 6));
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 7, 8));
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 9, 10));
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 11, 13));
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 15, 17));
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 17));
            sheet.addMergedRegion(new CellRangeAddress(6, 7, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(6, 7, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(6, 7, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(6, 7, 3, 3));
            sheet.addMergedRegion(new CellRangeAddress(6, 7, 4, 4));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 5, 7));
            sheet.addMergedRegion(new CellRangeAddress(6, 7, 8, 8));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 9, 10));
            sheet.addMergedRegion(new CellRangeAddress(6, 7, 11, 11));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 12, 15));
            sheet.addMergedRegion(new CellRangeAddress(6, 7, 16, 16));
            sheet.addMergedRegion(new CellRangeAddress(6, 7, 17, 17));
            HouseRelationship houseRelationshipSpouse = new HouseRelationship();
            int relationshipListSize = relationshipList.size();
            for (HouseRelationship h : relationshipList) {
                if ("配偶".equals(h.getRelationship())) {
                    houseRelationshipSpouse = h;
                    relationshipListSize--;
                    break;
                }
            }
            int lastRow = relationshipListSize + 8 + 1;
            sheet.addMergedRegion(new CellRangeAddress(lastRow - 1, lastRow - 1, 0, 17));
            sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow, 0, 1));
            sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow, 2, 6));
            sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow, 7, 11));
            sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow, 12, 17));

            //赋值
            Map<Integer, String> stringMap = new HashMap<>();
            stringMap.put(0, "天津市居住证积分审核表(市卫健委)");
            stringMap.put(19, "姓名");
            stringMap.put(20, "曾用名");
            stringMap.put(21, "性别");
            stringMap.put(22, "身份证号码");
            stringMap.put(23, "婚姻状况");
            stringMap.put(24, "佐证材料");
            stringMap.put(25, "户籍地地址");
            stringMap.put(31, "现居住地址");
            stringMap.put(36, "申请人情况");
            stringMap.put(37, identityInfo.getName());
            stringMap.put(38, identityInfo.getFormerName());
            stringMap.put(39, identityInfo.getStringSex());
            stringMap.put(40, identityInfo.getIdNumber());
            stringMap.put(41, houseMove.getMarriageStatusStr());
            stringMap.put(42, "-");
            stringMap.put(43, houseMove.getMoveAddress());
            stringMap.put(49, houseMove.getMoveNowAddress());
            stringMap.put(54, "配偶情况");
            stringMap.put(55, houseRelationshipSpouse.getName());
            stringMap.put(56, houseRelationshipSpouse.getFormerName());
            String strSex = "";
            if (identityInfo.getStringSex().equals("女")) {
                strSex = "男";
            } else {
                strSex = "女";
            }
            //stringMap.put(57, houseRelationshipSpouse.getStringSex());
            stringMap.put(57, strSex);
            stringMap.put(58, houseRelationshipSpouse.getIdNumber());
            stringMap.put(59, houseRelationshipSpouse.getStringMarriageStatus());
            stringMap.put(60, "-");
            stringMap.put(61, houseRelationshipSpouse.getSpouse_HJAddress());
            stringMap.put(67, houseRelationshipSpouse.getSpouse_LivingAddress());
            stringMap.put(72, "申请人联系电话");
            stringMap.put(74, other.getSelfPhone());
            stringMap.put(77, "申请人单位电话");
            stringMap.put(79, other.getCompanyPhone());
            stringMap.put(81, "申请人单位名称");
            stringMap.put(83, other.getCompanyName());
            stringMap.put(86, "申请人单位地址");
            stringMap.put(87, other.getCompanyAddress());
            stringMap.put(90, "以下填写申请人及配偶的子女情况，包括亲生子女（具体指婚生子女、非婚生子女、离异时抚养权判予对方的子女）和收养子女。");
            stringMap.put(108, "序号");
            stringMap.put(109, "姓名");
            stringMap.put(110, "曾用名");
            stringMap.put(111, "性别");
            stringMap.put(112, "身份证号码");
            stringMap.put(113, "出生医学证明");
            stringMap.put(116, "出生地");
            stringMap.put(117, "收养子女");
            stringMap.put(119, "政策属性");
            stringMap.put(120, "再生育审批情况");
            stringMap.put(124, "与第几任妻子/丈夫所生");
            stringMap.put(125, "抚养权归属");
            stringMap.put(131, "编号");
            stringMap.put(132, "签证机构");
            stringMap.put(133, "佐证材料");
            stringMap.put(135, "是/否");
            stringMap.put(136, "佐证材料");
            stringMap.put(138, "审批时间");
            stringMap.put(139, "审批证明编号");
            stringMap.put(140, "审批单位名称");
            stringMap.put(141, "审批条例适用");

            int startIndex = 144;
            int listIndex = 1;
            for (HouseRelationship h : relationshipList) {
                if ("配偶".equals(h.getRelationship())) {
                    continue;
                }
                stringMap.put(startIndex, String.valueOf(listIndex));
                startIndex++;
                stringMap.put(startIndex, h.getName());
                startIndex++;
                stringMap.put(startIndex, h.getFormerName());
                startIndex++;
                stringMap.put(startIndex, h.getStringSex());
                startIndex++;
                stringMap.put(startIndex, h.getIdNumber());
                startIndex++;
                stringMap.put(startIndex, h.getMedical_number());
                startIndex++;
                stringMap.put(startIndex, h.getMedical_authority());
                startIndex++;
                stringMap.put(startIndex, "-");
                startIndex++;
                stringMap.put(startIndex, h.getBirthplace());
                startIndex++;
                stringMap.put(startIndex, h.getStringIsAdopt());
                startIndex++;
                stringMap.put(startIndex, "-");
                startIndex++;
                stringMap.put(startIndex, h.getPolicyAttribute());
                startIndex++;
                if (h.getApproval_time() != null) {
                    stringMap.put(startIndex, h.getApproval_time().toString());
                } else {
                    stringMap.put(startIndex, "");
                }
                startIndex++;
                stringMap.put(startIndex, h.getApproval_number());
                startIndex++;
                stringMap.put(startIndex, h.getApproval_companyName());
                startIndex++;
                stringMap.put(startIndex, h.getApproval_rules());
                startIndex++;
                //与第${ritem.approval_index}任${ritem.approval_spouse} ${ritem.approval_which}所生
                //stringMap.put(startIndex, h.getApproval_which());
                stringMap.put(startIndex, "与第" + h.getApproval_index() + "任" + h.getApproval_spouse() + " " + h.getApproval_which() + "所生");
                startIndex++;
                stringMap.put(startIndex, h.getApproval_custody());
                startIndex++;
                listIndex++;
            }
            //往後一行
            startIndex += 18;

            startIndex += 2;
            String pregnantWeek = identityInfo.getPregnantWeek();
            if (pregnantWeek == null) {
                pregnantWeek = "_";
            }
            stringMap.put(startIndex, "本人及配偶承诺目前" + identityInfo.getStringPregnantPromise() + " " + pregnantWeek + "周");
            startIndex += 5;
            stringMap.put(startIndex, "本人及配偶承诺目前" + identityInfo.getStringThirdPregnantPromise() + "政策外第三个及以上子女怀孕期间");
            int stringIndex = 0;
            XSSFFont font = workBook.createFont();
            font.setFontName("宋体");
            XSSFCell cell;
            XSSFRow row;
            XSSFCellStyle cellStyle = workBook.createCellStyle();
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setFont(font);
            for (int i = 0; i <= lastRow; i++) {
                row = sheet.createRow(i);
                if (i > 7) {
                    row.setHeight((short) (600));
                } else {
                    row.setHeight((short) (300));
                }
                for (int j = 0; j < 18; j++) {
                    cell = row.createCell(j);
                    cell.setCellValue(stringMap.get(stringIndex));
                    cell.setCellStyle(cellStyle);
                    stringIndex++;
                }
            }
            row = sheet.getRow(5);
            row.setHeight((short) (650));
            row = sheet.getRow(lastRow);
            row.setHeight((short) (300));
            row = sheet.getRow(0);
            row.setHeight((short) (700));
            cell = row.getCell(0);
            XSSFFont font2 = workBook.createFont();
            XSSFCellStyle cellStyle2 = workBook.createCellStyle();
            cellStyle2.cloneStyleFrom(cell.getCellStyle());
            font2.setFontHeightInPoints((short) 18);
            font2.setFontName("宋体");
            font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellStyle2.setFont(font2);
            cell.setCellStyle(cellStyle2);

            //自动设宽度对这种复杂的表格不太好使,而且又是中文,就更不支持了,自己写吧...望大神优化
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 2000);
            sheet.setColumnWidth(2, 2000);
            sheet.setColumnWidth(3, 2000);
            sheet.setColumnWidth(4, 6000);
            sheet.setColumnWidth(5, 4000);
            sheet.setColumnWidth(6, 4000);
            sheet.setColumnWidth(7, 2000);
            sheet.setColumnWidth(8, 8000);
            sheet.setColumnWidth(9, 2000);
            sheet.setColumnWidth(10, 2000);
            sheet.setColumnWidth(11, 3000);
            sheet.setColumnWidth(12, 4000);
            sheet.setColumnWidth(13, 5000);
            sheet.setColumnWidth(14, 6000);
            sheet.setColumnWidth(15, 14000);
            sheet.setColumnWidth(16, 6000);
            sheet.setColumnWidth(17, 5000);

            workBook.write(out);
            InputStream inputStream = new FileInputStream(tempfile);
            File file = new File(savePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) file.createNewFile();
            FileUtil.saveFileFromInputStream(inputStream, savePath);
            ExcelFileUtil.download(response, savePath, "天津市居住证积分审核表(市卫健委).xlsx");
            //file.delete();
        } finally {
            tempfile.delete();
        }
    }

    private static File createTempFile() throws IOException {
        return new File(PropertiesUtil.get("application.properties", "temp.folder") + System.currentTimeMillis());
    }
}
