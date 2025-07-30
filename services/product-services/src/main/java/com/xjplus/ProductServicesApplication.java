package com.xjplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xujing
 * @description
 * @date 2025/7/29
 **/
@EnableDiscoveryClient // 显示启用 服务发现 目前可以不需要 自动装配机制
@EnableFeignClients // 启用 Feign 客户端
@SpringBootApplication
public class ProductServicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServicesApplication.class, args);
    }
}
