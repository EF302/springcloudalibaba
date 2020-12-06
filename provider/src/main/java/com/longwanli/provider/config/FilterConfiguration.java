package com.longwanli.provider.config;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    //手动关闭URL收敛。为测试“链路流控模式”
    @Bean
    public FilterRegistrationBean registrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new CommonFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter(CommonFilter.WEB_CONTEXT_UNIFY, "false");
        registrationBean.setName("sentinelFilter");
        return registrationBean;
    }
}