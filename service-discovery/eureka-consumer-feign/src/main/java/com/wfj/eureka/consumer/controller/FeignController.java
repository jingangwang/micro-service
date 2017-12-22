package com.wfj.eureka.consumer.controller;

import com.wfj.eureka.consumer.feign.UserFeignClient;
import com.wfj.eureka.consumer.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wjg
 * @date 2017/12/8 14:14
 */
@RestController
@RequestMapping("user")
public class FeignController {
    @Autowired
    private UserFeignClient userFeignClient;
    @RequestMapping("getUser")
    public User getUser(Integer id){
        return userFeignClient.findUserById(id);
    }
}
