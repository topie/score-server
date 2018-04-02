package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.AcceptAddress;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IAcceptAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* Created by chenJz1012 on 2018-04-02.
*/
@RestController
@RequestMapping("/api/score/acceptAddress")
public class AcceptAddressController {

    @Autowired
    private IAcceptAddressService iAcceptAddressService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(AcceptAddress acceptAddress,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<AcceptAddress> pageInfo = iAcceptAddressService.selectByFilterAndPage(acceptAddress, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> list = iCommonQueryService.selectFormItemsByTable("t_accept_address");
        return ResponseUtil.success(list);
    }

    @PostMapping("/insert")
    public Result insert(AcceptAddress acceptAddress) {
        iAcceptAddressService.save(acceptAddress);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iAcceptAddressService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(AcceptAddress acceptAddress) {
        iAcceptAddressService.update(acceptAddress);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        AcceptAddress acceptAddress = iAcceptAddressService.findById(id);
        return ResponseUtil.success(acceptAddress);
    }
}
