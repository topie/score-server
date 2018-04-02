package com.orange.score.module.core.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.core.model.Example;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by chenJz1012 on 2017/12/27.
 */
@RestController
@RequestMapping("/api/core/example")
public class ExampleController {

    @Autowired
    private IExampleService iExampleService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(Example example,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<Example> pageInfo = iExampleService.selectByFilterAndPage(example, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> list = iCommonQueryService.selectFormItemsByTable("d_example");
        return ResponseUtil.success(list);
    }

    @RequestMapping("/insert")
    @ResponseBody
    public Result insert(Example example) {
        iExampleService.save(example);
        return ResponseUtil.success();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam Integer id) {
        iExampleService.deleteById(id);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(Example example) {
        iExampleService.update(example);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    @ResponseBody
    public Result detail(@RequestParam Integer id) {
        Example modelNameLowerCamel = iExampleService.findById(id);
        return ResponseUtil.success(modelNameLowerCamel);
    }
}
