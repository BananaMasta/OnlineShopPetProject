package org.onlineshop.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;
    private String name;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;
    @ManyToOne
    @JoinColumn(name = "manufactory_id")
    private Company companyProduct;
    @OneToOne
    @JoinColumn(name = "productRating_id")
    private ProductRating productRating;
    private String fileName;

    public Product() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Product(String name, BigDecimal price, ProductCategory productCategory, Company companyProduct) {
        this.name = name;
        this.price = price;
        this.productCategory = productCategory;
        this.companyProduct = companyProduct;
    }

    @Override
    public String toString() {
        return getName() + " " + getCompanyProduct() + " " + getProductCategory() + " " + getPrice() + " " + getProductRating();
    }
}
