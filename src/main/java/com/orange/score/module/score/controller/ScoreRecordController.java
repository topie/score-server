package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.exception.AuthBusinessException;
import com.orange.score.common.tools.freemarker.FreeMarkerUtil;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.*;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.*;
import com.orange.score.module.security.SecurityUser;
import com.orange.score.module.security.SecurityUtil;
import com.orange.score.module.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
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

    @GetMapping(value = "/scoring")
    @ResponseBody
    public Result scoring(ScoreRecord scoreRecord,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserRoleByUserId(userId);
        criteria.andIn("opRoleId", roles);
        criteria.andEqualTo("status", 3);
        PageInfo<ScoreRecord> pageInfo = iScoreRecordService.selectByFilterAndPage(condition, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/scored")
    @ResponseBody
    public Result scored(ScoreRecord scoreRecord,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserRoleByUserId(userId);
        criteria.andIn("opRoleId", roles);
        criteria.andEqualTo("status", 4);
        PageInfo<ScoreRecord> pageInfo = iScoreRecordService.selectByFilterAndPage(condition, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping("/detailPerson")
    public Result detailPerson(@RequestParam Integer identityInfoId) throws FileNotFoundException {
        Map params = new HashMap();
        Condition condition = new Condition(ScoreRecord.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserRoleByUserId(userId);
        criteria.andEqualTo("personId", identityInfoId);
        criteria.andIn("opRoleId", roles);
        criteria.andEqualTo("status", 3);
        List<ScoreRecord> records = iScoreRecordService.findByCondition(condition);
        List<Map> scoreList = new ArrayList<>();
        for (ScoreRecord record : records) {
            Map msMap = new HashMap();
            Indicator indicator = iIndicatorService.findById(record.getIndicatorId());
            msMap.put("indicator", indicator);
            condition = new Condition(IndicatorItem.class);
            criteria = condition.createCriteria();
            criteria.andEqualTo("indicatorId", record.getIndicatorId());
            List<IndicatorItem> indicatorItems = iIndicatorItemService.findByCondition(condition);
            msMap.put("indicatorItems", indicatorItems);
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
        condition = new Condition(HouseRelationship.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("identityInfoId", identityInfoId);
        List<HouseRelationship> relationshipList = iHouseRelationshipService.findByCondition(condition);
        if (CollectionUtils.isEmpty(relationshipList)) {
            relationshipList = new ArrayList<>();
        }
        params.put("relation", relationshipList);
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "score_info.ftl", params);
        Map result = new HashMap();

        List<String> sCheckList = new ArrayList<>();
        List<String> sTextList = new ArrayList<>();
        condition = new Condition(ScoreRecord.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("opRoleId", roles.get(0));
        criteria.andEqualTo("personId", identityInfoId);
        criteria.andEqualTo("batchId", person.getBatchId());
        List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);
        for (ScoreRecord item : scoreRecords) {
            if (item.getStatus() == 4) {
                Indicator indicator1 = iIndicatorService.findById(item.getIndicatorId());
                if (indicator1.getItemType() == 0) {
                    sCheckList.add(item.getIndicatorId() + "_" + item.getItemId());
                } else {
                    sTextList.add(item.getIndicatorId() + "_" + item.getScoreValue());
                }
            }
        }
        result.put("sCheckList", sCheckList);
        result.put("sTextList", sTextList);
        result.put("html", html);
        return ResponseUtil.success(result);
    }

    @GetMapping("/detailAll")
    public Result detailAll(@RequestParam Integer identityInfoId, @RequestParam Integer indicatorId)
            throws FileNotFoundException {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserRoleByUserId(userId);
        Map params = new HashMap();
        List<Map> scoreList = new ArrayList<>();
        Map msMap = new HashMap();
        Indicator indicator = iIndicatorService.findById(indicatorId);
        msMap.put("indicator", indicator);
        Condition condition = new Condition(IndicatorItem.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("indicatorId", indicatorId);
        List<IndicatorItem> indicatorItems = iIndicatorItemService.findByCondition(condition);
        msMap.put("indicatorItems", indicatorItems);
        scoreList.add(msMap);
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
        condition = new Condition(HouseRelationship.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("identityInfoId", identityInfoId);
        List<HouseRelationship> relationshipList = iHouseRelationshipService.findByCondition(condition);
        if (CollectionUtils.isEmpty(relationshipList)) {
            relationshipList = new ArrayList<>();
        }
        params.put("relation", relationshipList);
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "score_info.ftl", params);
        Map result = new HashMap();
        List<String> sCheckList = new ArrayList<>();
        List<String> sTextList = new ArrayList<>();
        condition = new Condition(ScoreRecord.class);
        criteria = condition.createCriteria();
        criteria.andEqualTo("opRoleId", roles.get(0));
        criteria.andEqualTo("personId", identityInfoId);
        criteria.andEqualTo("batchId", person.getBatchId());
        criteria.andEqualTo("indicatorId", indicatorId);
        List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);
        for (ScoreRecord item : scoreRecords) {
            if (item.getStatus() == 4) {
                Indicator indicator1 = iIndicatorService.findById(item.getIndicatorId());
                if (indicator1.getItemType() == 0) {
                    sCheckList.add(item.getIndicatorId() + "_" + item.getItemId());
                } else {
                    sTextList.add(item.getIndicatorId() + "_" + item.getScoreValue());
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
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_person_batch_score_record");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_person_batch_score_record");
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
            @RequestParam(value = "sAns", required = false) String[] sAns) {
        SecurityUser user = SecurityUtil.getCurrentSecurityUser();
        Integer userId = user.getId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserRoleByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) throw new AuthBusinessException("用户未设置角色");
        IdentityInfo person = iIdentityInfoService.findById(personId);
        if (sAns != null) {
            for (String sAn : sAns) {
                String[] sAnArr = sAn.split("_");
                Integer indicatorId = Integer.valueOf(sAnArr[0]);
                BigDecimal scoreValue = BigDecimal.valueOf(Float.valueOf(sAnArr[1]));
                Condition condition = new Condition(ScoreRecord.class);
                tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
                criteria.andEqualTo("indicatorId", indicatorId);
                criteria.andEqualTo("personId", personId);
                criteria.andEqualTo("batchId", person.getBatchId());
                criteria.andEqualTo("opRoleId", roles.get(0));
                List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);
                for (ScoreRecord scoreRecord : scoreRecords) {
                    scoreRecord.setItemId(0);
                    scoreRecord.setScoreDate(new Date());
                    scoreRecord.setOpUserId(userId);
                    scoreRecord.setOpUser(user.getDisplayName());
                    scoreRecord.setScoreValue(scoreValue);
                    scoreRecord.setStatus(4);
                    iScoreRecordService.update(scoreRecord);
                }
            }
        }
        if (sIds != null) {
            for (String sId : sIds) {
                String[] sIdArr = sId.split("_");
                Integer indicatorId = Integer.valueOf(sIdArr[0]);
                Integer itemId = Integer.valueOf(sIdArr[1]);
                Condition condition = new Condition(ScoreRecord.class);
                tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
                criteria.andEqualTo("indicatorId", indicatorId);
                criteria.andEqualTo("personId", personId);
                criteria.andEqualTo("batchId", person.getBatchId());
                criteria.andEqualTo("opRoleId", roles.get(0));
                List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);
                for (ScoreRecord scoreRecord : scoreRecords) {
                    scoreRecord.setItemId(itemId);
                    scoreRecord.setScoreDate(new Date());
                    scoreRecord.setOpUserId(userId);
                    scoreRecord.setOpUser(user.getDisplayName());
                    if (itemId == 0) {
                        scoreRecord.setScoreValue(BigDecimal.ZERO);
                    } else {
                        IndicatorItem item = iIndicatorItemService.findById(itemId);
                        scoreRecord.setScoreValue(BigDecimal.valueOf(item.getScore()));
                    }
                    scoreRecord.setStatus(4);
                    iScoreRecordService.update(scoreRecord);
                }
            }
        }
        return ResponseUtil.success();
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
}
