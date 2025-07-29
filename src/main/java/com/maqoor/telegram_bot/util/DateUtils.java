package com.maqoor.telegram_bot.util;



import java.time.Duration;
import java.time.LocalDateTime;

public class DateUtils {

    public static Duration calculateUnblock(LocalDateTime firstAttempt) {
        LocalDateTime unlockTime = firstAttempt.plusHours(24);
        return Duration.between(LocalDateTime.now(),unlockTime );
    }

    public static boolean isEmpty(LocalDateTime firstAttempt){
        return firstAttempt == null;
    }

}
