package com.orange.score.module.score.controller;

import com.orange.score.common.core.Result;
import com.orange.score.common.exception.AuthBusinessException;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.common.utils.date.Week;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.module.score.service.IIdentityInfoService;
import com.orange.score.module.security.SecurityUser;
import com.orange.score.module.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-08.
 */
@RestController
@RequestMapping("/api/score/index")
public class ScoreIndexController {

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @GetMapping(value = "/reservationCount")
    @ResponseBody
    public Result batchList() {
        Map result = new HashMap<>();
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        Date today = DateUtil.getToday();
        Condition condition = new Condition(IdentityInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("reservationDate", today);
        if (securityUser.getUserType() == 0) {
            result.put("address", "市行政审批");
            criteria.andEqualTo("acceptAddressId", 1);
        } else if (securityUser.getUserType() == 1) {
            result.put("address", "滨海区");
            criteria.andEqualTo("acceptAddressId", 2);
        }
        int cnt = iIdentityInfoService.countByCondition(condition);
        result.put("cnt", cnt);

        condition = new Condition(IdentityInfo.class);
        criteria = condition.createCriteria();
        Week week = DateUtil.getWeek(today);
        if (week.getNumber() == 5){
            today = DateUtil.addDay(today, 2);
        }
        criteria.andEqualTo("reservationDate", DateUtil.addDay(today, 1));
        if (securityUser.getUserType() == 0) {
            result.put("address2", "市行政审批");
            criteria.andEqualTo("acceptAddressId", 1);
        } else if (securityUser.getUserType() == 1) {
            result.put("address2", "滨海区");
            criteria.andEqualTo("acceptAddressId", 2);
        }
        int cnt2 = iIdentityInfoService.countByCondition(condition);
        result.put("cnt2", cnt2);

        condition = new Condition(IdentityInfo.class);
        criteria = condition.createCriteria();
        week = DateUtil.getWeek(today);
        if (week.getNumber() == 1){
            today = DateUtil.addDay(today, -2);
        }
        criteria.andEqualTo("reservationDate", DateUtil.addDay(today, -1));
        if (securityUser.getUserType() == 0) {
            result.put("address3", "市行政审批");
            criteria.andEqualTo("acceptAddressId", 1);
        } else if (securityUser.getUserType() == 1) {
            result.put("address3", "滨海区");
            criteria.andEqualTo("acceptAddressId", 2);
        }
        int cnt3 = iIdentityInfoService.countByCondition(condition);
        result.put("cnt3", cnt3);
        return ResponseUtil.success(result);
    }

}
