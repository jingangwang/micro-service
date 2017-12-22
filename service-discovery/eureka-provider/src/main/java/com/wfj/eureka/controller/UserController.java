package com.wfj.eureka.controller;

import com.wfj.eureka.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wjg
 * @date 2017/12/7 15:38
 */
@RestController
@RequestMapping("user")
public class UserController {

    @RequestMapping("getUser")
    public User getUser(Integer id) {
        return new User(id, "test", 29);
    }

    @RequestMapping("getAllUser")
    public List<User> getAllUser(String ids){
        String[] split = ids.split(",");
        return Arrays.asList(split)
                .stream()
                .map(id -> new User(Integer.valueOf(id),"test"+id,30))
                .collect(Collectors.toList());
    }
}
