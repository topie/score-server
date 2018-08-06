package com.orange.score.module.score.controller;

import com.google.common.base.Joiner;
import com.orange.score.common.core.BaseController;
import com.orange.score.common.core.Result;
import com.orange.score.common.exception.AuthBusinessException;
import com.orange.score.common.tools.freemarker.FreeMarkerUtil;
import com.orange.score.common.tools.htmltoword.String2DocConverter;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.database.core.model.Region;
import com.orange.score.database.score.model.*;
import com.orange.score.database.security.model.Role;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.core.service.IRegionService;
import com.orange.score.module.score.service.*;
import com.orange.score.module.security.SecurityUser;
import com.orange.score.module.security.SecurityUtil;
import com.orange.score.module.security.service.RoleService;
import com.orange.score.module.security.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by chenJz1012 on 2018-04-04.
 */
@RestController
@RequestMapping("/api/score/export")
public class ExportController extends BaseController {

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Autowired
    private IMaterialAcceptRecordService iMaterialAcceptRecordService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private IOnlinePersonMaterialService iOnlinePersonMaterialService;

    @Autowired
    private IMaterialInfoService iMaterialInfoService;

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private IHouseOtherService iHouseOtherService;

    @Autowired
    private IHouseMoveService iHouseMoveService;

    @Autowired
    private IHouseProfessionService iHouseProfessionService;

    @Autowired
    private IHouseRelationshipService iHouseRelationshipService;

    @Autowired
    private IOfficeService iOfficeService;

    @Autowired
    private IRegionService iRegionService;

    @Autowired
    private IIndicatorService iIndicatorService;

    @Autowired
    private IIndicatorItemService iIndicatorItemService;

    @Autowired
    private IDictService iDictService;

    @Autowired
    private IScoreRecordService iScoreRecordService;

