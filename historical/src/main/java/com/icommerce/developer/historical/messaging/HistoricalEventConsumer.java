package com.icommerce.developer.historical.messaging;

import com.icommerce.developer.historical.domain.ProductChangelogHistorical;
import com.icommerce.developer.historical.domain.UserActivitiesHistorical;
import com.icommerce.developer.historical.repository.ProductChangelogHistoricalRepository;
import com.icommerce.developer.historical.repository.UserActivitiesHistoricalRepository;
import com.icommerce.developer.historical.service.dto.ProductChangelogHistoricalEvent;
import com.icommerce.developer.historical.service.dto.UserActivitiesHistoricalEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class HistoricalEventConsumer {

    private final Logger logger = LoggerFactory.getLogger(HistoricalEventConsumer.class);

    private final UserActivitiesHistoricalRepository userActivitiesHistoricalRepository;

    private final ProductChangelogHistoricalRepository productChangelogHistoricalRepository;

    public HistoricalEventConsumer(UserActivitiesHistoricalRepository userActivitiesHistoricalRepository,
                                   ProductChangelogHistoricalRepository productChangelogHistoricalRepository) {
        this.userActivitiesHistoricalRepository = userActivitiesHistoricalRepository;
        this.productChangelogHistoricalRepository = productChangelogHistoricalRepository;
    }

    @StreamListener(HistoricalEventChannel.ACTIVIES_INPUT)
    public void consumeUserActivitiesHistoricalEvent(@Payload UserActivitiesHistoricalEvent userActivitiesHistoricalEvent) {
        logger.info("Consume userActivitiesHistoricalEvent");
        UserActivitiesHistorical userActivitiesHistorical = new UserActivitiesHistorical();
        userActivitiesHistorical.setActionDate(userActivitiesHistoricalEvent.getActionDate());
        userActivitiesHistorical.setActionDescription(userActivitiesHistoricalEvent.getActionDescription());
        userActivitiesHistorical.setActionId(userActivitiesHistoricalEvent.getActionId());
        userActivitiesHistorical.setUserId(userActivitiesHistoricalEvent.getUserId());
        userActivitiesHistoricalRepository.save(userActivitiesHistorical);
        logger.info("Done consuming userActivitiesHistoricalEvent");
    }

    @StreamListener(HistoricalEventChannel.PRODUCT_INPUT)
    public void consumeUserProductChangelogEvent(@Payload ProductChangelogHistoricalEvent productChangelogHistoricalEvent) {
        logger.info("Consume productChangelogHistoricalEvent");
        if (!productChangelogHistoricalRepository
            .existsByProductIdAndVersion(productChangelogHistoricalEvent.getProductId(), productChangelogHistoricalEvent.getVersion())) {
            ProductChangelogHistorical productChangelogHistorical = new ProductChangelogHistorical();
            productChangelogHistorical.setDetail(productChangelogHistoricalEvent.getDetail());
            productChangelogHistorical.setProductId(productChangelogHistoricalEvent.getProductId());
            productChangelogHistorical.setUpdatedDate(productChangelogHistoricalEvent.getUpdateDate());
            productChangelogHistorical.setVersion(productChangelogHistoricalEvent.getVersion());
            productChangelogHistorical.setUpdatedBy(productChangelogHistoricalEvent.getUserId());
            productChangelogHistoricalRepository.save(productChangelogHistorical);
        }
        logger.info("Done consuming productChangelogHistoricalEvent");
    }

}
