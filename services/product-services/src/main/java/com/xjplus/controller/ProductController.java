package com.xjplus.controller;

import com.xjplus.bean.Product;
import com.xjplus.cons.RequestConstant;
import com.xjplus.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xujing
 * @description
 * @date 2025/7/29
 **/
@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getProduct/{productId}")
    public Product getProduct(@PathVariable("productId") Long id, HttpServletRequest request) throws InterruptedException {
        log.info("enter getProduct by id:{}, request.header.x-token: {}", id, request.getHeader(RequestConstant.REQUEST_HEADER_X_TOKEN));
//        Thread.sleep(5000);
        return productService.getProductById(id);
    }

}
