package com.maqoor.telegram_bot.telegram;


import com.maqoor.telegram_bot.security.MyDecryptor;
import com.maqoor.telegram_bot.telegram.service.TelegramBotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private TelegramBotService telegramBotService;

    @Value("${telegram.token}")
    private String token;

    @Value("${telegram.bot.name}")
    private String botName;

    private final String decKey = "carleone";

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return MyDecryptor.decrypt(this.token, decKey);
    }


    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        var user = message.getFrom();
        var chatId = user.getId(); // chat_id
        telegramBotService.executeBotCommand(message ,user,chatId);
    }


}


