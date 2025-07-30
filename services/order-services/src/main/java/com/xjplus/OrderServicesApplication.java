package com.xjplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xujing
 * @description Application
 * @date 2025/7/29
 **/
@EnableFeignClients
@SpringBootApplication
public class OrderServicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServicesApplication.class, args);
    }
}
