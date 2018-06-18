package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.Document;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by chenJz1012 on 2018-06-18.
 */
@RestController
@RequestMapping("/api/score/document")
public class DocumentController {

    @Autowired
    private IDocumentService iDocumentService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(Document document,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<Document> pageInfo = iDocumentService.selectByFilterAndPage(document, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @PostMapping("/insert")
    public Result insert(Document document) {
        iDocumentService.save(document);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iDocumentService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(Document document) {
        iDocumentService.update(document);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Document document = iDocumentService.findById(id);
        return ResponseUtil.success(document);
    }
}
