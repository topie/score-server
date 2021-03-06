package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.database.score.model.AcceptAddress;
import com.orange.score.database.score.model.AcceptDateConf;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IAcceptAddressService;
import com.orange.score.module.score.service.IAcceptDateConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-10.
 */
@RestController
@RequestMapping("/api/score/acceptDateConf")
public class AcceptDateConfController {

    @Autowired
    private IAcceptDateConfService iAcceptDateConfService;

    @Autowired
    private IAcceptAddressService iAcceptAddressService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(AcceptDateConf acceptDateConf,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<AcceptDateConf> pageInfo = iAcceptDateConfService
                .selectByFilterAndPage(acceptDateConf, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_accept_date_conf");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_accept_date_conf");

        List<AcceptAddress> list = iAcceptAddressService.selectByFilter(null);
        Map addressMap = new HashMap();
        for (AcceptAddress acceptAddress : list) {
            addressMap.put(acceptAddress.getId(), acceptAddress.getAddress());
        }
        Map isWorkingDayMap = new HashMap();
        isWorkingDayMap.put("0","不是");
        isWorkingDayMap.put("1","是");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        result.put("addressMap", addressMap);
        result.put("isWorkingDayMap", isWorkingDayMap);
        return ResponseUtil.success(result);
    }

    @PostMapping("/insert")
    public Result insert(AcceptDateConf acceptDateConf) {
        Condition condition = new Condition(AcceptDateConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("batchId", acceptDateConf.getBatchId());
        criteria.andEqualTo("addressId", acceptDateConf.getAddressId());
        criteria.andEqualTo("acceptDate", acceptDateConf.getAcceptDate());
        List<AcceptDateConf> confs = iAcceptDateConfService.findByCondition(condition);
        if(confs.size()>0){
            return ResponseUtil.error("受理日期重复！");
        }
        if (acceptDateConf.getAcceptDate() != null) {
            acceptDateConf.setWeekDay(DateUtil.getWeek(acceptDateConf.getAcceptDate()).getChineseName());
        }
        acceptDateConf.setAmRemainingCount(acceptDateConf.getAmUserCount());
        acceptDateConf.setPmRemainingCount(acceptDateConf.getPmUserCount());
        iAcceptDateConfService.save(acceptDateConf);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iAcceptDateConfService.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(AcceptDateConf acceptDateConf) {
        Condition condition = new Condition(AcceptDateConf.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("batchId", acceptDateConf.getBatchId());
        criteria.andEqualTo("addressId", acceptDateConf.getAddressId());
        criteria.andEqualTo("acceptDate", acceptDateConf.getAcceptDate());
        criteria.andNotEqualTo("id", acceptDateConf.getId());
        List<AcceptDateConf> confs = iAcceptDateConfService.findByCondition(condition);
        if(confs.size()>0){
            return ResponseUtil.error("受理日期重复！");
        }
        if (acceptDateConf.getAcceptDate() != null) {
            acceptDateConf.setWeekDay(DateUtil.getWeek(acceptDateConf.getAcceptDate()).getChineseName());
        }

        /*
        2019年10月30日，完善后台管理员增加预约名额
        先获取剩余人数，在剩余人数基础上增加变动的名额
         */
        AcceptDateConf byId = iAcceptDateConfService.findById(acceptDateConf.getId());
        int changeCountAm = acceptDateConf.getAmUserCount() - byId.getAmUserCount();// 上午变动
        int changeCountPm = acceptDateConf.getPmUserCount() - byId.getPmUserCount();// 下午变动
        acceptDateConf.setAmRemainingCount(byId.getAmRemainingCount()+changeCountAm);
        acceptDateConf.setPmRemainingCount(byId.getPmRemainingCount() + changeCountPm);
        iAcceptDateConfService.update(acceptDateConf);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        AcceptDateConf acceptDateConf = iAcceptDateConfService.findById(id);
        return ResponseUtil.success(acceptDateConf);
    }
}
