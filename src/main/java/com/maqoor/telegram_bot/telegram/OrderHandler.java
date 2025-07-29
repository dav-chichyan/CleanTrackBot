package com.maqoor.telegram_bot.telegram;
import com.maqoor.telegram_bot.entity.order.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderHandler {

    public String responseBuilder(Order order) {

        if (order.getTotalCost().equals("0.00") || order.getTotalCost().equals("0")) {
            return "*🧾 Order Summary*\n\n\n" +
                    "*🆔 Order ID:* " + order.getId() + "\n\n" +
                    "*🧼 Cleaning Status:* " + order.getCleaningStatus().getDescription() + "\n\n" +
                    "*📦 Order Type:* " + order.getOrderType().getDisplayName() + "\n\n" +
                    order.getPaymentStatus().getMessage() + "\n\n\n" +
                    "*\uD83D\uDD14 We’ll notify you as soon as your order is ready..*\n\n\n" +
                    "🙏 _Thank you for choosing MAQOOR!_";
        }

        if (order.getCleaningStatus().getNumber().equals("0")) {
            return "*🧾 Order Summary*\n\n\n" +
                    "*🆔 Order ID:* " + order.getId() + "\n\n" +
                    "*🧼 Cleaning Status:* " + order.getCleaningStatus().getDescription() + "\n\n" +
                    "*📦 Order Type:* " + order.getOrderType().getDisplayName() + "\n\n" +
                    "*💰 Total Cost:* " + order.getTotalCost() + " ֏\n\n" +
                    order.getPaymentStatus().getMessage() + "\n\n\n" +
                    "*\uD83D\uDD14 We’ll notify you as soon as your order is ready..*\n\n\n" +
                    "🙏 _Thank you for choosing MAQOOR!_";
        }

        return "*🧾 Order Summary*\n\n\n" +
                "*🆔 Order ID:* " + order.getId() + "\n\n" +
                "*🧼 Cleaning Status:* " + order.getCleaningStatus().getDescription() + "\n\n" +
                "*📦 Order Type:* " + order.getOrderType().getDisplayName() + "\n\n" +
                "*💰 Total Cost:* " + order.getTotalCost() + " ֏\n\n" +
                order.getPaymentStatus().getMessage() + "\n\n\n" +
                "*\uD83D\uDD14 We’ll notify you as soon as your order is ready..*\n\n\n" +
                "🙏 _Thank you for choosing MAQOOR!_";

    }

    //TODO USE STRINGBUILDER

    public String updateNotificationBuilder(Order order) {
        if (order.getTotalCost().equals("0.00") || order.getTotalCost().equals("0")) {
            return "\uD83D\uDD14 *Your Order is ready*\n\n\n" +
                    "*🆔 Order ID:* " + order.getId() + "\n\n" +
                    "*🧼 Cleaning Status:* " + order.getCleaningStatus().getDescription() + "\n\n" +
                    "*📦 Order Type:* " + order.getOrderType().getDisplayName() + "\n\n" +
                    order.getPaymentStatus().getMessage() + "\n\n\n" +
                    "🙏 _Thank you for choosing MAQOOR!_";
        }
        return "\uD83D\uDD14 *Your Order is ready*\n\n\n " +
                "*🆔 Order ID:* " + order.getId() + "\n\n" +
                "*🧼 Cleaning Status:* " + order.getCleaningStatus().getDescription() + "\n\n" +
                "*📦 Order Type:* " + order.getOrderType().getDisplayName() + "\n\n" +
                "*💰 Total Cost:* " + order.getTotalCost() + " ֏\n\n" +
                order.getPaymentStatus().getMessage() + "\n\n\n" +
                "🙏 _Thank you for choosing MAQOOR!_";
    }



public String completedNotificationBuilder(Order order) {

    if (order.getTotalCost().equals("0.00") || order.getTotalCost().equals("0")) {
        return "*✅Your Order has been completed*\n\n\n" +
                "*🆔 Order ID:* " + order.getId() + "\n\n" +
                "*🧼 Cleaning Status:* " + order.getCleaningStatus().getDescription() + "\n\n" +
                order.getPaymentStatus().getMessage() + "\n\n\n" +
                "🙏 _Thank you for choosing MAQOOR!_";
    }
    return "*✅Your Order has been completed*\n\n\n" +
            "*🆔 Order ID:* " + order.getId() + "\n\n" +
            "*🧼 Cleaning Status:* " + order.getCleaningStatus().getDescription() + "\n\n" +
            "*💰 Total Cost:* " + order.getTotalCost() + " ֏\n\n" +
            order.getPaymentStatus().getMessage() + "\n\n\n" +
            "🙏 _Thank you for choosing MAQOOR!_";
 }
}
