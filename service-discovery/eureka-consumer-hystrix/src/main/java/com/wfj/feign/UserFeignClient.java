package com.wfj.feign;


import com.wfj.config.FeignConfiguration;
import com.wfj.pojo.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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


    /**
     *  超找用户列表
     * @param ids   id列表
     * @return  用户的集合
     */
    @RequestMapping(value = "user/getAllUser.json",method = RequestMethod.GET)
    List<User> findAllUser(@RequestParam("ids") String ids);
}
