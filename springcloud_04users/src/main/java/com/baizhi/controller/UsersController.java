package com.baizhi.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("users")
public class UsersController {

    //服务注册与发现客户端
    @Autowired
    DiscoveryClient discoveryClient;

    //负载均衡的客户端对象
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    /*@GetMapping
    public String invokeDemo(){

        log.info("user demo ...");
        //调用订单服务，服务地址：http://localhost:8080/orders  必须Get方式，接收返回值类型String
        RestTemplate restTemplate = new RestTemplate();
        String orderResutl = restTemplate.getForObject("http://"+ randomHost() + "/orders", String.class);
        log.info("調用訂單服務成功,結果為{}",orderResutl);
        return "user demo ok!!!,结果为" + orderResutl;
    }*/

    @GetMapping
    public String invokeDemo(){

        //用ribbon组件 + RestTemplate实现负载均衡调用 DiscoverClient LoadBalanceClient @LoadBalance

        //2.通过discoveryClient服务发现，像consul注册中心查找拉取到所有服务名称为ORDERS的服务
        /*List<ServiceInstance> orders = discoveryClient.getInstances("ORDERS");
        orders.forEach(serviceInstance -> {
            log.info("服务的主机:{},服务的端口:{},服务的uri:{}",serviceInstance.getHost(),serviceInstance.getPort(),serviceInstance.getUri());
        });

        String result = new RestTemplate().getForObject(orders.get(0).getUri() + "/orders", String.class);*/


        //3.负载均衡,根据服务的ID去服务注册中心获取服务的列表，并根据默认的负载均衡策略选择列表中的一个服务进行返回，默认是轮询策略
        /*ServiceInstance instance = loadBalancerClient.choose("ORDERS");
        log.info("服务的主机:{},服务的端口:{},服务的uri:{}",instance.getHost(),instance.getPort(),instance.getUri());
        String result = new RestTemplate().getForObject(instance.getUri() + "/orders", String.class);*/

        //4.使用@LoadBalance注解，作用：让RestTemplate对象具有Ribbon负载均衡特性
        String result = restTemplate.getForObject("http://ORDERS/orders", String.class);


        log.info("result:{}",result);

        return "user demo ok!!!" + result;
    }


    /**
     * 手動寫負載均衡冊羅
     */
    private String randomHost(){

        ArrayList<String> hostList = new ArrayList<>();
        hostList.add("localhost:8080");
        hostList.add("localhost:8081");
        //生成随机数 只能在0 - hostList.size之间
        int i = new Random().nextInt(hostList.size());
        return hostList.get(i);
    }
}
