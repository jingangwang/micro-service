package com.wfj.controller;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.wfj.pojo.User;
import com.wfj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @author wjg
 * @date 2017/12/21 15:17
 */
@RequestMapping("user")
@RestController
public class FeignCollapserController {
    @Autowired
    private UserService userService;

    /**
     * HystrixCollapser 的scope默认是Request，请求必须包裹在
     *  HystrixRequestContext context = HystrixRequestContext.initializeContext();
     *  .....
     *  context.close();
     *  可以使用filter来实现
     * @param id
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("findUser")
    public User getUser(Integer id) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        User user = userService.find(id).get();
        User user2 = userService.find(1989).get();
        context.close();
        return user;
    }
}
