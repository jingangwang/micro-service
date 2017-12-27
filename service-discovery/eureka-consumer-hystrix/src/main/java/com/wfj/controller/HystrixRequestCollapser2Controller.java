package com.wfj.controller;

import com.wfj.pojo.User;
import com.wfj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 测试Scope.REQUEST的请求合并
 * @author wjg
 * @date 2017/12/21 15:17
 */
@RequestMapping("user")
@RestController
public class HystrixRequestCollapser2Controller {
    @Autowired
    private UserService userService;

    @RequestMapping("findUser2")
    public List<User> getUser() throws ExecutionException, InterruptedException {
        Future<User> user1 = userService.find(1989);
        Future<User> user2= userService.find(1990);
        List<User> users = new ArrayList<>();
        users.add(user1.get());
        users.add(user2.get());
        return users;
    }
}