    @GetMapping(value = "/approveEmptyDoc")
    public void approveEmptyDoc(HttpServletResponse response, @RequestParam Integer identityInfoId) throws Exception {
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
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "approve_empty_doc.ftl", params);
        String tmpName = System.currentTimeMillis() + ".doc";
        String2DocConverter string2DocConverter = new String2DocConverter(html, "/tmp/" + tmpName);
        string2DocConverter.writeWordFile();
        string2DocConverter.download(response, tmpName);
    }

    @GetMapping(value = "/accept")
    @ResponseBody
    public void accept(HttpServletResponse response, @RequestParam("personId") Integer personId) throws Exception {
        IdentityInfo identityInfo = iIdentityInfoService.findById(personId);
        Map params = new HashMap();
        params.put("person", identityInfo);
        Date reDate = identityInfo.getReservationDate();
        if (reDate == null) {
            params.put("year", "-");
            params.put("month", "-");
            params.put("day", "-");
        } else {
            String year = String.valueOf(DateUtil.getYear(reDate));
            params.put("year", year);
            int month = DateUtil.getMonth(identityInfo.getReservationDate()) + 1;
            String monthStr = String.valueOf(month);
            if (month < 10) {
                monthStr = "0" + monthStr;
            }
            params.put("month", monthStr);
            int day = DateUtil.getDay(identityInfo.getReservationDate());
            String dayStr = String.valueOf(day);
            if (day < 10) {
                dayStr = "0" + dayStr;
            }
            params.put("day", dayStr);
        }
        Date now = new Date();
        String nowYear = String.valueOf(DateUtil.getYear(now));
        params.put("nowYear", nowYear);
        int newMonth = DateUtil.getMonth(now) + 1;
        String nowMonthStr = String.valueOf(newMonth);
        if (newMonth < 10) {
            nowMonthStr = "0" + nowMonthStr;
        }
        params.put("nowMonth", nowMonthStr);
        int nowDay = DateUtil.getDay(now);
        String nowDayStr = String.valueOf(nowDay);
        if (nowDay < 10) {
            nowDayStr = "0" + nowDayStr;
        }
        params.put("nowDay", nowDayStr);
        String nowStr = DateUtil.getDate(now);
        params.put("now", nowStr);
        SecurityUser user = SecurityUtil.getCurrentSecurityUser();
        params.put("user", user);
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(user.getId());
        Role role = roleService.findRoleById(roles.get(0));
        params.put("department", role.getRoleName());
        List<MaterialInfo> materialInfos = iMaterialInfoService.findAll();
        Map mMap = new HashMap();
        for (MaterialInfo materialInfo : materialInfos) {
            mMap.put(materialInfo.getId() + "", materialInfo.getName());
        }
        Condition condition = new Condition(OnlinePersonMaterial.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("personId", identityInfo.getId());
        criteria.andEqualTo("batchId", identityInfo.getBatchId());
        List<OnlinePersonMaterial> onlinePersonMaterials = iOnlinePersonMaterialService.findByCondition(condition);
        for (OnlinePersonMaterial onlinePersonMaterial : onlinePersonMaterials) {
            onlinePersonMaterial.setMaterialInfoName((String) mMap.get(onlinePersonMaterial.getMaterialInfoId() + ""));
        }
        params.put("mList", onlinePersonMaterials);

        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "accept_doc.ftl", params);
        String tmpName = System.currentTimeMillis() + ".doc";
        String2DocConverter string2DocConverter = new String2DocConverter(html, "/tmp/" + tmpName);
        string2DocConverter.writeWordFile();
        string2DocConverter.download(response, tmpName);
    }

    @GetMapping(value = "/acceptMaterialDoc")
    public void acceptMaterialDoc(HttpServletResponse response, @RequestParam("personId") Integer personId)
            throws Exception {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(userId);
        IdentityInfo identityInfo = iIdentityInfoService.findById(personId);
        CompanyInfo companyInfo = iCompanyInfoService.findById(identityInfo.getCompanyId());
        Map params = new HashMap();
        params.put("person", identityInfo);
        params.put("company", companyInfo);
        Date reDate = identityInfo.getReservationDate();
        if (reDate == null) {
            params.put("year", "-");
            params.put("month", "-");
            params.put("day", "-");
        } else {
            String year = String.valueOf(DateUtil.getYear(reDate));
            params.put("year", year);
            int month = DateUtil.getMonth(identityInfo.getReservationDate()) + 1;
            String monthStr = String.valueOf(month);
            if (month < 10) {
                monthStr = "0" + monthStr;
            }
            params.put("month", monthStr);
            int day = DateUtil.getDay(identityInfo.getReservationDate());
            String dayStr = String.valueOf(day);
            if (day < 10) {
                dayStr = "0" + dayStr;
            }
            params.put("day", dayStr);
        }
        Date now = new Date();
        String nowYear = String.valueOf(DateUtil.getYear(now));
        params.put("nowYear", nowYear);
        int newMonth = DateUtil.getMonth(now) + 1;
        String nowMonthStr = String.valueOf(newMonth);
        if (newMonth < 10) {
            nowMonthStr = "0" + nowMonthStr;
        }
        params.put("nowMonth", nowMonthStr);
        int nowDay = DateUtil.getDay(now);
        String nowDayStr = String.valueOf(nowDay);
        if (nowDay < 10) {
            nowDayStr = "0" + nowDayStr;
        }
        params.put("nowDay", nowDayStr);
        String nowStr = DateUtil.getDate(now);
        params.put("now", nowStr);
        SecurityUser user = SecurityUtil.getCurrentSecurityUser();
        params.put("user", user);
        List<String> departmentNames = new ArrayList<>();
        for (Integer roleId : roles) {
            Role role = roleService.findRoleById(roleId);
            departmentNames.add(role.getRoleName());
        }
        if (roles.contains(3)) {
            params.put("renshe", true);
        }
        params.put("department", Joiner.on("、").join(departmentNames));
        List<MaterialInfo> materialInfos = iMaterialInfoService.findAll();
        Map mMap = new HashMap();
        for (MaterialInfo materialInfo : materialInfos) {
            mMap.put(materialInfo.getId() + "", materialInfo.getName());
        }
        Condition condition = new Condition(MaterialAcceptRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("roleId", roles);
        criteria.andEqualTo("personId", personId);
        criteria.andEqualTo("batchId", identityInfo.getBatchId());
        List<MaterialAcceptRecord> materials = iMaterialAcceptRecordService.findByCondition(condition);
        params.put("mList", materials);
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "accept_doc.ftl", params);
        String2DocConverter string2DocConverter = new String2DocConverter(html,
                "/tmp/" + System.currentTimeMillis() + ".doc");
        string2DocConverter.writeWordFile();
        string2DocConverter.download(response, "接收凭证-" + identityInfo.getName());
    }

    @GetMapping(value = "/acceptNotice")
    @ResponseBody
    public Result acceptNotice(@RequestParam("personId") Integer personId) throws FileNotFoundException {
        IdentityInfo identityInfo = iIdentityInfoService.findById(personId);
        Map params = new HashMap();
        params.put("person", identityInfo);
        CompanyInfo companyInfo = iCompanyInfoService.findById(identityInfo.getCompanyId());
        params.put("company", companyInfo);
        if (identityInfo.getReservationDate() == null) {
            params.put("year", "-");
            params.put("month", "-");
            params.put("day", "-");
        } else {
            String year = String.valueOf(DateUtil.getYear(identityInfo.getReservationDate()));
            params.put("year", year);
            int month = DateUtil.getMonth(identityInfo.getReservationDate()) + 1;
            String monthStr = String.valueOf(month);
            if (month < 10) {
                monthStr = "0" + monthStr;
            }
            params.put("month", monthStr);
            int day = DateUtil.getDay(identityInfo.getReservationDate());
            String dayStr = String.valueOf(day);
            if (day < 10) {
                dayStr = "0" + dayStr;
            }
            params.put("day", dayStr);
        }
        Date now = new Date();
        String nowYear = String.valueOf(DateUtil.getYear(now));
        params.put("nowYear", nowYear);
        int newMonth = DateUtil.getMonth(now) + 1;
        String nowMonthStr = String.valueOf(newMonth);
        if (newMonth < 10) {
            nowMonthStr = "0" + nowMonthStr;
        }
        params.put("nowMonth", nowMonthStr);
        int nowDay = DateUtil.getDay(now);
        String nowDayStr = String.valueOf(nowDay);
        if (nowDay < 10) {
            nowDayStr = "0" + nowDayStr;
        }
        params.put("nowDay", nowDayStr);
        String nowStr = DateUtil.getDate(now);
        params.put("now", nowStr);
        SecurityUser user = SecurityUtil.getCurrentSecurityUser();
        params.put("user", user);
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(user.getId());
        Role role = roleService.findRoleById(roles.get(0));
        params.put("department", role.getRoleName());
        List<MaterialInfo> materialInfos = iMaterialInfoService.findAll();
        Map mMap = new HashMap();
        for (MaterialInfo materialInfo : materialInfos) {
            mMap.put(materialInfo.getId() + "", materialInfo.getName());
        }
        Condition condition = new Condition(OnlinePersonMaterial.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("personId", identityInfo.getId());
        criteria.andEqualTo("batchId", identityInfo.getBatchId());
        List<OnlinePersonMaterial> onlinePersonMaterials = iOnlinePersonMaterialService.findByCondition(condition);
        for (OnlinePersonMaterial onlinePersonMaterial : onlinePersonMaterials) {
            onlinePersonMaterial.setMaterialInfoName((String) mMap.get(onlinePersonMaterial.getMaterialInfoId() + ""));
        }
        params.put("mList", onlinePersonMaterials);

        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "accept_notice_doc.ftl", params);
        Map result = new HashMap<>();
        result.put("html", html);
        return ResponseUtil.success(result);
    }

    @GetMapping(value = "/moveNotice")
    public void moveNotice(HttpServletResponse response, @RequestParam("personId") Integer personId) throws Exception {
        IdentityInfo identityInfo = iIdentityInfoService.findById(personId);
        Map params = new HashMap();
        params.put("person", identityInfo);

        CompanyInfo companyInfo = iCompanyInfoService.findById(identityInfo.getCompanyId());
        params.put("company", companyInfo);
        String year = String.valueOf(DateUtil.getYear(identityInfo.getReservationDate()));
        params.put("year", year);
        int month = DateUtil.getMonth(identityInfo.getReservationDate()) + 1;
        String monthStr = String.valueOf(month);
        if (month < 10) {
            monthStr = "0" + monthStr;
        }
        params.put("month", monthStr);
        int day = DateUtil.getDay(identityInfo.getReservationDate());
        String dayStr = String.valueOf(day);
        if (day < 10) {
            dayStr = "0" + dayStr;
        }
        params.put("day", dayStr);
        HouseOther other = iHouseOtherService.findBy("identityInfoId", personId);
        if (other == null) {
            other = new HouseOther();
        }
        params.put("other", other);
        HouseProfession profession = iHouseProfessionService.findBy("identityInfoId", personId);
        if (profession == null) {
            profession = new HouseProfession();
        }
        params.put("profession", profession);
        HouseMove houseMove = iHouseMoveService.findBy("identityInfoId", personId);
        if (houseMove == null) {
            houseMove = new HouseMove();
        }
        params.put("move", houseMove);
        Condition condition = new Condition(HouseRelationship.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("identityInfoId", personId);
        List<HouseRelationship> relationshipList = iHouseRelationshipService.findByCondition(condition);
        if (CollectionUtils.isEmpty(relationshipList)) {
            relationshipList = new ArrayList<>();
        }
        params.put("relation", relationshipList);
        Date now = new Date();
        String nowYear = String.valueOf(DateUtil.getYear(now));
        params.put("nowYear", nowYear);
        int newMonth = DateUtil.getMonth(now) + 1;
        String nowMonthStr = String.valueOf(newMonth);
        if (newMonth < 10) {
            nowMonthStr = "0" + nowMonthStr;
        }
        params.put("nowMonth", nowMonthStr);
        int nowDay = DateUtil.getDay(now);
        String nowDayStr = String.valueOf(nowDay);
        if (day < 10) {
            nowDayStr = "0" + nowDayStr;
        }
        params.put("nowDay", nowDayStr);
        String nowStr = DateUtil.getDate(now);
        params.put("now", nowStr);
        SecurityUser user = SecurityUtil.getCurrentSecurityUser();
        params.put("user", user);
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(user.getId());
        Role role = roleService.findRoleById(roles.get(0));
        params.put("department", role.getRoleName());
        List<MaterialInfo> materialInfos = iMaterialInfoService.findAll();
        Map mMap = new HashMap();
        for (MaterialInfo materialInfo : materialInfos) {
            mMap.put(materialInfo.getId() + "", materialInfo.getName());
        }
        condition = new Condition(OnlinePersonMaterial.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("personId", identityInfo.getId());
        criteria.andEqualTo("batchId", identityInfo.getBatchId());
        List<OnlinePersonMaterial> onlinePersonMaterials = iOnlinePersonMaterialService.findByCondition(condition);
        for (OnlinePersonMaterial onlinePersonMaterial : onlinePersonMaterials) {
            onlinePersonMaterial.setMaterialInfoName((String) mMap.get(onlinePersonMaterial.getMaterialInfoId() + ""));
        }
        params.put("mList", onlinePersonMaterials);

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
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "move_notice_doc.ftl", params);
        String tmpName = System.currentTimeMillis() + ".doc";
        String2DocConverter string2DocConverter = new String2DocConverter(html, "/tmp/" + tmpName);
        string2DocConverter.writeWordFile();
        string2DocConverter.download(response, tmpName);
    }

    @GetMapping(value = "/materialList")
    public void materialList(HttpServletResponse response) throws Exception {
        Map params = new HashMap();
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "material_list_doc.ftl", params);
        String tmpName = System.currentTimeMillis() + ".doc";
        String2DocConverter string2DocConverter = new String2DocConverter(html, "/tmp/" + tmpName);
        string2DocConverter.writeWordFile();
        string2DocConverter.download(response, tmpName);
    }

    @GetMapping(value = "/uploadMaterialDoc")
    public void uploadMaterialDoc(HttpServletResponse response, @RequestParam("personId") Integer personId)
            throws Exception {
        Map params = new HashMap();
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(userId);
        List<Integer> indicatorIds = iIndicatorService.selectIndicatorIdByRoleIds(roles);
        Set<Integer> roleMidSet = new HashSet<>();
        for (Integer indicatorId : indicatorIds) {
            List<Integer> iIds = iIndicatorService.selectBindMaterialIds(indicatorId);
            for (Integer iId : iIds) {
                if (!roleMidSet.contains(iId)) {
                    roleMidSet.add(iId);
                }
            }
        }
        IdentityInfo person = iIdentityInfoService.findById(personId);
        Condition condition = new Condition(MaterialInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isUpload", 1);
        List<MaterialInfo> materialInfos = iMaterialInfoService.findByCondition(condition);

        Map mMap = new HashMap();
        for (MaterialInfo materialInfo : materialInfos) {
            mMap.put(materialInfo.getId() + "", materialInfo.getName());

        }
        condition = new Condition(OnlinePersonMaterial.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("personId", person.getId());
        criteria.andEqualTo("batchId", person.getBatchId());
        List<OnlinePersonMaterial> uploadMaterialList = iOnlinePersonMaterialService.findByCondition(condition);
        List<OnlinePersonMaterial> roleMaterialInfoList = new ArrayList<>();
        for (OnlinePersonMaterial onlinePersonMaterial : uploadMaterialList) {
            onlinePersonMaterial.setMaterialInfoName((String) mMap.get(onlinePersonMaterial.getMaterialInfoId() + ""));
            if (roleMidSet.contains(onlinePersonMaterial.getMaterialInfoId()) && StringUtils
                    .isNotEmpty(onlinePersonMaterial.getMaterialInfoName())) {
                roleMaterialInfoList.add(onlinePersonMaterial);
            }
        }
        params.put("uploadMaterialList", roleMaterialInfoList);
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "upload_material_doc.ftl", params);
        String tmpName = System.currentTimeMillis() + ".doc";
        String2DocConverter string2DocConverter = new String2DocConverter(html, "/tmp/" + tmpName);
        string2DocConverter.writeWordFile();
        string2DocConverter.download(response, tmpName);
    }

}
