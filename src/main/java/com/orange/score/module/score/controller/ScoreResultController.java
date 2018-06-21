package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.ScoreResult;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IScoreResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by chenJz1012 on 2018-04-18.
*/
@RestController
@RequestMapping("/api/score/scoreResult")
public class ScoreResultController {

    @Autowired
    private IScoreResultService iScoreResultService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(ScoreResult scoreResult,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<ScoreResult> pageInfo = iScoreResultService.selectByFilterAndPage(scoreResult, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_pb_score_result");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_pb_score_result");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(ScoreResult scoreResult) {
        iScoreResultService.save(scoreResult);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iScoreResultService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(ScoreResult scoreResult) {
        iScoreResultService.update(scoreResult);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        ScoreResult scoreResult = iScoreResultService.findById(id);
        return ResponseUtil.success(scoreResult);
    }
}
