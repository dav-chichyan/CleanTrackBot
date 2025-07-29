package com.maqoor.telegram_bot.cleancloud.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maqoor.telegram_bot.entity.order.GetOrderResponse;
import com.maqoor.telegram_bot.entity.order.Order;
import com.maqoor.telegram_bot.exceptions.CleanCloudClientException;
import com.maqoor.telegram_bot.exceptions.OrderParsingError;
import com.maqoor.telegram_bot.mapper.OrderMapper;
import com.maqoor.telegram_bot.security.MyDecryptor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.maqoor.telegram_bot.util.Constants.*;

@Slf4j
@Component
public class CleanCloudAPIClient {

    @Value("${cleancloud.api.token}")
    private String token;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Value("${cleancloud.api.url}")
    private String url;

    private final String decKey = "bowe";

    private String decToken;


    @PostConstruct
    public void init() {
        decToken = MyDecryptor.decrypt(token, decKey);
    }

    public Order getOrder(String orderId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> body = Map.of(
                API_CC_TOKEN, decToken,
                ORDER_ID, orderId
        );
        try {
            String jsonBody = objectMapper.writeValueAsString(body);
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url + "/getOrders", request, String.class);
            GetOrderResponse orderResponse = objectMapper.readValue(response.getBody(), GetOrderResponse.class);
            return orderMapper.mapToOrder(orderResponse);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse response of this order {}", orderId, e);
            throw new OrderParsingError("Failed to parse response of this order " + orderId);
        } catch (RestClientException e) {
            log.error("RestClient error occurred", e);
            throw new CleanCloudClientException("Failed on calling CleanCloud api for this order " + orderId);
        }
    }

}