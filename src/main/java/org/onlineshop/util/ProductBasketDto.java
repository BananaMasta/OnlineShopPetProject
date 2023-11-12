package org.onlineshop.util;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductBasketDto {
    private long id;
    private String title;
    private String description;
    private int quantity;
    private BigDecimal price;
    private BigDecimal sum;

}
