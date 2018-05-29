package com.orange.score.module.score.controller.stat;

import com.orange.score.common.core.Result;
import com.orange.score.common.tools.freemarker.FreeMarkerUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.Indicator;
import com.orange.score.database.score.model.IndicatorItem;
import com.orange.score.database.score.model.ScoreRecord;
import com.orange.score.module.score.service.IIndicatorItemService;
import com.orange.score.module.score.service.IIndicatorService;
import com.orange.score.module.score.service.IScoreResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/score/stat/item")
public class StatItemController {

    @Autowired
    IIndicatorService iIndicatorService;

    @Autowired
    IIndicatorItemService iIndicatorItemService;

    @Autowired
    IScoreResultService iScoreResultService;

    @GetMapping("/html")
    public Result html(@RequestParam Integer batchId, @RequestParam(required = false) Integer companyId)
            throws FileNotFoundException {
        Map result = new HashMap();
        Map params = new HashMap();
        List<Indicator> indicators = iIndicatorService.findAll();
        List iList = new ArrayList<>();
        for (Indicator indicator : indicators) {
            Integer total = 0;
            Map iMap = new HashMap();
            iMap.put("id", indicator.getId());
            iMap.put("name", indicator.getName());
            iList.add(iMap);
            Condition condition = new Condition(IndicatorItem.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
            criteria.andEqualTo("indicatorId", indicator.getId());
            List<IndicatorItem> items = iIndicatorItemService.findByCondition(condition);
            List itemList = new ArrayList();
            iMap.put("items", itemList);
            for (IndicatorItem item : items) {
                Map itMap = new HashMap();
                itMap.put("id", item.getId());
                itMap.put("name", item.getContent());
                ScoreRecord scoreRecord = new ScoreRecord();
                scoreRecord.setBatchId(batchId);
                scoreRecord.setIndicatorId(indicator.getId());
                scoreRecord.setItemId(item.getId());
                int personCount = iScoreResultService.selectCountByFilter(scoreRecord);
                itMap.put("total", personCount);
                total += personCount;
                itemList.add(itMap);
            }
            iMap.put("total", total);
            iList.add(iMap);
        }
        params.put("iList", iList);
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "batch_score_stat.ftl", params);
        result.put("html", html);
        return ResponseUtil.success(result);
    }
}
