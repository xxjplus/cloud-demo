package com.xjplus.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.xjplus.bean.Order;
import com.xjplus.bean.Product;
import com.xjplus.feign.ProductFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xujing
 * @description
 * @date 2025/7/29
 **/
@Service
@RefreshScope
@Slf4j
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Value("${product-service.name}")
    private String productServiceName;

    @Value("${user.address}")
    private String address;


    // 创建订单
    @SentinelResource(value = "createOrder")
    public Order createOrder(Long orderId, Long productId) {

        Order order = new Order();
        order.setAddress(address);
        order.setId(orderId);

        // 根据productId调用商品服务获取商品信息
        log.info("nacos 配置信息:" + productServiceName);

//        Product product = getProductByRestTemplate(productId);
        // 通过 FeignClient调用
        Product product = getProductByOpenFeignClient(productId);
        order.setProducts(List.of(product));
        order.setTotalCost(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserName("张三");

        return order;
    }

    /**
     * 通过 OpenFeignClient 调用商品服务获取商品信息
     */
    private Product getProductByOpenFeignClient(Long productId) {
        log.info("enter getProductByOpenFeignClient productId:{}", productId);
        return productFeignClient.getProduct(productId);
    }

    /**
     * 通过RestTemplate调用商品服务获取商品信息
     * RestTemplateConfig 中已经将 RestTemplate 注入到Spring容器中,且配合负载均衡注解 @LoadBalanced
     * @param productId 商品信息
     * @return Product
     */
    private Product getProductByRestTemplate(Long productId) {
        return restTemplate.getForObject("http://" + productServiceName + "/product/getProduct/" + productId, Product.class);
    }
}
