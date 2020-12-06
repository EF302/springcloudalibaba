package com.longwanli.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 向orders表中插入一条数据
     */
    public void save() {
        this.jdbcTemplate.update("insert into orders(name) values ('张三')");
    }
}