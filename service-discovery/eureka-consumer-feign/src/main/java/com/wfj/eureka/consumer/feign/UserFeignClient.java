package com.wfj.eureka.consumer.feign;

import com.wfj.eureka.consumer.config.FeignConfiguration;
import com.wfj.eureka.consumer.pojo.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wjg
 * @date 2017/12/8 14:11
 */
@FeignClient(name = "eureka-provider",configuration = FeignConfiguration.class)
public interface UserFeignClient {
    /**
     * 根据id查找用户
     * @param id    用户id
     * @return      User
     */
    @RequestMapping(value = "user/getUser.json",method = RequestMethod.GET)
    User findUserById(@RequestParam("id") Integer id);
}
