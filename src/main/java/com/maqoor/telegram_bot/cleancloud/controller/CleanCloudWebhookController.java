package com.maqoor.telegram_bot.cleancloud.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maqoor.telegram_bot.entity.WebhookEventProcessor;
import com.maqoor.telegram_bot.entity.constant.CleaningStatus;
import com.maqoor.telegram_bot.exceptions.OrderNotFoundException;
import com.maqoor.telegram_bot.service.OrderService;
import com.maqoor.telegram_bot.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CleanCloudWebhookController {

    @Autowired
    public OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;


    @RequestMapping("/webhook/receiver")
    public ResponseEntity<String> webhookReceiver(@RequestBody String payload) throws JsonProcessingException {

        log.info("Webhook received: {}", payload);
        WebhookEventProcessor webhookEventProcessor = objectMapper.readValue(payload, WebhookEventProcessor.class);
        if (webhookEventProcessor.getEvent().equals(Constants.EVENT_SOURCE_STATUS_CHANGED)) {
            try {
                orderService.updateStatus(webhookEventProcessor.getId(),
                        CleaningStatus.fromNumber(webhookEventProcessor.getStatusNumber("new_status")));
                log.info("App Status has been updated");
            } catch (OrderNotFoundException e) {
                log.error("Order not found");
            }
        }
        return ResponseEntity.ok("Webhook Event has been received");

    }
}



