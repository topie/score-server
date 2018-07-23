package com.orange.score.module.score.controller.cms;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.QuestionAnswer;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IQuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-08.
 */
@RestController
@RequestMapping("/api/score/cms/question")
public class QuestionController {

    @Autowired
    private IQuestionAnswerService iQuestionAnswerService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(QuestionAnswer questionAnswer,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<QuestionAnswer> pageInfo = iQuestionAnswerService
                .selectByFilterAndPage(questionAnswer, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_common_question");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_common_question");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(QuestionAnswer questionAnswer) {
        questionAnswer.setcTime(new Date());
        iQuestionAnswerService.save(questionAnswer);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iQuestionAnswerService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(QuestionAnswer questionAnswer) {
        iQuestionAnswerService.update(questionAnswer);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        QuestionAnswer questionAnswer = iQuestionAnswerService.findById(id);
        return ResponseUtil.success(questionAnswer);
    }

}
