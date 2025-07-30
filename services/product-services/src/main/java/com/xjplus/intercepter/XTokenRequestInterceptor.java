package com.xjplus.intercepter;

import com.xjplus.cons.RequestConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.UUID;

/**
 * @author xujing
 * @description 增加XToken请求拦截器
 * @date 2025/7/30
 **/
@Slf4j
//@Component  // 如果放到Spring中 则会对所有的 有效拦截
public class XTokenRequestInterceptor implements RequestInterceptor {


    /**
     *
     * @param template 请求模板(详细信息)
     */
    @Override
    public void apply(RequestTemplate template) {
        template.headers().put(RequestConstant.REQUEST_HEADER_X_TOKEN, Collections.singleton(UUID.randomUUID().toString()));
        log.info("XTokenRequestInterceptor apply template:{}", template);
    }
}
