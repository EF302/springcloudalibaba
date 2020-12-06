package com.longwanli.provider.test;

import org.springframework.web.client.RestTemplate;

/**
 * 测试“降级规则——RT、异常数比例、异常数”
 */
public class TestDropRules {
    public static void main(String[] args) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 100; i++) {
            restTemplate.getForObject("http://localhost:8083/providerController/index", String.class);
            Thread.sleep(10);
        }
    }
}
