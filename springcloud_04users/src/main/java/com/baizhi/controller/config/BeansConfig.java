package com.baizhi.controller.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration //代表这是一个springboot配置类， 就相当于spring.xml文件，<bean id=  class=">
public class BeansConfig {


    @Bean //放入spring容器中
    @LoadBalanced //
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
