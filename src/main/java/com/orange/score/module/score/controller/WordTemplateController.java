package com.orange.score.module.score.controller;

import com.orange.score.common.core.BaseController;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.freemarker.FreeMarkerUtil;
import com.orange.score.common.utils.ResponseUtil;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-04.
 */
@RestController
@RequestMapping("/api/score/wordTemplate")
public class WordTemplateController extends BaseController {

    @GetMapping(value = "/html")
    @ResponseBody
    public Result html() throws FileNotFoundException {
        Map params = new HashMap();
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "word_template.ftl", params);
        Map result = new HashMap<>();
        result.put("html", html);
        return ResponseUtil.success(result);
    }

}
