package com.wfj.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.wfj.feign.UserFeignClient;
import com.wfj.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author wjg
 * @date 2017/12/21 15:08
 */
@Service
public class UserCacheService {
    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * @HystrixCommand 的requestCache.enabled 可控制是否支持缓存
     * 只有加了@CacheResult才能缓存，即使requestCache.enabled=true
     * @CacheKey 指定走缓存要判断的字段
     * @param id    用户id
     * @return  指定的用户
     */
    @CacheResult
    @HystrixCommand(commandKey = "findUserById",commandProperties = {
            @HystrixProperty(name="requestCache.enabled",value = "true")
    })
    public User findUserById(Integer id){
        return  userFeignClient.findUserById(id);
    }

    /**
     * 通过@CacheKey 指定缓存key
     * @param id    用户id
     * @param name  用户姓名
     * @return  指定的用户
     */
    @CacheResult
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name="requestCache.enabled",value = "true")
    })
    public User findUserByIdAndName(@CacheKey Integer id,String name){
        return  userFeignClient.findUserById(id);
    }

    /**
     * 通过@CacheRemove 注解指定当调用findUserById时将此方法的缓存删除
     * @param id    用户id
     * @param name  用户姓名
     * @return  指定的用户
     */
    @CacheResult
    @CacheRemove(commandKey = "findUserById")
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name="requestCache.enabled",value = "true")
    })
    public User findUserByIdAndName2(@CacheKey Integer id,String name){
        return  userFeignClient.findUserById(id);
    }
}
