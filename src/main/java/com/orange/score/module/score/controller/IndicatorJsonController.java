package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.IndicatorJson;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IIndicatorJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by chenJz1012 on 2018-04-14.
*/
@RestController
@RequestMapping("/api/score/indicatorJson")
public class IndicatorJsonController {

    @Autowired
    private IIndicatorJsonService iIndicatorJsonService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(IndicatorJson indicatorJson,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<IndicatorJson> pageInfo = iIndicatorJsonService.selectByFilterAndPage(indicatorJson, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_indicator_json");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_indicator_json");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(IndicatorJson indicatorJson) {
        iIndicatorJsonService.save(indicatorJson);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iIndicatorJsonService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(IndicatorJson indicatorJson) {
        iIndicatorJsonService.update(indicatorJson);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        IndicatorJson indicatorJson = iIndicatorJsonService.findById(id);
        return ResponseUtil.success(indicatorJson);
    }
}
