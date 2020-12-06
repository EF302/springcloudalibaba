package com.longwanli.provider.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * “授权规则”配置类，同时把该配置类注入到IOC容器才能生效
 */
public class RequestOriginParserDefinition implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest){
        //通过追加参数名为name的参数进行授权控制，即现在访问的请求必须带有“name”参数
        //!!!!!!!!!注意：全局影响，此后，各请求必须带上name参数。
        String name= httpServletRequest.getParameter("name");
        if(StringUtils.isEmpty(name)){
            throw new RuntimeException("name is null!");
        }
        return name;
    }
}
