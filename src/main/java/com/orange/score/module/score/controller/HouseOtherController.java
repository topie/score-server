package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.HouseOther;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IHouseOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* Created by chenJz1012 on 2018-04-08.
*/
@RestController
@RequestMapping("/api/score/houseOther")
public class HouseOtherController {

    @Autowired
    private IHouseOtherService iHouseOtherService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(HouseOther houseOther,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<HouseOther> pageInfo = iHouseOtherService.selectByFilterAndPage(houseOther, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> list = iCommonQueryService.selectFormItemsByTable("t_house_other");
        return ResponseUtil.success(list);
    }

    @PostMapping("/insert")
    public Result insert(HouseOther houseOther) {
        iHouseOtherService.save(houseOther);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iHouseOtherService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(HouseOther houseOther) {
        iHouseOtherService.update(houseOther);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        HouseOther houseOther = iHouseOtherService.findById(id);
        return ResponseUtil.success(houseOther);
    }
}
