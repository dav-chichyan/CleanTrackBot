package com.maqoor.telegram_bot.service;

import com.maqoor.telegram_bot.entity.order.Order;
import com.maqoor.telegram_bot.entity.constant.CleaningStatus;
import com.maqoor.telegram_bot.exceptions.OrderNotFoundException;
import com.maqoor.telegram_bot.telegram.entity.TelegramUser;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    void create(Order order) throws OrderNotFoundException;
    void updateStatus(String orderId, CleaningStatus cleaningStatus) throws OrderNotFoundException;
    void delete(Order order);
    void create(Order order, TelegramUser telegramUser);
}
