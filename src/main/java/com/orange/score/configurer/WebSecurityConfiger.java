package com.orange.score.configurer;

import com.orange.score.module.security.*;
import com.orange.score.module.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiger extends WebSecurityConfigurerAdapter {

    @Autowired
    private HttpAccessDeniedHandler httpAccessDeniedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserCache userCache;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private HttpAuthenticationEntryPoint httpAuthenticationEntryPoint;

    @Value("${security.token.header}")
    private String tokenHeader;

    @Value("${security.token.front}")
    private String frontToken;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserCache(this.userCache);
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        authenticationManagerBuilder.eraseCredentials(false).authenticationProvider(daoAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterInvocationSecurityMetadataSource securityMetadataSource() {
        return new SecurityMetadataSourceImpl(this.securityService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring()
                .antMatchers("/download/**", "/upload/**", "/api/token/login", "/api/token/refresh", "/api/noneAuth/**",
                        "/static/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().exceptionHandling().authenticationEntryPoint(httpAuthenticationEntryPoint)
                .accessDeniedHandler(httpAccessDeniedHandler).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers("/api/token/logout").permitAll().anyRequest().authenticated().and().headers()
                .frameOptions().disable();
        HttpAuthenticationTokenFilter httpAuthenticationTokenFilter = new HttpAuthenticationTokenFilter();
        httpAuthenticationTokenFilter.setTokenUtils(this.tokenUtils);
        httpAuthenticationTokenFilter.setUserCache(this.userCache);
        httpAuthenticationTokenFilter.setTokenHeader(this.tokenHeader);
        httpAuthenticationTokenFilter.setFrontToken(this.frontToken);
        httpAuthenticationTokenFilter.setAuthenticationFailureHandler(new HttpAuthenticationFailureHandler());
        httpSecurity.addFilterBefore(httpAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        SecurityInterceptor securityInterceptor = new SecurityInterceptor();
        securityInterceptor.setSecurityMetadataSource(securityMetadataSource());
        securityInterceptor.setAccessDecisionManager(new AccessDecisionManager());
        httpSecurity.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);

    }
}
