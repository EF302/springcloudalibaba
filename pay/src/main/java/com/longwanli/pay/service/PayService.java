package com.longwanli.pay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PayService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save() {
        this.jdbcTemplate.update("insert into pay(name) values ('张三')");
    }
}