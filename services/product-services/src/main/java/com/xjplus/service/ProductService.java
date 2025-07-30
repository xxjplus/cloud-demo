package com.xjplus.service;

import com.xjplus.bean.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author xujing
 * @description
 * @date 2025/7/29
 **/
@Service
public class ProductService {

    @Value("${product.name}")
    private String name;

    @Value("${product.price.top}")
    private BigDecimal priceTop;

    @Value("${product.num.top}")
    private Integer numTop;


    public Product getProductById(Long id){
        Product product = new Product();
        product.setId(id);
        product.setName(name + "-" + id);
        product.setPrice(priceTop);
        product.setNum(numTop);

        return product;
    }
}
