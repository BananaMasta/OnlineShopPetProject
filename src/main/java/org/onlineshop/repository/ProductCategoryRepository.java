package org.onlineshop.repository;

import org.onlineshop.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    boolean existsByCategoryName(String categoryName);

    ProductCategory findByCategoryName(String categoryName);
}
