package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.HouseMove;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IHouseMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* Created by chenJz1012 on 2018-04-08.
*/
@RestController
@RequestMapping("/api/score/houseMove")
public class HouseMoveController {

    @Autowired
    private IHouseMoveService iHouseMoveService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(HouseMove houseMove,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<HouseMove> pageInfo = iHouseMoveService.selectByFilterAndPage(houseMove, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> list = iCommonQueryService.selectFormItemsByTable("t_house_move");
        return ResponseUtil.success(list);
    }

    @PostMapping("/insert")
    public Result insert(HouseMove houseMove) {
        iHouseMoveService.save(houseMove);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iHouseMoveService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(HouseMove houseMove) {
        iHouseMoveService.update(houseMove);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        HouseMove houseMove = iHouseMoveService.findById(id);
        return ResponseUtil.success(houseMove);
    }
}
