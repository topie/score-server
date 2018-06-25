package com.orange.score.module.score.controller.info;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.BatchConf;
import com.orange.score.database.score.model.ScoreResult;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.IBatchConfService;
import com.orange.score.module.score.service.IScoreResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-08.
 */
@RestController
@RequestMapping("/api/score/info/listInfo")
public class ListInfoController {

    @Autowired
    private IBatchConfService iBatchConfService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IDictService iDictService;

    @Autowired
    private IScoreResultService iScoreResultService;

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

    @GetMapping(value = "/rank")
    @ResponseBody
    public Result batchList(@RequestParam("batchId") Integer batchId,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<ScoreResult> pageInfo = iScoreResultService.selectRankByBatchId(batchId, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/rankHtml")
    @ResponseBody
    public Result rankHtml(@RequestParam("batchId") Integer batchId) {
        BatchConf batchConf = iBatchConfService.findById(batchId);
        if (batchConf == null) return ResponseUtil.error("批次不存在");
        List<ScoreResult> finalResultList = new ArrayList<>();
        List<ScoreResult> scoreResultList = iScoreResultService.selectRankByBatchId(batchId);
        int type = batchConf.getIndicatorType();
        int limit = batchConf.getIndicatorValue();
        if (type == 0) {
            for (int i = 0; i < scoreResultList.size(); i++) {
                if (limit > i) {
                    ScoreResult item = scoreResultList.get(i);
                    finalResultList.add(item);
                }
            }
        } else if (type == 1) {
            for (ScoreResult item : scoreResultList) {
                if (limit <= item.getScoreValue().intValue()) {
                    finalResultList.add(item);
                }
            }
        }
        return ResponseUtil.success(PageConvertUtil.grid(finalResultList));
    }


    @PostMapping("/setList")
    public Result setList(@RequestParam Integer batchId) {
        BatchConf batchConf = iBatchConfService.findById(batchId);
        if (batchConf == null) return ResponseUtil.error("批次不存在");
        batchConf.setProcess(4);
        iBatchConfService.update(batchConf);
        return ResponseUtil.success();
    }

    @PostMapping("/cancelList")
    public Result cancelList(@RequestParam Integer batchId) {
        BatchConf batchConf = iBatchConfService.findById(batchId);
        if (batchConf == null) return ResponseUtil.error("批次不存在");
        batchConf.setProcess(4);
        iBatchConfService.update(batchConf);
        return ResponseUtil.success();
    }
}
