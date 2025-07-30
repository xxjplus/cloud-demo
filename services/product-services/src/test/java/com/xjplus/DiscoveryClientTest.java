package com.xjplus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.DiscoveryClient;

/**
 * @author xujing
 * @description
 * @date 2025/7/29
 **/
@SpringBootTest
public class DiscoveryClientTest {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Test
    void testDiscoveryClient() {
        discoveryClient.getServices().forEach(service -> {
            System.out.println("serviceId:" + service);
            discoveryClient.getInstances(service).forEach(serviceInstance -> {
                System.out.println(serviceInstance.getUri() + " " + serviceInstance.getServiceId());
            });
        });

    }
}
