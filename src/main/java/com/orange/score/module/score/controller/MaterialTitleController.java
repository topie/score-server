package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.Option;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.MaterialTitle;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IMaterialTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-11.
 */
@RestController
@RequestMapping("/api/score/materialTitle")
public class MaterialTitleController {

    @Autowired
    private IMaterialTitleService iMaterialTitleService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(MaterialTitle materialTitle,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<MaterialTitle> pageInfo = iMaterialTitleService
                .selectByFilterAndPage(materialTitle, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_material_title");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_material_title");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(MaterialTitle materialTitle) {
        iMaterialTitleService.save(materialTitle);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iMaterialTitleService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(MaterialTitle materialTitle) {
        iMaterialTitleService.update(materialTitle);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        MaterialTitle materialTitle = iMaterialTitleService.findById(id);
        return ResponseUtil.success(materialTitle);
    }

    @RequestMapping(value = "/options")
    @ResponseBody
    public List<Option> options() {
        List<Option> options = new ArrayList<>();
        List<MaterialTitle> list = iMaterialTitleService.selectByFilter(null);
        for (MaterialTitle item : list) {
            options.add(new Option(item.getTitle(), item.getId()));
        }
        return options;
    }
}
