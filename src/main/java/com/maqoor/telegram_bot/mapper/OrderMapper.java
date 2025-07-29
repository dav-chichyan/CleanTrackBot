package com.maqoor.telegram_bot.mapper;

import com.maqoor.telegram_bot.entity.order.GetOrderResponse;
import com.maqoor.telegram_bot.entity.order.Order;
import com.maqoor.telegram_bot.entity.constant.CleaningStatus;
import com.maqoor.telegram_bot.entity.constant.OrderType;
import com.maqoor.telegram_bot.entity.constant.PaymentStatus;
import com.maqoor.telegram_bot.util.TotalCostUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class OrderMapper {

    private static final Logger log = LoggerFactory.getLogger(OrderMapper.class);

    public Order mapToOrder(GetOrderResponse orderResponse) {

        if (!orderResponse.isSuccess() || CollectionUtils.isEmpty(orderResponse.getOrders())) {
            return null;
        }
        Order rawOrder = orderResponse.getOrders().getFirst();


        if(rawOrder.getCleaningStatus() == CleaningStatus.fromNumber("2")) {
            return new Order(rawOrder.getCleaningStatus());
        }
        Order order = new Order();
        order.setId(rawOrder.getId());
        PaymentStatus isPaid = rawOrder.getPaymentStatus();
        order.setPaymentStatus(PaymentStatus.fromNumber(isPaid.getNumber()));
        CleaningStatus isCleaned = rawOrder.getCleaningStatus();
        order.setCleaningStatus(CleaningStatus.fromNumber(isCleaned.getNumber()));
        order.setTotalCost(TotalCostUtil.getFinalTotal(rawOrder.getTotalCost()));
        order.setOrderType(OrderType.getOrderType(
                rawOrder.getLockerOrder(),
                rawOrder.getDeliveryOrder())
        );
        order.setCustomerId(rawOrder.getCustomerId());
        log.info("Order is mapped");
        return order;
    }
}
