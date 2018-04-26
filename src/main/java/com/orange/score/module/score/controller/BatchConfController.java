package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.BaseController;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.Option;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.BatchConf;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.IBatchConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-04.
 */
@RestController
@RequestMapping("/api/score/batchConf")
public class BatchConfController extends BaseController {

    @Autowired
    private IBatchConfService iBatchConfService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IDictService iDictService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(BatchConf batchConf,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<BatchConf> pageInfo = iBatchConfService.selectByFilterAndPage(batchConf, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_batch_conf");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_batch_conf");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        Map batchStatus = iDictService.selectMapByAlias("batchStatus");
        result.put("batchStatus", batchStatus);
        Map batchProcess = iDictService.selectMapByAlias("batchProcess");
        result.put("batchProcess", batchProcess);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(BatchConf batchConf) {
        iBatchConfService.save(batchConf);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iBatchConfService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(BatchConf batchConf) {
        iBatchConfService.update(batchConf);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        BatchConf batchConf = iBatchConfService.findById(id);
        return ResponseUtil.success(batchConf);
    }

    @RequestMapping(value = "/options")
    @ResponseBody
    public List<Option> options() {
        List<Option> options = new ArrayList<>();
        List<BatchConf> list = iBatchConfService.selectByFilter(null);
        for (BatchConf item : list) {
            options.add(new Option(item.getBatchNumber(), item.getId()));
        }
        return options;
    }


    @PostMapping("/updateStatus")
    public Result updateStatus(BatchConf batchConf) {
        if(batchConf.getStatus()==1){
            batchConf.setProcess(1);
        }
        iBatchConfService.update(batchConf);
        return ResponseUtil.success();
    }
}
