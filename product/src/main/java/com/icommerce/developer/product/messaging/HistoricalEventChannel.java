package com.icommerce.developer.product.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface HistoricalEventChannel {

    String ACTIVITIES_OUTPUT = "userActivitiesHistoricalEventOutputChannel";

    String PRODUCT_CHANGELOG_OUTPUT = "productChangelogHistoricalEventOutputChannel";

    @Output(ACTIVITIES_OUTPUT)
    MessageChannel userActivitiesHistoricalEventOutputChannel();

    @Output(PRODUCT_CHANGELOG_OUTPUT)
    MessageChannel productChangelogHistoricalEventOutputChannel();
}
