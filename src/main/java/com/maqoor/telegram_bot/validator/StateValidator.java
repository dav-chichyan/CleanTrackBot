package com.maqoor.telegram_bot.validator;

import com.maqoor.telegram_bot.telegram.entity.TelegramUser;
import com.maqoor.telegram_bot.telegram.enums.UserState;
import com.maqoor.telegram_bot.util.TelegramUserNotFound;

import java.time.LocalDateTime;


public class StateValidator {

    private static final int DEFAULT_REQUEST_COUNT = 10;

    public static int getDefaultRequestCount() {
        return DEFAULT_REQUEST_COUNT;
    }


    public static void setStateIfNeeded(TelegramUser telegramUser) {
        if (telegramUser == null) {
            throw new TelegramUserNotFound("User not found");
        }
        if (RecordSetter.attemptTimeIsEmpty(telegramUser) || RecordSetter.attemptPastTheTime(telegramUser)) {
            telegramUser.setRequestCount(DEFAULT_REQUEST_COUNT);
            telegramUser.setUserState(UserState.ACTIVE);
        } else if (telegramUser.getRequestCount() <= 0) {
            telegramUser.setUserState(UserState.BLOCKED);
        }
    }

    public static boolean validateUserState(TelegramUser telegramUser) {
        if (telegramUser == null) {
            throw new TelegramUserNotFound("User not found");
        }

        if (telegramUser.getUserState().equals(UserState.ACTIVE)) {
            telegramUser.setFirstAttemptAt(LocalDateTime.now());
            return true;
        }
        return false;
    }

}
