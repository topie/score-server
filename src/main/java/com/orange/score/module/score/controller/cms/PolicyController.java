package com.orange.score.module.score.controller.cms;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.BasicConf;
import com.orange.score.database.score.model.CmsModule;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IBasicConfService;
import com.orange.score.module.score.service.ICmsModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by chenJz1012 on 2018-04-08.
*/
@RestController
@RequestMapping("/api/score/cms/policy")
public class PolicyController {
    @Autowired
    private ICmsModuleService iCmsModuleService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(CmsModule cmsModule,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        cmsModule.setType(1);
        PageInfo<CmsModule> pageInfo = iCmsModuleService.selectByFilterAndPage(cmsModule, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_system_notice");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_system_notice");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(CmsModule cmsModule) {
        cmsModule.setcTime(new Date());
        cmsModule.setType(1);
        iCmsModuleService.save(cmsModule);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iCmsModuleService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(CmsModule cmsModule) {
        cmsModule.setType(1);
        iCmsModuleService.update(cmsModule);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        CmsModule cmsModule = iCmsModuleService.findById(id);
        return ResponseUtil.success(cmsModule);
    }

}
