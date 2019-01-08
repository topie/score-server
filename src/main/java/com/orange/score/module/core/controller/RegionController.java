package com.orange.score.module.core.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.Option;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.core.model.Region;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-12.
 */
@RestController
@RequestMapping("/api/core/region")
public class RegionController {

    @Autowired
    private IRegionService iRegionService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(Region region,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        region.setParentId(19);
        PageInfo<Region> pageInfo = iRegionService.selectByFilterAndPage(region, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_region_2");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_region_2");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(Region region) {
        iRegionService.save(region);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iRegionService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(Region region) {
        iRegionService.update(region);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Region region = iRegionService.findById(id);
        return ResponseUtil.success(region);
    }

    @RequestMapping(value = "/treeNodes", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeNode> treeNodes() {
        return iRegionService.selectTreeNodes();
    }

    @RequestMapping(value = "/options")
    @ResponseBody
    public List<Option> options(Region region) {
        List<Option> options = new ArrayList<>();
        List<Region> list = iRegionService.selectByFilter(region);
        for (Region item : list) {
            options.add(new Option(item.getName(), item.getId()));
        }
        return options;
    }
}
