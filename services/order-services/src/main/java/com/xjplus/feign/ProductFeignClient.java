package com.xjplus.feign;

import com.xjplus.bean.Product;
import com.xjplus.feign.fallback.ProductFeignClientFallback;
import com.xjplus.feign.fallback.factory.ProductFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xujing
 * @description ProductFeignClient
 * @date 2025/7/30
 * value 指定调用哪个服务
 * fallback 指定服务降级类(兜底处理类,配合sentinel使用)
 **/
@FeignClient(value = "product-service",
//        fallback = ProductFeignClientFallback.class
        fallbackFactory = ProductFeignClientFallbackFactory.class
)
public interface ProductFeignClient {

    /**
     * 根据id获取商品信息
     * @param id 商品id 从路径参数总获取
     * @return Product
     */
    @GetMapping("/product/getProduct/{productId}")
    Product getProduct(@PathVariable("productId") Long id);
}
