package com.xjplus.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xujing
 * @description 订单
 * @date 2025/7/29
 **/
@Data
public class Order {
    private Long id;
    private String userName;
    private String address;
    private BigDecimal totalCost;
    private List<Product> products;
}
