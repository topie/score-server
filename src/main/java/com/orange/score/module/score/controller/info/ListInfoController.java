package com.orange.score.module.score.controller.info;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.date.DateStyle;
import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.database.score.model.BatchConf;
import com.orange.score.database.score.model.ScoreResult;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.IBatchConfService;
import com.orange.score.module.score.service.IScoreResultService;
import com.orange.score.module.score.thread.SendTaskSingleThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

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

    @Autowired
    private ExecutorService executorService;

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
                    if (limit == i + 1) {
                        for (int j = i + 1; j < scoreResultList.size(); j++) {
                            ScoreResult jItem = scoreResultList.get(j);
                            if (jItem.getScoreValue().floatValue() == item.getScoreValue().floatValue()) {
                                finalResultList.add(jItem);
                            }
                        }
                    }
                }
            }
        } else if (type == 1) {
            for (ScoreResult item : scoreResultList) {
                if (limit <= item.getScoreValue().floatValue()) {
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
        batchConf.setProcess(3);
        iBatchConfService.update(batchConf);
        return ResponseUtil.success();
    }

    @PostMapping("/endList")
    public Result endList(@RequestParam Integer batchId) {
        BatchConf batchConf = iBatchConfService.findById(batchId);
        if (batchConf == null) return ResponseUtil.error("批次不存在");
        batchConf.setProcess(5);
        iBatchConfService.update(batchConf);
        return ResponseUtil.success();
    }

    @PostMapping("/sendMessage")
    public Result sendMessage(@RequestParam Integer batchId) throws IOException {
        BatchConf batchConf = iBatchConfService.findById(batchId);
        if (batchConf == null) return ResponseUtil.error("批次不存在");
        List<Map> smsList = iBatchConfService.selectMobilesByBatchId(batchId);
        String begin = DateUtil.DateToString(batchConf.getNoticeBegin(), DateStyle.YYYY_MM_DD_CN);
        String end = DateUtil.DateToString(batchConf.getNoticeBegin(), DateStyle.MM_DD_CN);
        String template =
                "系统提示：__name__，天津市" + batchConf.getBatchName() + "积分落户受理审核工作已结束，请您登录天津市居住证积分专栏及时查看审核结果，公示期为：" + begin
                        + "-" + end + "。";
        executorService.execute(new SendTaskSingleThread(smsList, template));
        return ResponseUtil.success();
    }
}
