package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.database.score.model.AcceptAddress;
import com.orange.score.database.score.model.AcceptDateConf;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IAcceptAddressService;
import com.orange.score.module.score.service.IAcceptDateConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-10.
 */
@RestController
@RequestMapping("/api/score/acceptDateConf")
public class AcceptDateConfController {

    @Autowired
    private IAcceptDateConfService iAcceptDateConfService;

    @Autowired
    private IAcceptAddressService iAcceptAddressService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(AcceptDateConf acceptDateConf,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<AcceptDateConf> pageInfo = iAcceptDateConfService
                .selectByFilterAndPage(acceptDateConf, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_accept_date_conf");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_accept_date_conf");

        List<AcceptAddress> list = iAcceptAddressService.selectByFilter(null);
        Map addressMap = new HashMap();
        for (AcceptAddress acceptAddress : list) {
            addressMap.put(acceptAddress.getId(),acceptAddress.getAddress());
        }
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        result.put("addressMap", addressMap);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(AcceptDateConf acceptDateConf) {
        if (acceptDateConf.getAcceptDate() != null) {
            acceptDateConf.setWeekDay(DateUtil.getWeek(acceptDateConf.getAcceptDate()).getChineseName());
        }
        iAcceptDateConfService.save(acceptDateConf);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iAcceptDateConfService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(AcceptDateConf acceptDateConf) {
        if (acceptDateConf.getAcceptDate() != null) {
            acceptDateConf.setWeekDay(DateUtil.getWeek(acceptDateConf.getAcceptDate()).getChineseName());
        }
        iAcceptDateConfService.update(acceptDateConf);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        AcceptDateConf acceptDateConf = iAcceptDateConfService.findById(id);
        return ResponseUtil.success(acceptDateConf);
    }
}
