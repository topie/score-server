package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.IndicatorItem;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IIndicatorItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* Created by chenJz1012 on 2018-04-02.
*/
@RestController
@RequestMapping("/api/score/indicatorItem")
public class IndicatorItemController {

    @Autowired
    private IIndicatorItemService iIndicatorItemService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(IndicatorItem indicatorItem,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<IndicatorItem> pageInfo = iIndicatorItemService.selectByFilterAndPage(indicatorItem, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> list = iCommonQueryService.selectFormItemsByTable("t_indicator_item");
        return ResponseUtil.success(list);
    }

    @PostMapping("/insert")
    public Result insert(IndicatorItem indicatorItem) {
        iIndicatorItemService.save(indicatorItem);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iIndicatorItemService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(IndicatorItem indicatorItem) {
        iIndicatorItemService.update(indicatorItem);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        IndicatorItem modelNameLowerCamel = iIndicatorItemService.findById(id);
        return ResponseUtil.success(modelNameLowerCamel);
    }
}
