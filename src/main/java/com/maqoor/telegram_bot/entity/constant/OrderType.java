package com.maqoor.telegram_bot.entity.constant;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum OrderType {

    LOCKER("\uD83D\uDEAA Locker Order"),
    DELIVERY("🚚 Delivery Order"),
    STORE("🏬 In-Store Order");


    private final String displayName;

    OrderType(String displayName) {
        this.displayName = displayName;
    }

    public static OrderType getOrderType(String locker, String delivery) {
        if (locker.equals("1")) {
            return OrderType.LOCKER;
        }
        if (delivery.equals("1")) {
            return OrderType.DELIVERY;
        }
        return OrderType.STORE;
    }
}

