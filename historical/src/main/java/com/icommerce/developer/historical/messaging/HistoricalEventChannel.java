package com.icommerce.developer.historical.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface HistoricalEventChannel {
    String PRODUCT_INPUT = "productChangelogHistoricalEventInputChannel";
    String ACTIVIES_INPUT = "userActivitiesHistoricalEventInputChannel";

    @Input(PRODUCT_INPUT)
    MessageChannel productChangelogHistoricalEventInputChannel();

    @Input(ACTIVIES_INPUT)
    MessageChannel userActivitiesHistoricalEventInputChannel();
}
