package com.wfj.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wfj.feign.UserFeignClient;
import com.wfj.pojo.User;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author wjg
 * @date 2017/12/8 14:14
 */
@RestController
@RequestMapping("user")
public class HystrixCircuitBreakerController {
    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 测试 execution.isolation.thread.timeoutInMilliseconds 命令超时
     * @param id    用户id
     * @return      返回对应id的用户对象
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("getUser")
    @HystrixCommand(commandKey = "getUserCommond",threadPoolKey = "getUserThreadPool",fallbackMethod = "getUserFallBack",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public User getUser(Integer id) throws ExecutionException, InterruptedException {
        Thread.sleep(6000);
        return userFeignClient.findUserById(id);
    }

    /**
     * 测试 execution.timeout.enabled 是否开启超时触发
     * @param id    用户id
     * @return      返回对应id的用户对象
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("getUser1")
    @HystrixCommand(commandKey = "getUserCommond1",threadPoolKey = "getUserThreadPool1",fallbackMethod = "getUserFallBack",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "5000"),
            @HystrixProperty(name="execution.timeout.enabled",value = "false")
    })
    public User getUser1(Integer id) throws ExecutionException, InterruptedException {
        Thread.sleep(6000);
        return userFeignClient.findUserById(id);
    }

    /**
     * 测试 execution.isolation.thread.interruptOnTimeout
     * 使用线程隔离时，当命令超时时是否对命令所在线程执行中断操作，默认为true，
     * 只会中断线程处于阻塞状态（如sleep，wait，join等状态），正常运行的线程都不会中断
     * @param id    用户id
     * @return      返回对应id的用户对象
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("getUser2")
    @HystrixCommand(commandKey = "getUserCommond2",threadPoolKey = "getUserThreadPool2",fallbackMethod = "getUserFallBack",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "5000"),
            @HystrixProperty(name="execution.isolation.thread.interruptOnTimeout",value = "true")
    })
    public User getUser2(Integer id) throws ExecutionException, InterruptedException {
        Thread.sleep(6000);
        System.out.println("休眠结束");
        return userFeignClient.findUserById(id);
    }

    /**
     * 测试 fallback.enabled
     * 是否开启fallback降级策略，默认为true，如果设为false，会抛出500的超时错误
     * @param id    用户id
     * @return      返回对应id的用户对象
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("getUser3")
    @HystrixCommand(commandKey = "getUserCommond3",threadPoolKey = "getUserThreadPool3",fallbackMethod = "getUserFallBack",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "5000"),
            @HystrixProperty(name="fallback.enabled",value = "false")
    })
    public User getUser3(Integer id) throws ExecutionException, InterruptedException {
        Thread.sleep(6000);
        return userFeignClient.findUserById(id);
    }

    /**
     * 测试 circuitBreaker.requestVolumeThreshold
     * 一个滚动窗口内触发熔断的最少请求失败数，默认是20个。
     * 滚动窗口时间取自（metrics.rollingStats.timeInMilliseconds），默认10s内至少请求失败20次，熔断才发挥作用
     * @param id    用户id
     * @return      返回对应id的用户对象
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("getUser4")
    @HystrixCommand(commandKey = "getUserCommond4",threadPoolKey = "getUserThreadPool4",fallbackMethod = "getUserFallBack",commandProperties = {
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name="circuitBreaker.enabled",value = "false")
    })
    public User getUser4(Integer id) throws ExecutionException, InterruptedException {
        return userFeignClient.findUserById(id);
    }

    /**
     * 测试 circuitBreaker.errorThresholdPercentage （目前测试不生效，原因未知）
     * 设置启动熔断的错误或者延迟的比例，默认是50%。在一个滚动窗口（默认10s）如果超过50%的请求发生错误或者延迟，则触发熔断器
     * @param id    用户id
     * @return      返回对应id的用户对象
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("getUser5")
    @HystrixCommand(commandKey = "getUserCommond5",threadPoolKey = "getUserThreadPool5",fallbackMethod = "getUserFallBack",commandProperties = {
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "50")
    })
    public User getUser5(Integer id) throws ExecutionException, InterruptedException {
        return userFeignClient.findUserById(id);
    }


    /**
     * 测试 coreSize
     * 设置线程池的大小，默认为10，也就是说最大并发为10
     * @param id    用户id
     * @return      返回对应id的用户对象
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("getUser6")
    @HystrixCommand(commandKey = "getUserCommond6",threadPoolKey = "getUserThreadPool6",fallbackMethod = "getUserFallBack",
            commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "5000")
            },
            threadPoolProperties = {
            @HystrixProperty(name="coreSize",value = "2")
    })
    public User getUser6(Integer id) throws ExecutionException, InterruptedException {

        return userFeignClient.findUserById(id);
    }

    /**
     * 测试 maxQueueSize
     * 该属性设置实现的最大队列大小，默认为-1，-1代表SynchronousQueue将被使用，否则将使用一个正值的LinkedBlockingQueue，
     * 这个属性只适用于初始化期间，并且不能更改或者调整，如果修改的话需要重启
     * @param id    用户id
     * @return      返回对应id的用户对象
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("getUser7")
    @HystrixCommand(commandKey = "getUserCommond7",threadPoolKey = "getUserThreadPool7",fallbackMethod = "getUserFallBack",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "100000"),
                    @HystrixProperty(name="execution.timeout.enabled",value = "false")
            },
            threadPoolProperties = {
                @HystrixProperty(name="coreSize",value = "1"),
                @HystrixProperty(name="maxQueueSize",value = "2")
            })
    public User getUser7(Integer id) throws ExecutionException, InterruptedException {
        Thread.sleep(10000);
        return userFeignClient.findUserById(id);
    }

    /**
     * 测试 queueSizeRejectionThreshold
     * 该属性设置队列大小拒绝阈值，即maxQueueSize还没有达到最大值，
     * 此值也能动态响应拒绝队列的大小，当maxQueueSize=-1时此值不起作用
     * @param id    用户id
     * @return      返回对应id的用户对象
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("getUser8")
    @HystrixCommand(commandKey = "getUserCommond8",threadPoolKey = "getUserThreadPool8",fallbackMethod = "getUserFallBack",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "100000"),
                    @HystrixProperty(name="execution.timeout.enabled",value = "false")
            },
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize",value = "1"),
                    @HystrixProperty(name="maxQueueSize",value = "2"),
                    @HystrixProperty(name="queueSizeRejectionThreshold",value = "1")
            })
    public User getUser8(Integer id) throws ExecutionException, InterruptedException {
        Thread.sleep(10000);
        return userFeignClient.findUserById(id);
    }

    /**
     * 测试 maximumSize
     * 线程池打最大值，默认为10，只有allowMaximumSizeToDivergeFromCoreSize为true时才生效
     * @param id    用户id
     * @return      返回对应id的用户对象
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("getUser9")
    @HystrixCommand(commandKey = "getUserCommond9",threadPoolKey = "getUserThreadPool9",fallbackMethod = "getUserFallBack",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "100000"),
                    @HystrixProperty(name="execution.timeout.enabled",value = "false")
            },
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize",value = "1"),
                    @HystrixProperty(name="allowMaximumSizeToDivergeFromCoreSize",value = "true"),
                    @HystrixProperty(name="maximumSize",value = "4")
            })
    public User getUser9(Integer id) throws ExecutionException, InterruptedException {
        System.out.println("当前线程ID为："+ Thread.currentThread().getId());
        Thread.sleep(10000);
        return userFeignClient.findUserById(id);
    }

    public User getUserFallBack(Throwable throwable,Integer id){
        System.out.println("服务降级的原因是："+throwable.getMessage());
        return new User(-1,"fallback",-1);
    }


    public class Task implements Callable<String>{

        @Override
        public String call() throws Exception {
            Thread.sleep(10000);
            System.out.println("结束休眠");
            return "something";
        }
    }
}
