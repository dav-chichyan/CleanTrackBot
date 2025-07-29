package com.maqoor.telegram_bot.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;


import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class GetOrderResponse {
    @JsonProperty("Success")
    private String success;

    @Getter
    @JsonProperty("Orders")
    private List<Order> orders;

    public boolean isSuccess() {
        return "True".equalsIgnoreCase(success);
    }

}
