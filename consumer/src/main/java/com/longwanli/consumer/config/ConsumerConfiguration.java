package com.longwanli.consumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * bean配置类
 */
@Configuration
public class ConsumerConfiguration {

    /**
     * 注入RestTemplate对象
     *
     * @return
     */
    @Bean
    /*!!!!!!!!!注意：用了 @LoadBalanced注解后，restTemplate对象访问接口时，URL不能再用具体的String形式请求地址，只能用服务名代替URI。
    因为要隐藏端口，用负载均衡算法决定使用哪个端口。
    */
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
