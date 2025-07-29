package com.maqoor.telegram_bot.telegram.service;

import com.maqoor.telegram_bot.telegram.entity.TelegramUser;

public interface TelegramUserService {
    void registerUser(TelegramUser user);
    TelegramUser findById(Long id);
    void updateUser(TelegramUser user);
    public TelegramUser getUserWithOrders(Long id);

    // other methods like save, update etc.
}


