package com.wfj.controller;

import com.wfj.pojo.User;
import com.wfj.service.UserCacheService;
import com.wfj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 测试Request Cache
 * @author wjg
 * @date 2017/12/21 15:17
 */
@RequestMapping("user")
@RestController
public class HystrixRequestCacheController {
    @Autowired
    private UserCacheService userCacheService;
    @RequestMapping("findUser3")
    public User getUser(Integer id) throws ExecutionException, InterruptedException {
        userCacheService.findUserById(id);
        userCacheService.findUserById(id);
        return userCacheService.findUserById(id);
    }
}
