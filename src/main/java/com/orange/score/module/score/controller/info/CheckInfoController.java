package com.orange.score.module.score.controller.info;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.BatchConf;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-08.
 */
@RestController
@RequestMapping("/api/score/info/checkInfo")
public class CheckInfoController {

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IDictService iDictService;

    @Autowired
    private IBatchConfService iBatchConfService;

    @Autowired
    private IHouseOtherService iHouseOtherService;

    @Autowired
    private IHouseMoveService iHouseMoveService;

    @Autowired
    private IHouseProfessionService iHouseProfessionService;

    @Autowired
    private IHouseRelationshipService iHouseRelationshipService;

    @Autowired
    private IMaterialInfoService iMaterialInfoService;

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private IOnlinePersonMaterialService iOnlinePersonMaterialService;

    @GetMapping(value = "/batch/list")
    @ResponseBody
    public Result batchList(BatchConf batchConf,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<BatchConf> pageInfo = iBatchConfService.selectByFilterAndPage(batchConf, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/batch/formItems")
    @ResponseBody
    public Result batchFormItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_batch_conf");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_batch_conf");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        Map batchStatus = iDictService.selectMapByAlias("batchStatus");
        result.put("batchStatus", batchStatus);
        Map batchProcess = iDictService.selectMapByAlias("batchProcess");
        result.put("batchProcess", batchProcess);
        return ResponseUtil.success(result);
    }

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(IdentityInfo identityInfo,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<IdentityInfo> pageInfo = iIdentityInfoService.selectByFilterAndPage(identityInfo, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_identity_info");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_identity_info");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        Map reservationStatus = iDictService.selectMapByAlias("reservationStatus");
        result.put("reservationStatus", reservationStatus);
        return ResponseUtil.success(result);
    }

}
