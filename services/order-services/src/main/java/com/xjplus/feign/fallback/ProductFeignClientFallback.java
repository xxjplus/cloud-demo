package com.xjplus.feign.fallback;

import com.xjplus.bean.Product;
import com.xjplus.feign.ProductFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author xujing
 * @description
 * @date 2025/7/30
 **/
@Slf4j
@Component
public class ProductFeignClientFallback implements ProductFeignClient {


    @Override
    public Product getProduct(Long id) {
        log.info("调用商品服务失败，进入fallback方法 getProduct:{}", id);
        // Mock一个商品信息
        Product product = new Product();
        product.setId(id);
        product.setName("Fallback Product");
        product.setPrice(BigDecimal.valueOf(1.1d));
        product.setNum(4);
        return product;
    }
}
