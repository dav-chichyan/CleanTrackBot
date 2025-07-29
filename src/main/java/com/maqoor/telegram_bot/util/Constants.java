package com.maqoor.telegram_bot.util;

import com.maqoor.telegram_bot.validator.StateValidator;

public class Constants {

    public static final String ONLY_LETTER_MSG = "🚫 *Order ID should contain only numbers.*\n";
    public static final String INVALID_ID_MSG = "❌ *Invalid Order ID.*\nPlease double-check and try again.";

    public static final String PROVIDE_ORDER_ID = "\uD83D\uDCE8 Please provide your Order ID:\n" +
            "\n" + "You can find it in your receipt or inside order confirmation email.";

    public static final String ERROR_PROCESSING_ORDER = "⚠️ Error processing your order. Please try again later.";
    public static final String TYPE_START_ANOTHER = "Thanks for using our services \uD83D\uDC9A \n" +
            "Type /start to track another order.";
    public static final String TYPE_START_FIRST_TIME = "Please type /start before sending any message.";
    public static final String COMPLETED_ORDER = "\uD83C\uDF89 The provided order has been completed.";
    public static final String REACHED_ATTEMPTS_LIMIT = String.format("*You've reached your daily limit of %s attempts.*", StateValidator.getDefaultRequestCount());
    public static final String NUMBER_OF_ATTEMPTS_LEFT = "\uD83E\uDEF6\uD83C\uDFFC *You have %s attempt(s) left to check.*";



    public static final String EVENT_SOURCE_CUSTOMER_CREATED = "customer.created";
    public static final String EVENT_SOURCE_STATUS_CHANGED = "order.status_changed";
    public static final String MERGE_DISABLE_ID = "merge_disable_id";
    public static final String MERGE_MASTER_ID = "merge_master_id";
    public static final String EVENT_SOURCE_CUSTOMER = "customer";
    public static final String LOGIN_EMAIL = "login_email";
    public static final String LOGIN_PASSWORD = "login_password";
    public static final String STATION = "station";
    public static final String ORDER_ID = "orderID";
    public static final String API_CC_TOKEN = "api_token";
    public static final String SEARCH_BY_PHONE = "searchByPhone";
    public static final String CUSTOMER_API_TELEPHONE = "customerTel";
    public static final String DEACTIVATED_CUSTOMERS = "excludeDeactivated";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String CUSTOMER_EMAIL = "customer_email";
    public static final String CUSTOMER_TEL = "customer_tel";
    public static final String CUSTOMER_SOURCE = "customer";
    public static final String POS_SOURCE = "pos";
    public static final String CHOOSE_STORE = "choose_store_select";




    private Constants() {}
}
