package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.MaterialInfo;
import com.orange.score.database.score.model.OnlinePersonMaterial;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IMaterialInfoService;
import com.orange.score.module.score.service.IOnlinePersonMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-17.
 */
@RestController
@RequestMapping("/api/score/onlinePersonMaterial")
public class OnlinePersonMaterialController {

    @Autowired
    private IOnlinePersonMaterialService iOnlinePersonMaterialService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IMaterialInfoService iMaterialInfoService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(OnlinePersonMaterial onlinePersonMaterial,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<OnlinePersonMaterial> pageInfo = iOnlinePersonMaterialService
                .selectByFilterAndPage(onlinePersonMaterial, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_online_person_material");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_online_person_material");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(OnlinePersonMaterial onlinePersonMaterial) {
        if (onlinePersonMaterial.getMaterialId() != null) {
            MaterialInfo materialInfo = iMaterialInfoService.findById(onlinePersonMaterial.getMaterialId());
            onlinePersonMaterial.setMaterialName(materialInfo.getName());
        }
        iOnlinePersonMaterialService.save(onlinePersonMaterial);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iOnlinePersonMaterialService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(OnlinePersonMaterial onlinePersonMaterial) {
        if (onlinePersonMaterial.getMaterialId() != null) {
            MaterialInfo materialInfo = iMaterialInfoService.findById(onlinePersonMaterial.getMaterialId());
            onlinePersonMaterial.setMaterialName(materialInfo.getName());
        }
        iOnlinePersonMaterialService.update(onlinePersonMaterial);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        OnlinePersonMaterial onlinePersonMaterial = iOnlinePersonMaterialService.findById(id);
        return ResponseUtil.success(onlinePersonMaterial);
    }
}
