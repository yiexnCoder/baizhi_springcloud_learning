package com.baizhi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("orders")
public class OrdersController {


    @Value("${server.port}")
    private int port;

    @GetMapping
    public String orderDemo(){
        log.info("order demo...");
        return "Order demo OK!!!当前服务的端口为：" + port;
    }

}
