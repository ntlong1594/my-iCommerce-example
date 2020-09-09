package com.icommerce.developer.product.messaging;

import com.icommerce.developer.product.domain.Product;
import com.icommerce.developer.product.security.SecurityUtils;
import com.icommerce.developer.product.service.dto.ProductChangelogHistoricalEvent;
import com.icommerce.developer.product.service.dto.UserActivitiesHistoricalEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProductChangelogHistoricalEventPublisher {
    private final Logger logger = LoggerFactory.getLogger(ProductChangelogHistoricalEventPublisher.class);
    private final HistoricalEventChannel historicalEventChannel;

    public ProductChangelogHistoricalEventPublisher(HistoricalEventChannel historicalEventChannel) {
        this.historicalEventChannel = historicalEventChannel;
    }

    public void publish(Product product) {
        try {
            Map<String, Object> detail = new HashMap<>();
            detail.put("title", product.getTitle());
            detail.put("brand", product.getBrand());
            detail.put("price", product.getPrice());
            ProductChangelogHistoricalEvent productChangelogHistoricalEvent = new ProductChangelogHistoricalEvent();
            productChangelogHistoricalEvent.setProductId(product.getId());
            productChangelogHistoricalEvent.setUpdateDate(LocalDate.now());
            productChangelogHistoricalEvent.setVersion(product.getVersion());
            productChangelogHistoricalEvent.setUserId(SecurityUtils.getCurrentUserLogin().orElse(""));
            productChangelogHistoricalEvent.setDetail(detail);

            Message<ProductChangelogHistoricalEvent> message = MessageBuilder.withPayload(productChangelogHistoricalEvent)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
            historicalEventChannel.productChangelogHistoricalEventOutputChannel()
                .send(message);
        } catch (Exception exception) {
            logger.error("Unable to send product historical to kafka", exception);
        }
    }
}
