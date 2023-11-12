package org.onlineshop.util;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DtoProduct {
    private long id;
    private String fileName;
    private String productName;
    private BigDecimal productPrice;
    private String description;
    private int productQuantity;
    private BigDecimal sum;

}
