package com.longwanli.provider.test;

import org.springframework.web.client.RestTemplate;

/**
 * 测试“关联流控模式”,在超“单机阈值”访问list接口的情况下，再去访问index接口
 */
public class TestList {
    public static void main(String[] args) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 1000; i++) {
            restTemplate.getForObject("http://localhost:8083/providerController/list", String.class);
            Thread.sleep(200);
        }
    }
}
