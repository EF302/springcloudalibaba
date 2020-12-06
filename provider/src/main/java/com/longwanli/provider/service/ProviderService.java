package com.longwanli.provider.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

    //================为测试“链路流控模式”添加test方法==============
    @SentinelResource("test")//对该方法进行限流控制
    public void test() {
        System.out.println("test");
    }
}
