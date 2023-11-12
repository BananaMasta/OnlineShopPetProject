package org.onlineshop.repository;

import org.onlineshop.models.Product;
import org.onlineshop.models.User;
import org.onlineshop.models.UserCommentary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaryRepository extends JpaRepository<UserCommentary, Long> {
    List<UserCommentary> findByProduct(Product product);

    List<UserCommentary> findByUser(User user);
}
