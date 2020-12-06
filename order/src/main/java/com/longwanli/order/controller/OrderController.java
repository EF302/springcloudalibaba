package com.longwanli.order.controller;

import com.longwanli.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 模拟分布式异常：实现在order数据库插入一条数据时，把该数据同步到pay数据库
     *
     * @return
     */
    @GetMapping("/save")
    @GlobalTransactional
    public String save() {
        //订单
        this.orderService.save();
        //异常：导致同步更新数据库pay失败
        int i = 10 / 0;
        //支付
        this.restTemplate.getForObject("http://localhost:8020/save", String.class);
        return "success";
    }
}