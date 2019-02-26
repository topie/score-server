package com.orange.score.module.score.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.Option;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.score.model.Indicator;
import com.orange.score.database.score.model.IndicatorItem;
import com.orange.score.database.score.model.IndicatorJson;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.IIndicatorItemService;
import com.orange.score.module.score.service.IIndicatorJsonService;
import com.orange.score.module.score.service.IIndicatorService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-02.
 */
@RestController
@RequestMapping("/api/score/indicator")
public class IndicatorController {

    @Autowired
    private IIndicatorService iIndicatorService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IIndicatorItemService iIndicatorItemService;

    @Autowired
    private IIndicatorJsonService iIndicatorJsonService;

    @Autowired
    private IDictService iDictService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(Indicator indicator,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<Indicator> pageInfo = iIndicatorService.selectByFilterAndPage(indicator, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List formItems = iCommonQueryService.selectFormItemsByTable("t_indicator");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_indicator");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        Map scoreRule = iDictService.selectMapByAlias("scoreRule");
        result.put("scoreRule", scoreRule);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(Indicator indicator) {
        iIndicatorService.save(indicator);
        if (CollectionUtils.isNotEmpty(indicator.getMaterial())) {
            iIndicatorService.deleteBindMaterial(indicator.getId());
            for (Integer mId : indicator.getMaterial()) {
                iIndicatorService.insertBindMaterial(indicator.getId(), mId);
            }
        }
        if (CollectionUtils.isNotEmpty(indicator.getDepartment())) {
            iIndicatorService.deleteBindDepartment(indicator.getId());
            for (Integer dId : indicator.getDepartment()) {
                iIndicatorService.insertBindDepartment(indicator.getId(), dId);
            }
        }
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iIndicatorService.deleteById(id);
        iIndicatorService.deleteBindMaterial(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(Indicator indicator) {
        iIndicatorService.deleteBindMaterial(indicator.getId());
        iIndicatorService.update(indicator);
        if (CollectionUtils.isNotEmpty(indicator.getMaterial())) {
            for (Integer mId : indicator.getMaterial()) {
                iIndicatorService.insertBindMaterial(indicator.getId(), mId);
            }
        }
        iIndicatorService.deleteBindDepartment(indicator.getId());
        if (CollectionUtils.isNotEmpty(indicator.getDepartment())) {
            iIndicatorService.deleteBindDepartment(indicator.getId());
            for (Integer dId : indicator.getDepartment()) {
                iIndicatorService.insertBindDepartment(indicator.getId(), dId);
            }
        }
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Indicator indicator = iIndicatorService.findById(id);
        List<Integer> mtIds = iIndicatorService.selectBindMaterialIds(id);
        List<Integer> depIds = iIndicatorService.selectBindDepartmentIds(id);
        indicator.setMaterial(mtIds);
        indicator.setDepartment(depIds);
        return ResponseUtil.success(indicator);
    }

    @RequestMapping(value = "/department/treeNodes", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeNode> treeNodes() {
        return iIndicatorService.selectDepartmentTreeNodes();
    }

    @RequestMapping(value = "/department/treeNodesIndicators", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeNode> treeNodesIndicators(){
        return iIndicatorService.selectDepartmentTreeNodesIndicators();
    }

    @PostMapping("/generateJson")
    public Result generateJson(@RequestParam Integer batchId) {
        JSONArray mainArray = JSONArray.parseArray("[]");
        List<Indicator> indicators = iIndicatorService.findAll();
        for (Indicator indicator : indicators) {
            JSONObject jsonObject = JSONObject.parseObject("{}");
            jsonObject.put("name", indicator.getName());
            jsonObject.put("id", indicator.getId());
            jsonObject.put("itemType", indicator.getItemType());
            jsonObject.put("index", indicator.getIndexNum());
            IndicatorItem item = new IndicatorItem();
            item.setIndicatorId(indicator.getId());
            List<IndicatorItem> items = iIndicatorItemService.selectByFilter(item);
            if (items.size() > 0) {
                JSONArray itemArray = JSONArray.parseArray("[]");
                for (IndicatorItem indicatorItem : items) {
                    JSONObject itemObject = JSONObject.parseObject("{}");
                    itemObject.put("text", indicatorItem.getContent());
                    itemObject.put("value", indicatorItem.getId());
                    itemObject.put("score", indicatorItem.getScore());
                    itemArray.add(itemObject);
                }
                jsonObject.put("items", itemArray);
            }
            mainArray.add(jsonObject);
        }
        Map result = new HashMap<>();
        result.put("html", mainArray.toJSONString());
        IndicatorJson json = iIndicatorJsonService.findBy("batchId", batchId);
        if (json != null) {
            json.setJson(mainArray.toJSONString());
            iIndicatorJsonService.update(json);
        } else {
            json = new IndicatorJson();
            json.setBatchId(batchId);
            json.setJson(mainArray.toJSONString());
            iIndicatorJsonService.save(json);
        }
        return ResponseUtil.success(result);
    }

    @RequestMapping(value = "/options")
    @ResponseBody
    public List<Option> options() {
        List<Option> options = new ArrayList<>();
        List<Indicator> list = iIndicatorService.selectByFilter(null);
        for (Indicator item : list) {
            options.add(new Option(item.getName(), item.getId()));
        }
        return options;
    }
}
