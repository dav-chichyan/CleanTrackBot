package com.maqoor.telegram_bot.util;

public class TotalCostUtil {
    public static String getFinalTotal(String total){
        String cleanTotal = StringUtils.removeZeroes(total);
        return StringUtils.tipExtract(cleanTotal);
    }
}
