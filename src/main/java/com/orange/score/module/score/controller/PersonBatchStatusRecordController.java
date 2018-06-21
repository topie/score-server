package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.PersonBatchStatusRecord;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IPersonBatchStatusRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-14.
 */
@RestController
@RequestMapping("/api/score/personBatchStatusRecord")
public class PersonBatchStatusRecordController {

    @Autowired
    private IPersonBatchStatusRecordService iPersonBatchStatusRecordService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(PersonBatchStatusRecord personBatchStatusRecord,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        if (StringUtils.isEmpty(personBatchStatusRecord.getPersonIdNumber())) {
            return ResponseUtil.success(PageConvertUtil.emptyGrid());
        }
        PageInfo<PersonBatchStatusRecord> pageInfo = iPersonBatchStatusRecordService
                .selectByFilterAndPage(personBatchStatusRecord, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_pb_status_record");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_pb_status_record");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(PersonBatchStatusRecord personBatchStatusRecord) {
        iPersonBatchStatusRecordService.save(personBatchStatusRecord);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iPersonBatchStatusRecordService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(PersonBatchStatusRecord personBatchStatusRecord) {
        iPersonBatchStatusRecordService.update(personBatchStatusRecord);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        PersonBatchStatusRecord personBatchStatusRecord = iPersonBatchStatusRecordService.findById(id);
        return ResponseUtil.success(personBatchStatusRecord);
    }
}
