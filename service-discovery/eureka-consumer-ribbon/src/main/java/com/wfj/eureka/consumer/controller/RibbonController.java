package com.wfj.eureka.consumer.controller;

import com.wfj.eureka.consumer.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * ribbon模式,以下两种方式不能并存
 * @author wjg
 * @date 2017/12/7 16:16
 */
@RestController
@RequestMapping("user")
public class RibbonController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    /**
     * 可以使用LoadBalancerClient的choose方法获取服务提供者的地址，choose已实现了负载均衡
     * @param id 用户id
     * @return  User
     */
    @RequestMapping("getUser2")
    public User getUser2(Integer id){
        ServiceInstance instance = loadBalancerClient.choose("eureka-provider");
        return restTemplate.getForObject("http://"+instance.getHost()+":"+instance.getPort()+"/user/getUser.json?id="+id,User.class);
    }

    /**
     * 例子中直接使用了"EUREKA-PROVIDER"的虚拟主机名，当Ribbon和Eureka配合使用时，会自动将虚拟主机名
     * 映射微微服务的网络地址。
     * @param id  用户id
     * @return  User
     */
    @RequestMapping("getUser3")
    public User getUser3(Integer id){
        return restTemplate.getForObject("http://EUREKA-PROVIDER/user/getUser.json?id="+id,User.class);
    }



}
