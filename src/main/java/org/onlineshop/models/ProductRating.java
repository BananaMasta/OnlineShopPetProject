package org.onlineshop.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ProductRating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_name_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private byte rating;

}
