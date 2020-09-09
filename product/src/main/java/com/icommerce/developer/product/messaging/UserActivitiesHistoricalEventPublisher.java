package com.icommerce.developer.product.messaging;

import com.icommerce.developer.product.security.SecurityUtils;
import com.icommerce.developer.product.service.dto.UserActivitiesHistoricalEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.time.LocalDate;

@Component
public class UserActivitiesHistoricalEventPublisher {
    private final Logger logger = LoggerFactory.getLogger(UserActivitiesHistoricalEventPublisher.class);
    private final HistoricalEventChannel historicalEventChannel;

    public UserActivitiesHistoricalEventPublisher(HistoricalEventChannel historicalEventChannel) {
        this.historicalEventChannel = historicalEventChannel;
    }

    public void publish(String actionId, String actionDescription) {
        try {
            UserActivitiesHistoricalEvent userActivitiesHistoricalEvent = new UserActivitiesHistoricalEvent();
            userActivitiesHistoricalEvent.setActionDate(LocalDate.now());
            userActivitiesHistoricalEvent.setActionId(actionId);
            userActivitiesHistoricalEvent.setActionDescription(actionDescription);
            userActivitiesHistoricalEvent.setUserId(SecurityUtils.getCurrentUserLogin().orElse(""));

            Message<UserActivitiesHistoricalEvent> message = MessageBuilder.withPayload(userActivitiesHistoricalEvent)
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                    .build();
            historicalEventChannel.userActivitiesHistoricalEventOutputChannel()
                    .send(message);
        } catch (Exception exception) {
            logger.error("Unable to send user historical to kafka", exception);
        }
    }
}
