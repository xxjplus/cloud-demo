package com.xjplus.intercepter;

import com.xjplus.cons.RequestConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

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

        Map<String, Collection<String>> headersMap = template.headers() == null ? template.headers() : new HashMap<String, Collection<String>>();
        headersMap.put(RequestConstant.REQUEST_HEADER_X_TOKEN, Collections.singleton(UUID.randomUUID().toString()));
        template.headers(headersMap);
        // 直接使用下面的 不处理会出现空指针问题
//        template.headers().put(RequestConstant.REQUEST_HEADER_X_TOKEN, Collections.singleton(UUID.randomUUID().toString()));
        log.info("XTokenRequestInterceptor apply template:{}", template);
    }
}
