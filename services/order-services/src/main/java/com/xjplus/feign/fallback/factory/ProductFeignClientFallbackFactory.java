package com.xjplus.feign.fallback.factory;

import com.xjplus.feign.ProductFeignClient;
import com.xjplus.feign.fallback.ProductFeignClientFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author xujing
 * @description FallbackFactory 可以捕获异常
 * @date 2025/7/30
 **/
@Slf4j
@Component
public class ProductFeignClientFallbackFactory implements FallbackFactory<ProductFeignClient> {

    @Override
    public ProductFeignClient create(Throwable cause) {
        log.error("调用商品服务失败", cause);
        // 返回 fallback 对应对象
        return new ProductFeignClientFallback();
    }
}
