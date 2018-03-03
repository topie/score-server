package com.orange.sbs.module.security;

import com.orange.sbs.common.utils.HttpResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class HttpAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException,
            ServletException {
        HttpResponseUtil.error(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED, "未授权访问");
    }

}
