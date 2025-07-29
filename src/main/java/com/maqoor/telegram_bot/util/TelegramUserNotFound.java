package com.maqoor.telegram_bot.util;

public class TelegramUserNotFound extends RuntimeException {
    public TelegramUserNotFound(String message) {
        super(message);
    }

}
