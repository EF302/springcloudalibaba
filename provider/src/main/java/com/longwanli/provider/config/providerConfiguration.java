package com.longwanli.provider.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import org.springframework.context.annotation.Configuration;

/**
 * 容器类
 */
@Configuration
public class providerConfiguration {

    /**
     * 注入“授权规则”配置bean
     */
    //@PostConstruct用于在依赖关系注入完成之后，需要执行的方法上,在spring容器启动的时候执行，以执行任何初始化。
//    @PostConstruct//再进行其他测试时，消除“授权规则”配置的注入，不然请求路径中都要加name参数
    public void init(){
        WebCallbackManager.setRequestOriginParser(new RequestOriginParserDefinition());
    }


}
