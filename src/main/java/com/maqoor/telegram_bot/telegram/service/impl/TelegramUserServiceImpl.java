package com.maqoor.telegram_bot.telegram.service.impl;

import com.maqoor.telegram_bot.telegram.repo.TelegramUserRepository;
import com.maqoor.telegram_bot.telegram.entity.TelegramUser;
import com.maqoor.telegram_bot.telegram.service.TelegramUserService;
import com.maqoor.telegram_bot.util.TelegramUserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TelegramUserServiceImpl implements TelegramUserService {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Override
    @Transactional
    public void registerUser(TelegramUser user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null");
        }
        telegramUserRepository.save(user);
        log.info("Registered user: {}", user);
    }

    @Override
    public TelegramUser findById(Long id) {
        if (id == null) {
            throw new TelegramUserNotFound("User id cannot be null");
        }
            return telegramUserRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void updateUser(TelegramUser user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null");
        }
        telegramUserRepository.saveAndFlush(user);
        log.info("Updated user: {}", user);
    }


    @Override
    @Transactional(readOnly = true)
    public TelegramUser getUserWithOrders(Long id) {
        return telegramUserRepository.findByIdWithOrders(id)
                .orElseThrow(() -> new TelegramUserNotFound("User not found"));
    }

}



