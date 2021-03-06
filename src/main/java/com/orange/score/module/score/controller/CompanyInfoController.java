package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.Option;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.CompanyInfo;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.ICompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenJz1012 on 2018-04-04.
 */
@RestController
@RequestMapping("/api/score/companyInfo")
public class CompanyInfoController {

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(CompanyInfo companyInfo,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<CompanyInfo> pageInfo = iCompanyInfoService.selectByFilterAndPage(companyInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> list = iCommonQueryService.selectFormItemsByTable("t_company_info");
        return ResponseUtil.success(list);
    }

    @PostMapping("/insert")
    public Result insert(CompanyInfo companyInfo) {
        iCompanyInfoService.save(companyInfo);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iCompanyInfoService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(CompanyInfo companyInfo) {
        iCompanyInfoService.update(companyInfo);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        CompanyInfo companyInfo = iCompanyInfoService.findById(id);
        return ResponseUtil.success(companyInfo);
    }

    @RequestMapping(value = "/options")
    @ResponseBody
    public List<Option> options() {
        List<Option> options = new ArrayList<>();
//        List<CompanyInfo> list = iCompanyInfoService.selectByFilter(null);
//        for (CompanyInfo item : list) {
//            options.add(new Option(item.getCompanyName(), item.getId()));
//        }
        return options;
    }
}
