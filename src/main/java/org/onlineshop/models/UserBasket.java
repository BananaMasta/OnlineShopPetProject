package org.onlineshop.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class UserBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany
    private List<ProductInBasket> product = new ArrayList<>();
    private BigDecimal sum;

    public List<ProductInBasket> getProduct() {
        return product;
    }
}
