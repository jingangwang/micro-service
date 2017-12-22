package com.wfj.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wfj.feign.UserFeignClient;
import com.wfj.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author wjg
 * @date 2017/12/21 15:08
 */
@Service
public class UserService {
    @Autowired
    private UserFeignClient userFeignClient;



    /**
     * maxRequestsInBatch								该属性设置批量处理的最大请求数量，默认值为Integer.MAX_VALUE
     * timerDelayInMilliseconds							该属性设置多长时间之内算一次批处理，默认为10ms
     * @param id
     * @return
     */
    @HystrixCollapser(collapserKey = "findCollapserKey",scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,batchMethod = "findAllUser",collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds",value = "5000" ),
            @HystrixProperty(name = "maxRequestsInBatch",value = "5" )
    })
    public Future<User> find(Integer id){
        return null;
    }

    @HystrixCommand(commandKey = "findAllUser")
    public List<User> findAllUser(List<Integer> ids){
        return userFeignClient.findAllUser(StringUtils.join(ids,","));
    }
}
