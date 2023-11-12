package org.onlineshop.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ProductInBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private Product product;
    private long quantity;

    public ProductInBasket(Product product, long quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
