package com.maqoor.telegram_bot.telegram.service;

import com.maqoor.telegram_bot.entity.order.Order;
import com.maqoor.telegram_bot.telegram.entity.TelegramUser;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

public interface TelegramBotService {
     void sendText(Long chatId, String text);
     void notifyStatusReady(Order order, TelegramUser telegramUser);
     void notifyStatusCompleted(Order order, TelegramUser telegramUser);
     void executeBotCommand(Message message, User user, Long chatId);


}
