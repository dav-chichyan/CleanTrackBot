package com.maqoor.telegram_bot.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maqoor.telegram_bot.entity.constant.CleaningStatus;
import com.maqoor.telegram_bot.entity.constant.OrderType;
import com.maqoor.telegram_bot.entity.constant.PaymentStatus;
import com.maqoor.telegram_bot.telegram.entity.TelegramUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "orders")
public class Order {
    @JsonProperty("id")
    @Id
    @Column(name = "id")
    private String id;

    @JsonProperty("paid")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @JsonProperty("status")
    @Column(name = "cleaning_status")
    @Enumerated(EnumType.STRING)
    private CleaningStatus cleaningStatus;

    @JsonProperty("total")
    @Column(name = "total_cost")
    private String totalCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OrderType orderType;

    @JsonProperty("lockerOrder")
    private String lockerOrder;

    @JsonProperty("delivery")
    private String deliveryOrder;

    @JsonProperty("customerID")
    private String customerId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "chat",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<TelegramUser> users = new HashSet<>();

    public Order (CleaningStatus isCompleted){
        if (isCompleted == CleaningStatus.COMPLETED){
            this.cleaningStatus = CleaningStatus.COMPLETED;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order that = (Order) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31 + (id == null ? 0 : id.hashCode());
    }

    @Override
    public String toString() {
        return "Order{id=" + id +
                ", paymentStatus=" + paymentStatus +
                ", cleaningStatus=" + cleaningStatus +
                ", totalCost='" + totalCost + '\'' +
                ", orderType=" + orderType +
                ", lockerOrder='" + lockerOrder + '\'' +
                ", deliveryOrder='" + deliveryOrder + '\'' +
                ", customerId='" + customerId + '\'' +
                "}";
    }
}
