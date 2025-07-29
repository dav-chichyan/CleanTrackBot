package com.maqoor.telegram_bot.telegram.repo;

import com.maqoor.telegram_bot.telegram.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {

    @Query("select u from TelegramUser u left join fetch u.orders where u.id = :id ")
    Optional<TelegramUser> findByIdWithOrders(@Param("id") Long id);
}
