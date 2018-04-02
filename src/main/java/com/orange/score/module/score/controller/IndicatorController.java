package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.score.model.Indicator;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IIndicatorService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<FormItem> list = iCommonQueryService.selectFormItemsByTable("t_indicator");
        return ResponseUtil.success(list);
    }

    @PostMapping("/insert")
    public Result insert(Indicator indicator) {
        iIndicatorService.save(indicator);
        if (CollectionUtils.isNotEmpty(indicator.getMaterial())) {
            for (Integer mId : indicator.getMaterial()) {
                iIndicatorService.insertBindMaterial(indicator.getId(), mId);
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
        iIndicatorService.update(indicator);
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
}
