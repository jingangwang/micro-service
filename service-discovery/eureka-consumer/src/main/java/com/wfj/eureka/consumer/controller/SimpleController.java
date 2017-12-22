package com.wfj.eureka.consumer.controller;

import com.wfj.eureka.consumer.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 *
 * @author wjg
 * @date 2017/12/7 16:16
 */
@RestController
@RequestMapping("user")
public class SimpleController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     *  不使用ribbon的方式，需要自己获取服务提供者列表，实现负载均衡
     * @param id 用户id
     * @return  User
     */
    @RequestMapping("getUser")
    public User getUser(Integer id){
        List<ServiceInstance> instances = discoveryClient.getInstances("eureka-provider");
        ServiceInstance instance = instances.get(0);
        return restTemplate.getForObject("http://"+instance.getHost()+":"+instance.getPort()+"/user/getUser.json?id="+id,User.class);
    }
}
