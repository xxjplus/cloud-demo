package com.xjplus.controller;

import com.xjplus.bean.Order;
import com.xjplus.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xujing
 * @description
 * @date 2025/7/29
 **/
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/create")
    public Order createOrder(@RequestParam("orderId") Long orderId, @RequestParam("productId") Long productId) {
        log.info("create order, orderId: {}, productId: {}", orderId, productId);
        return orderService.createOrder(orderId, productId);
    }

    @GetMapping("/query/{orderId}")
    public Order queryOrder(@PathVariable("orderId") String orderId) {

        return null;
    }


}
