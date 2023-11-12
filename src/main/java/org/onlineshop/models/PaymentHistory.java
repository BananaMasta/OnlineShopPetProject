package org.onlineshop.models;

import lombok.Data;
import org.onlineshop.models.ProductInBasket;
import org.onlineshop.models.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToMany
    private List<ProductInBasket> product;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public List<ProductInBasket> getProduct() {
        return product;
    }

    public void setProduct(List<ProductInBasket> product) {
        this.product = product;
    }
}
