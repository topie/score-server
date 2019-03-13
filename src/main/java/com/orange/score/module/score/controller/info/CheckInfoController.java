package com.orange.score.module.score.controller.info;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.*;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by chenJz1012 on 2018-04-08.
 */
@RestController
@RequestMapping("/api/score/info/checkInfo")
public class CheckInfoController {

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IDictService iDictService;

    @Autowired
    private IBatchConfService iBatchConfService;

    @Autowired
    private IScoreResultService iScoreResultService;

    @Autowired
    private IIndicatorService iIndicatorService;

    @Autowired
    private IFakeRecordService iFakeRecordService;

    @Autowired
    private IScoreRecordService iScoreRecordService;

    @GetMapping(value = "/batch/list")
    @ResponseBody
    public Result batchList(BatchConf batchConf,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<BatchConf> pageInfo = iBatchConfService.selectByFilterAndPage(batchConf, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/batch/formItems")
    @ResponseBody
    public Result batchFormItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_batch_conf");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_batch_conf");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        Map batchStatus = iDictService.selectMapByAlias("batchStatus");
        result.put("batchStatus", batchStatus);
        Map batchProcess = iDictService.selectMapByAlias("batchProcess");
        result.put("batchProcess", batchProcess);
        return ResponseUtil.success(result);
    }

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(IdentityInfo identityInfo,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
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
        return ResponseUtil.success(result);
    }

    @PostMapping("/checkPerson")
    public Result checkPerson(@RequestParam Integer identityInfoId) {
        IdentityInfo identityInfo = iIdentityInfoService.findById(identityInfoId);
        if (identityInfo == null || (identityInfo.getReservationStatus() != 11 && identityInfo.getHallStatus() != 9)) {
            return ResponseUtil.error("当前申请用户状态无法核算！");
        }
        List<Indicator> indicators = iIndicatorService.findAll();
        Map<Integer, Integer> iMap = new HashMap();
        Map<Integer, Indicator> indicatorMap = new HashMap();
        for (Indicator indicator : indicators) {
            iMap.put(indicator.getId(), indicator.getScoreRule());
            indicatorMap.put(indicator.getId(), indicator);
        }
        iScoreResultService.insertToCheckIdentity(identityInfoId, iMap, indicatorMap);
        return ResponseUtil.success();
    }

    /**
     * 强制核算
     *
     * @param identityInfoId
     * @return
     */
    @PostMapping("/checkPersonForce")
    public Result checkPersonForce(@RequestParam Integer identityInfoId) {
        IdentityInfo identityInfo = iIdentityInfoService.findById(identityInfoId);
        if (identityInfo == null) {
            return ResponseUtil.error("申请用户不存在");
        }
        List<Indicator> indicators = iIndicatorService.findAll();
        Map<Integer, Integer> iMap = new HashMap();
        Map<Integer, Indicator> indicatorMap = new HashMap();
        for (Indicator indicator : indicators) {
            iMap.put(indicator.getId(), indicator.getScoreRule());
            indicatorMap.put(indicator.getId(), indicator);
        }
        iScoreResultService.insertToCheckIdentity(identityInfoId, iMap, indicatorMap);
        return ResponseUtil.success();
    }

