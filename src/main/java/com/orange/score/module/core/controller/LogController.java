package com.orange.score.module.core.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.core.model.Log;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by chenJz1012 on 2018-08-19.
*/
@RestController
@RequestMapping("/api/core/log")
public class LogController {

    @Autowired
    private ILogService iLogService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(Log log,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<Log> pageInfo = iLogService.selectByFilterAndPage(log, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("D_LOG");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("D_LOG");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(Log log) {
        iLogService.save(log);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iLogService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(Log log) {
        iLogService.update(log);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Log log = iLogService.findById(id);
        return ResponseUtil.success(log);
    }
}
