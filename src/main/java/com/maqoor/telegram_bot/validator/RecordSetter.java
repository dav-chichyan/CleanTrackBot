package com.maqoor.telegram_bot.validator;

import com.maqoor.telegram_bot.telegram.entity.TelegramUser;
import com.maqoor.telegram_bot.util.DateUtils;




public class RecordSetter {

    public static boolean attemptPastTheTime(TelegramUser telegramUser) {
        return DateUtils.calculateUnblock(telegramUser.getFirstAttemptAt()).toHours() <= 0
                && (DateUtils.calculateUnblock(telegramUser.getFirstAttemptAt()).toMinutes() % 60) <= 0;
    }

    public static boolean attemptTimeIsEmpty(TelegramUser telegramUser) {
       return DateUtils.isEmpty(telegramUser.getFirstAttemptAt());
    }
}
