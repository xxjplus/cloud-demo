package com.xjplus.config;

import feign.Logger;
import feign.RetryableException;
import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author xujing
 * @description
 * @date 2025/7/29
 **/
@Configuration
public class OrderConfig {


    /**
     * 负载均衡的 RestTemplate
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


//    @Bean
//    Logger.Level feignLoggerLevel() {
//        return Logger.Level.FULL;
//    }

    // 重试机制
//    @Bean
//    public Retryer retryer() {
//        return new Retryer.Default(100, SECONDS.toMillis(1), 3);
//    }
}
