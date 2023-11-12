package org.onlineshop.repository;

import org.onlineshop.models.PaymentHistory;
import org.onlineshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    List<PaymentHistory> findByUser(User user);
}
