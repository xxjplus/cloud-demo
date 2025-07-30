package com.xjplus.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xujing
 * @description 商品
 * @date 2025/7/29
 **/
@Data
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer num;
}
