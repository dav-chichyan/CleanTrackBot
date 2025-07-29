package com.maqoor.telegram_bot.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;


public class OrderNotFoundException  extends RuntimeException {
    public OrderNotFoundException(String message) {
        super("Order with Id - " + message + " is not found");
    }
}
