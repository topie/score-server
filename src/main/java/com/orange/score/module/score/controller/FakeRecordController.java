package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.FakeRecord;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IFakeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* Created by chenJz1012 on 2018-04-04.
*/
@RestController
@RequestMapping("/api/score/fakeRecord")
public class FakeRecordController {

    @Autowired
    private IFakeRecordService iFakeRecordService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(FakeRecord fakeRecord,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<FakeRecord> pageInfo = iFakeRecordService.selectByFilterAndPage(fakeRecord, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> list = iCommonQueryService.selectFormItemsByTable("t_fake_record");
        return ResponseUtil.success(list);
    }

    @PostMapping("/insert")
    public Result insert(FakeRecord fakeRecord) {
        iFakeRecordService.save(fakeRecord);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iFakeRecordService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(FakeRecord fakeRecord) {
        iFakeRecordService.update(fakeRecord);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        FakeRecord fakeRecord = iFakeRecordService.findById(id);
        return ResponseUtil.success(fakeRecord);
    }
}
