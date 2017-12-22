package com.wfj.service;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wfj.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author wjg
 * @date 2017/12/21 15:08
 */
@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private RestTemplate restTemplate;


    /**
     * maxRequestsInBatch								该属性设置批量处理的最大请求数量，默认值为Integer.MAX_VALUE
     * timerDelayInMilliseconds							该属性设置多长时间之内算一次批处理，默认为10ms
     * @param id
     * @return
     */
    @HystrixCollapser(batchMethod = "findAllUser",scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds",value = "5000" ),
            @HystrixProperty(name = "maxRequestsInBatch",value = "5" )
    })
    public Future<User> find(Integer id){
        return null;
    }

    @HystrixCommand
    public List<User> findAllUser(List<Integer> ids){
        logger.info("发起批量请求："+StringUtils.join(ids,","));
        User[] list  = restTemplate.getForObject("http://EUREKA-PROVIDER/user/getAllUser.json?ids={0}", User[].class, StringUtils.join(ids, ","));
        List<User> users = Arrays.asList(list);
        logger.info("批量请求返回结果："+ JSON.toJSONString(users));
        return users;
    }
}
