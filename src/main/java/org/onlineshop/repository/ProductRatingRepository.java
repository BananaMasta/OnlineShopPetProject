package org.onlineshop.repository;

import org.onlineshop.models.Product;
import org.onlineshop.models.ProductRating;
import org.onlineshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {
    ProductRating findByUser(User user);
    List<ProductRating> findByProduct(Product product);
}
