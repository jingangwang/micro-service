package com.wfj.controller;

import com.wfj.pojo.User;
import com.wfj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * 测试Scope.GLOBAL的请求合并
 * @author wjg
 * @date 2017/12/21 15:17
 */
@RequestMapping("user")
@RestController
public class HystrixRequestCollapserController {
    @Autowired
    private UserService userService;
    @RequestMapping("findUser")
    public User getUser(Integer id) throws ExecutionException, InterruptedException {
        return userService.find(id).get();
    }
}
