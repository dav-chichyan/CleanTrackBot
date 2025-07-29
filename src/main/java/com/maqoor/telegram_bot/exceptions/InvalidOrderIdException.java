package com.maqoor.telegram_bot.exceptions;

public class InvalidOrderIdException extends RuntimeException {

    public InvalidOrderIdException(String message) {
        super(message);
    }
}
