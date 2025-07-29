package com.maqoor.telegram_bot.service.impl;

import com.maqoor.telegram_bot.entity.order.Order;
import com.maqoor.telegram_bot.entity.constant.CleaningStatus;
import com.maqoor.telegram_bot.exceptions.OrderNotFoundException;
import com.maqoor.telegram_bot.telegram.entity.TelegramUser;
import com.maqoor.telegram_bot.telegram.repo.OrderRepository;
import com.maqoor.telegram_bot.service.OrderService;
import com.maqoor.telegram_bot.telegram.service.TelegramUserService;
import com.maqoor.telegram_bot.telegram.service.impl.TelegramBotServiceImpl;
import com.maqoor.telegram_bot.util.TelegramUserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TelegramUserService telegramUserService;

    @Autowired
    private TelegramBotServiceImpl telegramBotServiceImpl;


    @Override
    @Transactional
    public void create(Order order) throws OrderNotFoundException {
        if (order == null) {
            throw new OrderNotFoundException("Order cannot be null");
        }
        log.info("Creating order {}",order);
        orderRepository.save(order);
    }



    @Override
    @Transactional
    public void updateStatus(String orderId, CleaningStatus cleaningStatus) throws OrderNotFoundException {
        Order order = orderRepository
                .findByIdWithUsers(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id = " + orderId + "was not found"));

        log.info("Order users size: {}", order.getUsers().size());
        order.setCleaningStatus(cleaningStatus);
        orderRepository.save(order);
        log.info("Order with id {} updated", orderId);

        Set<TelegramUser> telegramUserSet = order.getUsers();
        if (telegramUserSet == null || telegramUserSet.isEmpty()) {
            log.warn("No users associated with order {}", orderId);
        } else {
            if (cleaningStatus == CleaningStatus.CLEANED_AND_READY) {
                for (TelegramUser user : telegramUserSet) {
                    telegramBotServiceImpl.notifyStatusReady(order, user);
                }
            } else if (cleaningStatus == CleaningStatus.COMPLETED) {
                for (TelegramUser user : telegramUserSet) {
                    telegramBotServiceImpl.notifyStatusCompleted(order, user);
                }
                delete(order);
            }

        }
    }


    @Transactional
    public Order getByIdWithUsers(String orderId) throws OrderNotFoundException {
        return orderRepository.findByIdWithUsers(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }



    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public void create(Order order, TelegramUser telegramUser) {
        TelegramUser user = telegramUserService.findById(telegramUser.getId());
        if(user == null){
            throw new TelegramUserNotFound("User cannot be null");
        }

        Order incomingOrder = orderRepository.findById(order.getId()).orElse(null);

        if(incomingOrder == null){
            incomingOrder = order;
            incomingOrder.getUsers().add(user);
            orderRepository.save(incomingOrder);
        }else{
            if (!incomingOrder.getUsers().contains(user)) {
                incomingOrder.getUsers().add(user);
                orderRepository.save(incomingOrder);
            }
        }
    }
}
