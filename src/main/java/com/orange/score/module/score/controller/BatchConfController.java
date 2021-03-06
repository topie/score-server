package com.orange.score.module.score.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.BaseController;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.freemarker.FreeMarkerUtil;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.Option;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.AcceptDateConf;
import com.orange.score.database.score.model.BatchConf;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.core.service.IDictService;
import com.orange.score.module.score.service.IAcceptDateConfService;
import com.orange.score.module.score.service.IBatchConfService;
import com.orange.score.module.score.service.IIdentityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenJz1012 on 2018-04-04.
 */
@RestController
@RequestMapping("/api/score/batchConf")
public class BatchConfController extends BaseController {

    @Autowired
    private IBatchConfService iBatchConfService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IDictService iDictService;

    @Autowired
    private IAcceptDateConfService iAcceptDateConfService;

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(BatchConf batchConf,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<BatchConf> pageInfo = iBatchConfService.selectByFilterAndPage(batchConf, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
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

    @PostMapping("/insert")
    public Result insert(BatchConf batchConf) {
        batchConf.setId(null);
        batchConf.setStatus(0);
        batchConf.setProcess(0);
        iBatchConfService.save(batchConf);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iBatchConfService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(BatchConf batchConf) {
        iBatchConfService.update(batchConf);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        BatchConf batchConf = iBatchConfService.findById(id);
        return ResponseUtil.success(batchConf);
    }

    @RequestMapping(value = "/options")
    @ResponseBody
    public List<Option> options() {
        List<Option> options = new ArrayList<>();
        List<BatchConf> list = iBatchConfService.selectByFilter(null);
        for (BatchConf item : list) {
            Option option = new Option(item.getBatchName(), item.getId());
            if (item.getStatus() == 1) {
                option.setSelected("selected");
            }
            options.add(option);
        }
        return options;
    }

    @PostMapping("/updateStatus")
    public Result updateStatus(BatchConf batchConf) {
        if (batchConf.getStatus() == 1) {
            batchConf.setProcess(1);
        }
        iBatchConfService.update(batchConf);
        return ResponseUtil.success();
    }

    @GetMapping(value = "/acceptDateList")
    @ResponseBody
    public Result acceptDateList(AcceptDateConf acceptDateConf) throws FileNotFoundException {
        Map params = new HashMap();
        List<AcceptDateConf> list = iAcceptDateConfService.selectByFilter(acceptDateConf);
        params.put("list", list);
        List<Date> dateList = iAcceptDateConfService.selectDistinctDateList(acceptDateConf);
        params.put("dateList", dateList);
        String templatePath = ResourceUtils.getFile("classpath:templates/").getPath();
        String html = FreeMarkerUtil.getHtmlStringFromTemplate(templatePath, "batch_reservation.ftl", params);
        Map result = new HashMap();
        result.put("html", html);
        return ResponseUtil.success(result);
    }

    /**
     * 2020年8月6日
     * 统计数据给统计系统
     * @return
     */
    @RequestMapping("/provideDataToZhangLaoshi")
    public Result provideDataToZhangLaoshi(HttpServletRequest httpServletRequest,
                                           HttpServletResponse httpServletResponse){
        String strDate = "2018-07";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date201807;
        Result result = new Result();



        try {
            date201807 = sdf.parse(strDate);
            Condition condition = new Condition(BatchConf.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
            criteria.andGreaterThan("applyBegin",date201807);
            condition.orderBy("id").asc();
            List<BatchConf> list = iBatchConfService.findByCondition(condition);

            JSONArray jsonArray = new JSONArray();

            for(BatchConf batchConf : list){
                condition = new Condition(IdentityInfo.class);
                criteria = condition.createCriteria();
                criteria.andEqualTo("batchId", batchConf.getId());
                List<IdentityInfo> identityInfoList = iIdentityInfoService.findByCondition(condition);

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id",batchConf.getId());
                jsonObject.put("batchName",batchConf.getBatchName());
                jsonObject.put("indicatorValue",batchConf.getIndicatorValue());
                jsonObject.put("acceptUserCount",identityInfoList.size());
                jsonObject.put("status",batchConf.getStatus());
                jsonObject.put("scoreValue",batchConf.getScoreValue());
                jsonArray.add(jsonObject);
            }

            result.setCode(1000);
            result.setMessage("成功");
            result.setData(jsonArray);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  result;
    }
}
