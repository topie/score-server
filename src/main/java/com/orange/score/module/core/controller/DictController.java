package com.orange.score.module.core.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.Option;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.core.model.Dict;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-08.
 */
@RestController
@RequestMapping("/api/core/dict")
public class DictController {

    @Autowired
    private IDictService iDictService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(Dict dict, @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<Dict> pageInfo = iDictService.selectByFilterAndPage(dict, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("d_dict");
        List<FormItem> batchFormItems = iCommonQueryService.selectFormItemsByTable("d_dict-batch");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("d_dict");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        result.put("batchFormItems", batchFormItems);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(Dict dict) {
        iDictService.save(dict);
        return ResponseUtil.success();
    }

    @PostMapping("/insertBatch")
    public Result insertBatch(Dict dict, String[] textList, String[] valueList, String[] sortList) {
        for (int i = 0; i < textList.length; i++) {
            dict.setId(null);
            dict.setText(textList[i]);
            dict.setValue(Integer.valueOf(valueList[i]));
            dict.setSort(Integer.valueOf(sortList[i]));
            iDictService.save(dict);
        }
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iDictService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(Dict dict) {
        iDictService.update(dict);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Dict dict = iDictService.findById(id);
        return ResponseUtil.success(dict);
    }

    @RequestMapping(value = "/{alias}/options")
    @ResponseBody
    public List<Option> options(@PathVariable("alias") String alias) {
        return iDictService.selectOptionsByAlias(alias);
    }
}
