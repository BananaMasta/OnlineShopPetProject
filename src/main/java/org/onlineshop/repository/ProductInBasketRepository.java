package org.onlineshop.repository;

import org.onlineshop.models.Product;
import org.onlineshop.models.ProductInBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInBasketRepository extends JpaRepository<ProductInBasket, Long> {
    ProductInBasket findByProduct(Product product);
}
