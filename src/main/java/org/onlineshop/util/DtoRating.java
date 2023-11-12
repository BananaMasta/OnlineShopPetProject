package org.onlineshop.util;

import lombok.Getter;
import lombok.Setter;
import org.onlineshop.models.Product;

@Getter
@Setter
public class DtoRating {
    private long id;
    private double finalScore;
    private Product product;

}
