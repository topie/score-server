package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.score.model.MaterialInfo;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IMaterialInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* Created by chenJz1012 on 2018-04-02.
*/
@RestController
@RequestMapping("/api/score/materialInfo")
public class MaterialInfoController {

    @Autowired
    private IMaterialInfoService iMaterialInfoService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(MaterialInfo materialInfo,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<MaterialInfo> pageInfo = iMaterialInfoService.selectByFilterAndPage(materialInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> list = iCommonQueryService.selectFormItemsByTable("t_material_info");
        return ResponseUtil.success(list);
    }

    @PostMapping("/insert")
    public Result insert(MaterialInfo materialInfo) {
        iMaterialInfoService.save(materialInfo);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iMaterialInfoService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(MaterialInfo materialInfo) {
        iMaterialInfoService.update(materialInfo);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        MaterialInfo modelNameLowerCamel = iMaterialInfoService.findById(id);
        return ResponseUtil.success(modelNameLowerCamel);
    }

    @RequestMapping(value = "/treeNodes", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeNode> treeNodes() {
        return iMaterialInfoService.selectMaterialTreeNodes();
    }
}
