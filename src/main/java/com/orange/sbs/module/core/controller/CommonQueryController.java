package com.orange.sbs.module.core.controller;

import com.github.pagehelper.PageInfo;
import com.orange.sbs.common.core.Result;
import com.orange.sbs.common.tools.plugins.FormItem;
import com.orange.sbs.common.utils.PageConvertUtil;
import com.orange.sbs.common.utils.ResponseUtil;
import com.orange.sbs.database.core.model.CommonQuery;
import com.orange.sbs.module.core.service.ICommonQueryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Controller
@RequestMapping("/api/core/commonQuery")
public class CommonQueryController {

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(CommonQuery commonQuery,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<CommonQuery> pageInfo = iCommonQueryService.selectByFilterAndPage(commonQuery, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(CommonQuery commonQuery) {
        iCommonQueryService.save(commonQuery);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(CommonQuery commonQuery) {
        iCommonQueryService.update(commonQuery);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        CommonQuery commonQuery = iCommonQueryService.findById(id);
        return ResponseUtil.success(commonQuery);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iCommonQueryService.deleteById(id);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/formItems", method = RequestMethod.GET)
    @ResponseBody
    public Result formItems(@RequestParam(value = "tables") String tables) {
        Map tbsMap = new HashMap<>();
        String[] tbs = tables.split(",");
        for (String tb : tbs) {
            if (StringUtils.isNotEmpty(tb)) {
                List<FormItem> list = iCommonQueryService.selectFormItemsByTable(tb);
                tbsMap.put(tb, list);
            }
        }
        return ResponseUtil.success(tbsMap);
    }

}
