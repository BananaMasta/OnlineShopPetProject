package org.onlineshop.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product companyProduct;
}
