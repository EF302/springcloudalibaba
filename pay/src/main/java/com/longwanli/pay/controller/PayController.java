package com.longwanli.pay.controller;

import com.longwanli.pay.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {

    @Autowired
    private PayService payService;

    @GetMapping("/save")
    public String save() {
        this.payService.save();
        return "success";
    }
}