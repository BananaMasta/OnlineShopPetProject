package org.onlineshop.util;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DtoPayment {
    private long id;
    private String fileName;
    private String productName;
    private int productQuantity;
    private BigDecimal productPrice;
    private BigDecimal sum;
}
