package com.maqoor.telegram_bot.exceptions;

public class OrderParsingError extends RuntimeException {
    public OrderParsingError(String message) {
        super(message);
    }
}
