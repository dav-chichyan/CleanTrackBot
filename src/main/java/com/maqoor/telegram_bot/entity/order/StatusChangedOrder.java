package com.maqoor.telegram_bot.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Map;

@Setter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class StatusChangedOrder {

    private String id;

    private Map<String,String> data;

    public String getStatusNumber(String status){
        return data.get(status);
    }
}
