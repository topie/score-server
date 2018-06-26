package com.orange.score.module.score.controller;

import com.orange.score.common.core.BaseController;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.freemarker.FreeMarkerUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.database.core.model.Region;
import com.orange.score.database.score.model.*;
import com.orange.score.database.security.model.Role;
import com.orange.score.module.core.service.IRegionService;
import com.orange.score.module.score.service.*;
import com.orange.score.module.security.SecurityUser;
import com.orange.score.module.security.SecurityUtil;
import com.orange.score.module.security.service.RoleService;
import com.orange.score.module.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by chenJz1012 on 2018-04-04.
 */
@RestController
@RequestMapping("/api/score/print")
public class PrintController extends BaseController {

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

    @GetMapping(value = "/template")
    @ResponseBody
    public Result html() throws FileNotFoundException {
        Map params = new HashMap();
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "word_template.ftl", params);
        Map result = new HashMap<>();
        result.put("html", html);
        return ResponseUtil.success(result);
    }

    @GetMapping(value = "/accept")
    @ResponseBody
    public Result accept(@RequestParam("personId") Integer personId) throws FileNotFoundException {
        IdentityInfo identityInfo = iIdentityInfoService.findById(personId);
        Map params = new HashMap();
        params.put("person", identityInfo);
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
        List<Integer> roles = userService.findUserRoleByUserId(user.getId());
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
        Map result = new HashMap<>();
        result.put("html", html);
        return ResponseUtil.success(result);
    }

    @GetMapping(value = "/acceptNotice")
    @ResponseBody
    public Result acceptNotice(@RequestParam("personId") Integer personId) throws FileNotFoundException {
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
        List<Integer> roles = userService.findUserRoleByUserId(user.getId());
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
    @ResponseBody
    public Result moveNotice(@RequestParam("personId") Integer personId) throws FileNotFoundException {
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
        List<Integer> roles = userService.findUserRoleByUserId(user.getId());
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
        List<Region> regionList = iRegionService.findAll();
        params.put("regionList", regionList);
        List<Office> officeList = iOfficeService.findAll();
        params.put("officeList", officeList);
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "move_notice_doc.ftl", params);
        Map result = new HashMap<>();
        result.put("html", html);
        return ResponseUtil.success(result);
    }


    @GetMapping(value = "/materialList")
    @ResponseBody
    public Result materialList() throws FileNotFoundException {
        Map params = new HashMap();
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "material_list_doc.ftl", params);
        Map result = new HashMap<>();
        result.put("html", html);
        return ResponseUtil.success(result);
    }

}
