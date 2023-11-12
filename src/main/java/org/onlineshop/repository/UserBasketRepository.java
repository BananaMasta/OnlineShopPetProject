package org.onlineshop.repository;

import org.onlineshop.models.ProductInBasket;
import org.onlineshop.models.User;
import org.onlineshop.models.UserBasket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBasketRepository extends JpaRepository<UserBasket, Long> {
    UserBasket findByUser(User user);

    boolean existsByProductContaining(ProductInBasket productInBasket);
}
