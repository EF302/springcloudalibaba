package com.longwanli.provider.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.longwanli.provider.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("providerController")
public class ProviderController {

    //使用SpringEL表达式注入值
    @Value("${server.port}")
    private String port;

    /**
     * 返回服务器当前端口
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return this.port;
    }

    //=================================为测试“关联流控模式”添加list接口=================
    @GetMapping("/list")
    public String list() {
        return "list";
    }

    //=================================为测试“链路流控模式”添加test1、test2接口=================
    @Autowired
    private ProviderService providerService;

    @GetMapping("/test1")
    public String test1() {
        this.providerService.test();
        return "test1";
    }

    @GetMapping("/test2")
    public String test2() {
        this.providerService.test();
        return "test2";
    }

    //======================测试“降级规则——异常比例、异常数===================
    int i = 0;

    @GetMapping("/index2")
    public String index2() {
        i++;
        if (i % 2 == 0) {
            throw new RuntimeException();
        }
        return this.port;
    }

    //======================测试”热点规则”=========
    @GetMapping("/hot")
    @SentinelResource("hot")
    public String hot(@RequestParam(value = "num1", required = false) Integer num1, @RequestParam(value = "num2", required = false) Integer num2) {
        return num1 + " " + num2;
    }

    //==============测试“路由分组”=================
    @GetMapping("/api1/demo1")
    public String demo1() {
        return "demo";
    }

    @GetMapping("/api1/demo2")
    public String demo2() {
        return "demo";
    }

    @GetMapping("/api2/demo1")
    public String demo3() {
        return "demo";
    }

    @GetMapping("/api2/demo2")
    public String demo4() {
        return "demo";
    }


}
