package com.orange.score.module.score.controller.info;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.BatchConf;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.database.score.model.Indicator;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.IBatchConfService;
import com.orange.score.module.score.service.IIdentityInfoService;
import com.orange.score.module.score.service.IIndicatorService;
import com.orange.score.module.score.service.IScoreResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<IdentityInfo> identityInfos = iIdentityInfoService.findByCondition(condition);
        for (IdentityInfo identityInfo : identityInfos) {
            iScoreResultService.insertToCheckIdentity(identityInfo.getId(), iMap, indicatorMap);
        }
        batchConf.setProcess(2);
        iBatchConfService.update(batchConf);
        return ResponseUtil.success();
    }

}
