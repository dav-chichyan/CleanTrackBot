package com.maqoor.telegram_bot.telegram.repo;

import com.maqoor.telegram_bot.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
     @Query("SELECT o FROM Order o LEFT JOIN FETCH o.users WHERE o.id = :id")
     Optional<Order> findByIdWithUsers(@Param("id") String id);
}