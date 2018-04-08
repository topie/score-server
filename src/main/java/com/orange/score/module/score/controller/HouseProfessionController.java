package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.HouseProfession;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IHouseProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by chenJz1012 on 2018-04-08.
*/
@RestController
@RequestMapping("/api/score/houseProfession")
public class HouseProfessionController {

    @Autowired
    private IHouseProfessionService iHouseProfessionService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(HouseProfession houseProfession,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<HouseProfession> pageInfo = iHouseProfessionService.selectByFilterAndPage(houseProfession, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List formItems = iCommonQueryService.selectFormItemsByTable("t_house_profession");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_house_profession");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(HouseProfession houseProfession) {
        iHouseProfessionService.save(houseProfession);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iHouseProfessionService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(HouseProfession houseProfession) {
        iHouseProfessionService.update(houseProfession);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        HouseProfession houseProfession = iHouseProfessionService.findById(id);
        return ResponseUtil.success(houseProfession);
    }
}
