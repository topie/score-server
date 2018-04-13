package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.freemarker.FreeMarkerUtil;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.IIdentityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-08.
 */
@RestController
@RequestMapping("/api/score/identityInfo")
public class IdentityInfoController {

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IDictService iDictService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(IdentityInfo identityInfo,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_identity_info");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_identity_info");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        Map reservationStatus = iDictService.selectMapByAlias("reservationStatus");
        result.put("reservationStatus", reservationStatus);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(IdentityInfo identityInfo) {
        iIdentityInfoService.save(identityInfo);
        return ResponseUtil.success(identityInfo.getId());
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iIdentityInfoService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(IdentityInfo identityInfo) {
        iIdentityInfoService.update(identityInfo);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        return ResponseUtil.success(identityInfo);
    }

    @GetMapping("/detailAll")
    public Result detailAll(@RequestParam Integer id) throws FileNotFoundException {
        Map params = new HashMap();
        IdentityInfo identityInfo = iIdentityInfoService.findById(id);
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "identity_info.ftl", params);
        Map result = new HashMap();
        result.put("html", html);
        return ResponseUtil.success(result);
    }
}
