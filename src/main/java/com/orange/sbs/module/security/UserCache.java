package com.orange.sbs.module.security;

import com.orange.sbs.common.tools.cache.RedisCache;
import com.orange.sbs.module.security.dto.FunctionDTO;
import com.orange.sbs.module.security.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by cgj on 2016/4/13.
 */
@Component
public class UserCache implements org.springframework.security.core.userdetails.UserCache, InitializingBean {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails getUserFromCache(String username) {
        UserDetails userDetails = null;
        try {
            userDetails = (UserDetails) redisCache.get(SecurityConstant.USER_CACHE_PREFIX + username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return userDetails == null ? null : userDetails;
        }

    }

    @Override
    public void putUserInCache(UserDetails user) {
        redisCache.set(SecurityConstant.USER_CACHE_PREFIX + user.getUsername(), user);
        List<FunctionDTO> functionList = userService.findUserFunctionByLoginName(user.getUsername());
        redisCache.set(SecurityConstant.FUNCTION_CACHE_PREFIX + user.getUsername(), functionList);
    }

    @Override
    public void removeUserFromCache(String username) {
        redisCache.del(SecurityConstant.USER_CACHE_PREFIX + username);
        redisCache.del(SecurityConstant.FUNCTION_CACHE_PREFIX + username);
    }

    public void removeUserFromCacheByUserId(Integer userId) {
        String loginName = userService.findLoginNameByUserId(userId);
        if (loginName != null) {
            removeUserFromCache(loginName);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
