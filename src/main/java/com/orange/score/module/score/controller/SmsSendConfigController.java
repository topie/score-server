package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.SmsSendConfig;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.ISmsSendConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by chenJz1012 on 2018-04-16.
*/
@RestController
@RequestMapping("/api/score/smsSendConfig")
public class SmsSendConfigController {

    @Autowired
    private ISmsSendConfigService iSmsSendConfigService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(SmsSendConfig smsSendConfig,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<SmsSendConfig> pageInfo = iSmsSendConfigService.selectByFilterAndPage(smsSendConfig, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_sms_send_config");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_sms_send_config");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(SmsSendConfig smsSendConfig) {
        iSmsSendConfigService.save(smsSendConfig);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iSmsSendConfigService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(SmsSendConfig smsSendConfig) {
        iSmsSendConfigService.update(smsSendConfig);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        SmsSendConfig smsSendConfig = iSmsSendConfigService.findById(id);
        return ResponseUtil.success(smsSendConfig);
    }
}
