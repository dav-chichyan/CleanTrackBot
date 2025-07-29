package com.maqoor.telegram_bot.telegram.service.impl;

import com.maqoor.telegram_bot.cleancloud.client.CleanCloudAPIClient;
import com.maqoor.telegram_bot.entity.order.Order;
import com.maqoor.telegram_bot.exceptions.CleanCloudClientException;
import com.maqoor.telegram_bot.exceptions.OrderNotFoundException;
import com.maqoor.telegram_bot.service.OrderService;
import com.maqoor.telegram_bot.telegram.OrderHandler;
import com.maqoor.telegram_bot.telegram.entity.TelegramUser;
import com.maqoor.telegram_bot.telegram.enums.ChatState;
import com.maqoor.telegram_bot.telegram.service.TelegramBotService;
import com.maqoor.telegram_bot.telegram.service.TelegramUserService;
import com.maqoor.telegram_bot.util.Constants;
import com.maqoor.telegram_bot.util.DateUtils;
import com.maqoor.telegram_bot.validator.StateValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.maqoor.telegram_bot.util.Constants.NUMBER_OF_ATTEMPTS_LEFT;


@Service
@Slf4j
public class TelegramBotServiceImpl implements TelegramBotService {

    @Autowired
    private CleanCloudAPIClient cleanCloudApiClient;

    @Autowired
    @Lazy
    private TelegramLongPollingBot telegramLongPollingBot;

    @Autowired
    private OrderHandler orderHandler;

    @Autowired
    @Lazy
    private OrderService orderService;


    @Autowired
    private TelegramUserService telegramUserService;

    private final Map<Long, ChatState> userState = new HashMap<>();

    String regex = "[0-9]+";


    @Override
    public void sendText(Long chatId, String text) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text).build();
        sendMessage.setParseMode("Markdown");
        try {
            telegramLongPollingBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyStatusReady(Order order, TelegramUser telegramUser) throws OrderNotFoundException {
        sendText(telegramUser.getId(), orderHandler.updateNotificationBuilder(order));
    }

    @Override
    public void notifyStatusCompleted(Order order, TelegramUser telegramUser) throws OrderNotFoundException {
        sendText(telegramUser.getId(), orderHandler.completedNotificationBuilder(order));

    }

    @Override
    @Transactional
    public void executeBotCommand(Message message, User user, Long chatId) {
        if (message.isCommand()) {
            if (message.getText().equals("/start")) {
                userState.put(chatId, ChatState.AWAITING_ORDER_ID);
                sendText(chatId, Constants.PROVIDE_ORDER_ID);
                return;
            }
        } else if (!message.getText().trim().matches(regex) && !message.isCommand()) {
            sendText(chatId, Constants.ONLY_LETTER_MSG);
            log.error("Order Id should contain only numbers, provided order Id : {}", message.getText());
            return;
        }

        ChatState state = userState.get(chatId);
        if (state == ChatState.AWAITING_ORDER_ID) {
            TelegramUser telegramUser = telegramUserService.findById(chatId);
            if (telegramUser == null) {
                telegramUser = new TelegramUser(chatId);
                telegramUserService.registerUser(telegramUser); // Save new user if needed
            }
            StateValidator.setStateIfNeeded(telegramUser);
            int requestCounter = telegramUser.getRequestCount();

            if (StateValidator.validateUserState(telegramUser)) {
                try {
                    Order order = cleanCloudApiClient.getOrder(message.getText());
                    if (order == null) {
                        throw new OrderNotFoundException(message.getText());
                    }
                    if (order.getCleaningStatus().getNumber().equals("2")) {
                        log.info("Order was completed : {}", message.getText());
                        sendText(chatId, Constants.COMPLETED_ORDER);
                        requestCounter--;
                        if (requestCounter < 5) {
                            sendText(chatId, String.format(NUMBER_OF_ATTEMPTS_LEFT, requestCounter));
                        }
                        telegramUser.setRequestCount(requestCounter);
                        telegramUserService.registerUser(telegramUser);
                        return;
                    } else {
                        log.info("Order is in progress {}", message.getText());
                        sendText(chatId, orderHandler.responseBuilder(order));
                        requestCounter--;
                        if (requestCounter < 5) {
                            sendText(chatId, String.format(NUMBER_OF_ATTEMPTS_LEFT, requestCounter));
                        }
                        telegramUser.setRequestCount(requestCounter);
                        telegramUserService.registerUser(telegramUser);


                        if (order.getCleaningStatus().getNumber().equals("0")) {
                            orderService.create(order, telegramUser);// Links and saves user+order!
                            log.info("Order: {}", order);
                        }
                        userState.put(chatId, ChatState.FINISHED);
                    }
                } catch (OrderNotFoundException e) {
                    log.error("Order was not found : {}", message.getText());
                    sendText(chatId, Constants.INVALID_ID_MSG);
                } catch (CleanCloudClientException e) {
                    log.error("CleanCloud Integration error : {}", e.getMessage());
                    sendText(chatId, Constants.ERROR_PROCESSING_ORDER);
                }
                return;
            } else {
                log.info("User has reached his daily limit");
                Duration duration = DateUtils.calculateUnblock(telegramUser.getFirstAttemptAt());
                sendText(chatId, Constants.REACHED_ATTEMPTS_LIMIT + String.format(
                        "\n Please try again after %s hours and %s minutes ⏳ ",
                        duration.toHours(), duration.toMinutes() % 60));
                telegramUserService.updateUser(telegramUser);
                return;
            }
        }

        if (state == ChatState.FINISHED) {
            sendText(chatId, Constants.TYPE_START_ANOTHER);
            userState.remove(chatId); // Clean up state
        }
    }
}
