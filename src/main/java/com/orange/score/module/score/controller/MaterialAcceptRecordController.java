package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.MaterialAcceptRecord;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IMaterialAcceptRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by chenJz1012 on 2018-04-19.
*/
@RestController
@RequestMapping("/api/score/materialAcceptRecord")
public class MaterialAcceptRecordController {

    @Autowired
    private IMaterialAcceptRecordService iMaterialAcceptRecordService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(MaterialAcceptRecord materialAcceptRecord,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<MaterialAcceptRecord> pageInfo = iMaterialAcceptRecordService.selectByFilterAndPage(materialAcceptRecord, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_person_material_accept_record");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_person_material_accept_record");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(MaterialAcceptRecord materialAcceptRecord) {
        iMaterialAcceptRecordService.save(materialAcceptRecord);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iMaterialAcceptRecordService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(MaterialAcceptRecord materialAcceptRecord) {
        iMaterialAcceptRecordService.update(materialAcceptRecord);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        MaterialAcceptRecord materialAcceptRecord = iMaterialAcceptRecordService.findById(id);
        return ResponseUtil.success(materialAcceptRecord);
    }
}