    @GetMapping("/canCheck")
    public Result canCheck(@RequestParam Integer batchId) {
        BatchConf batchConf = iBatchConfService.findById(batchId);
        if (batchConf == null) return ResponseUtil.error("批次不存在");
        Condition condition = new Condition(IdentityInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("batchId", batchId);
        criteria.andNotIn("hallStatus", Arrays.asList(6, 9));
        criteria.andEqualTo("cancelStatus", 1);
        List<IdentityInfo> identityInfos = iIdentityInfoService.findByCondition(condition);
        if (identityInfos == null) identityInfos = new ArrayList<>();
        Map result = new HashMap();
        result.put("list", identityInfos);
        if (identityInfos.size() > 0) {
            result.put("can", 0);
        } else {
            result.put("can", 1);
        }
        return ResponseUtil.success(result);
    }

    @GetMapping(value = "/canNotCheckList")
    @ResponseBody
    public Result canNotCheckList(@RequestParam Integer batchId) {
        BatchConf batchConf = iBatchConfService.findById(batchId);
        if (batchConf == null) return ResponseUtil.error("批次不存在");
        Condition condition = new Condition(IdentityInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("batchId", batchId);
        criteria.andNotIn("hallStatus", Arrays.asList(6, 9));
        criteria.andEqualTo("cancelStatus", 1);
        List<IdentityInfo> identityInfos = iIdentityInfoService.findByCondition(condition);
        if (identityInfos == null) identityInfos = new ArrayList<>();
        return ResponseUtil.success(PageConvertUtil.grid(identityInfos));
    }

    /**
     * 汇总发布
     * @param batchId
     * @return
     */
    @PostMapping("/checkBatch")
    public Result checkBatch(@RequestParam Integer batchId) {
        BatchConf batchConf = iBatchConfService.findById(batchId);
        if (batchConf == null) return ResponseUtil.error("批次不存在");
        List<Indicator> indicators = iIndicatorService.findAll();
        Map<Integer, Integer> iMap = new HashMap();
        Map<Integer, Indicator> indicatorMap = new HashMap();
        for (Indicator indicator : indicators) {
            iMap.put(indicator.getId(), indicator.getScoreRule());
            indicatorMap.put(indicator.getId(), indicator);
        }
        Condition condition = new Condition(IdentityInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("batchId", batchId);
        criteria.andEqualTo("hallStatus", 9);
        criteria.andEqualTo("reservationStatus", 11);
        List<IdentityInfo> identityInfos = iIdentityInfoService.findByCondition(condition);
        for (IdentityInfo identityInfo : identityInfos) {
            iScoreResultService.insertToCheckIdentity(identityInfo.getId(), iMap, indicatorMap);
        }
        batchConf.setProcess(2);
        iBatchConfService.update(batchConf);
        return ResponseUtil.success();
    }

    @PostMapping("/cancelCheck")
    public Result cancelCheck(@RequestParam Integer batchId) {
        BatchConf batchConf = iBatchConfService.findById(batchId);
        if (batchConf == null) return ResponseUtil.error("批次不存在");
        batchConf.setProcess(1);
        iBatchConfService.update(batchConf);
        List<Indicator> indicators = iIndicatorService.findAll();
        Map<Integer, Integer> iMap = new HashMap();
        Map<Integer, Indicator> indicatorMap = new HashMap();
        for (Indicator indicator : indicators) {
            iMap.put(indicator.getId(), indicator.getScoreRule());
            indicatorMap.put(indicator.getId(), indicator);
        }
        Condition condition = new Condition(IdentityInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("batchId", batchConf.getId());
        criteria.andEqualTo("resultStatus", 1);
        List<IdentityInfo> identityInfos = iIdentityInfoService.findByCondition(condition);
        for (IdentityInfo identityInfo : identityInfos) {
            identityInfo.setResultStatus(0);
            identityInfo.setHallStatus(5);
            iIdentityInfoService.update(identityInfo);
        }
        iBatchConfService.update(batchConf);
        return ResponseUtil.success();
    }

    @PostMapping("/endCheck")
    public Result endCheck(@RequestParam Integer batchId) {
        BatchConf batchConf = iBatchConfService.findById(batchId);
        if (batchConf == null) return ResponseUtil.error("批次不存在");
        batchConf.setProcess(3);
        iBatchConfService.update(batchConf);
        return ResponseUtil.success();
    }

    /*
    2019年3月12日，汇总发布后关联虚假材料库的申请人，进行相应分数的改变
    1、从虚假数据库取得数据；
    2、比对得分表，相应的减去30分；
     */
    @PostMapping("/relationFakeRecord")
    public Result relationFakeRecord(@RequestParam Integer batchId) {
        BatchConf batchConf = iBatchConfService.findById(batchId);
        if (batchConf == null) return ResponseUtil.error("批次不存在");
        List<FakeRecord> fakeRecords = iFakeRecordService.findAll();
        Date date = new Date();
        for (FakeRecord fakeRecord : fakeRecords){
            if (date.getTime() < fakeRecord.getRecordDate().getTime()){
                Condition condition = new Condition(ScoreRecord.class);
                tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
                criteria.andEqualTo("personIdNum", fakeRecord.getIdNumber());
                List<String> indicators = Arrays.asList(fakeRecord.getIndicatorRole().split(","));
                List<Integer> list = new ArrayList<Integer>();
                for(int i=0;i<indicators.size();i++){
                    list.add(Integer.parseInt(indicators.get(i)));
                }
                criteria.andIn("indicatorId", list);
                List<ScoreRecord> scoreRecords = iScoreRecordService.findByCondition(condition);
                for (ScoreRecord scoreRecord : scoreRecords){
                    if (scoreRecord.getIsDeducted() == null ){
                        BigDecimal fakeScore = new BigDecimal(-30);
                        scoreRecord.setOriginalScoreValue(scoreRecord.getScoreValue());//保存原始分数
                        scoreRecord.setIsDeducted("1");//已经扣过分，状态位变化
                        scoreRecord.setScoreValue(scoreRecord.getScoreValue().add(fakeScore));
                        iScoreRecordService.update(scoreRecord);
                    }
                }
            }
        }

        return ResponseUtil.success();
    }

}
