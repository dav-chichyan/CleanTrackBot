package com.maqoor.telegram_bot.telegram.entity;


import com.maqoor.telegram_bot.entity.order.Order;
import com.maqoor.telegram_bot.telegram.enums.UserState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "telegram_users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUser {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserState userState;

    @Column(name = "request_count")
    private Integer requestCount;

    @Column(name = "first_attempt_at")
    private LocalDateTime firstAttemptAt;

    @ManyToMany(mappedBy = "users")
    private Set<Order> orders = new HashSet<>();

    public TelegramUser(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TelegramUser)) return false;
        TelegramUser that = (TelegramUser) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31 + (id == null ? 0 : id.hashCode());
    }

    @Override
    public String toString() {
        return "TelegramUser{id=" + id + ", userState=" + userState + "}";
    }
}
