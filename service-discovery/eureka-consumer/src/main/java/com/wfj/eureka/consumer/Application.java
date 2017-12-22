package com.wfj.eureka.consumer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author wjg
 * @date 2017/12/7 16:14
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Application {
    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

}
