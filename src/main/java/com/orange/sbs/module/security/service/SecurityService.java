package com.orange.sbs.module.security.service;

import com.orange.sbs.module.security.SecurityUser;
import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.Map;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/4 说明：
 */
public interface SecurityService {

    SecurityUser loadSecurityUserByLoginName(String loginName);

    Map<String, Collection<ConfigAttribute>> getCacheResourceMap();

    Map<String, Collection<ConfigAttribute>> getDbResourceMap();

    String getDefaultAction(int roleId);
}
