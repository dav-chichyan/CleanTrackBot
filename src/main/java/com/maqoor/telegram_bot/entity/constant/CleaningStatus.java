package com.maqoor.telegram_bot.entity.constant;


import lombok.Getter;

@Getter
public enum CleaningStatus {
    CLEANING("0","Cleaning"),
    CLEANED_AND_READY("1","Ready"),
    COMPLETED("2","Completed"),
    AWAITING_PICKUP("4","Awaiting Pick up"),
    DETAILING("5","Detailing"),
    UNKNOWN("unknown","Unknown");

    private final String number;
    private final String description;

    CleaningStatus(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public static CleaningStatus fromNumber(String number) {
        for (CleaningStatus cleaningStatus : CleaningStatus.values()) {
            if (cleaningStatus.number.equals(number)) {
                return cleaningStatus;
            }
        }
        return CleaningStatus.UNKNOWN;
    }

}


