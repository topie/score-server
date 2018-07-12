package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.HouseRelationship;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IHouseRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by chenJz1012 on 2018-04-08.
 */
@RestController
@RequestMapping("/api/score/houseRelationship")
public class HouseRelationshipController {

    @Autowired
    private IHouseRelationshipService iHouseRelationshipService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(HouseRelationship houseRelationship,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<HouseRelationship> pageInfo = iHouseRelationshipService
                .selectByFilterAndPage(houseRelationship, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> list = iCommonQueryService.selectFormItemsByTable("t_house_relationship");
        return ResponseUtil.success(list);
    }

    @PostMapping("/insert")
    public Result insert(HouseRelationship houseRelationship) {
        iHouseRelationshipService.save(houseRelationship);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iHouseRelationshipService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(HouseRelationship houseRelationship) {
        iHouseRelationshipService.update(houseRelationship);
        return ResponseUtil.success();
    }

    @PostMapping("/updateIsMove")
    public Result updateIsMove(@RequestParam Integer id, @RequestParam("isMove") Integer isMove) {
        HouseRelationship houseRelationship = iHouseRelationshipService.findById(id);
        houseRelationship.setIsRemove(isMove);
        iHouseRelationshipService.update(houseRelationship);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        HouseRelationship houseRelationship = iHouseRelationshipService.findById(id);
        return ResponseUtil.success(houseRelationship);
    }
}
