package com.orange.score.module.core.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.core.model.ColumnJson;
import com.orange.score.module.core.service.IColumnJsonService;
import com.orange.score.module.core.service.ICommonQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by chenJz1012 on 2018-04-02.
 */
@RestController
@RequestMapping("/api/core/columnJson")
public class ColumnJsonController {

    @Autowired
    private IColumnJsonService iColumnJsonService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(ColumnJson columnJson,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<ColumnJson> pageInfo = iColumnJsonService.selectByFilterAndPage(columnJson, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> list = iCommonQueryService.selectFormItemsByTable("d_column_json");
        return ResponseUtil.success(list);
    }

    @PostMapping("/insert")
    @ResponseBody
    public Result insert(ColumnJson columnJson) {
        iColumnJsonService.save(columnJson);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam Integer id) {
        iColumnJsonService.deleteById(id);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(ColumnJson columnJson) {
        iColumnJsonService.update(columnJson);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    @ResponseBody
    public Result detail(@RequestParam Integer id) {
        ColumnJson modelNameLowerCamel = iColumnJsonService.findById(id);
        return ResponseUtil.success(modelNameLowerCamel);
    }
}
