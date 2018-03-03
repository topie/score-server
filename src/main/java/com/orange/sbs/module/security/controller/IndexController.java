package com.orange.sbs.module.security.controller;

import com.orange.sbs.common.core.Result;
import com.orange.sbs.common.exception.AuBzConstant;
import com.orange.sbs.common.exception.AuthBusinessException;
import com.orange.sbs.common.tools.cache.RedisCache;
import com.orange.sbs.common.utils.ResponseUtil;
import com.orange.sbs.database.security.model.User;
import com.orange.sbs.module.security.SecurityConstant;
import com.orange.sbs.module.security.SecurityUser;
import com.orange.sbs.module.security.SecurityUtil;
import com.orange.sbs.module.security.UserCache;
import com.orange.sbs.module.security.dto.FunctionDTO;
import com.orange.sbs.module.security.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguojun on 8/23/16.
 */
@Controller
@RequestMapping("/api/index")
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    RedisCache redisCache;

    @Autowired
    UserCache userCache;

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseBody
    public Result current() {
        SecurityUser user = SecurityUtil.getCurrentSecurityUser();
        if (user == null || StringUtils.isEmpty(user.getUsername())) {
            throw new AuthBusinessException(AuBzConstant.IS_NOT_LOGIN);
        }
        List<FunctionDTO> functionList = (List<FunctionDTO>) redisCache
                .get(SecurityConstant.FUNCTION_CACHE_PREFIX + user.getUsername());
        if (functionList == null) {
            functionList = userService.findUserFunctionByLoginName(user.getUsername());
            redisCache.set(SecurityConstant.FUNCTION_CACHE_PREFIX + user.getUsername(), functionList);
        }
        Map info = new HashMap();
        info.put("user", user);
        info.put("functionList", functionList);
        return ResponseUtil.success(info);
    }

    @RequestMapping(value = "/loadCurrentUser", method = RequestMethod.GET)
    @ResponseBody
    public Result loadCurrentUser() {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null || StringUtils.isEmpty(securityUser.getUsername())) {
            throw new AuthBusinessException(AuBzConstant.IS_NOT_LOGIN);
        }
        User user = userService.findUserById(securityUser.getId());
        return ResponseUtil.success(user);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public Result updateUser(User user, @RequestParam(value = "newPassword", required = false) String newPassword) {
        Integer currentId = SecurityUtil.getCurrentUserId();
        if (currentId == null) {
            throw new AuthBusinessException(AuBzConstant.IS_NOT_LOGIN);
        }
        if (user.getId() == null) {
            throw new AuthBusinessException("未检测到用户ID");
        }
        if (currentId.intValue() != user.getId().intValue()) {
            throw new AuthBusinessException("不能修改他人用户信息");
        }
        User u = userService.findUserById(user.getId());
        if (u == null) {
            throw new AuthBusinessException("用户不存在");
        }
        user.setLoginName(u.getLoginName());
        if (userService.findExistUser(user) > 0) {
            throw new AuthBusinessException(AuBzConstant.LOGIN_NAME_EXIST);
        }
        if (!SecurityUtil.matchString(user.getPassword(), u.getPassword())) {
            throw new AuthBusinessException("旧密码不正确");
        }
        if (StringUtils.isNotEmpty(newPassword)) {
            user.setPassword(newPassword);
        }
        userService.updateUser(user);
        return ResponseUtil.success();
    }

}
