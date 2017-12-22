package com.wfj.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wjg
 * @date 2017/12/8 15:39
 */
@Configuration
public class FeignConfiguration {
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
