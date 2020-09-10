package com.icommerce.developer.historical.messaging;

import com.icommerce.developer.historical.domain.ProductChangelogHistorical;
import com.icommerce.developer.historical.domain.UserActivitiesHistorical;
import com.icommerce.developer.historical.repository.ProductChangelogHistoricalRepository;
import com.icommerce.developer.historical.repository.UserActivitiesHistoricalRepository;
import com.icommerce.developer.historical.service.dto.ProductChangelogHistoricalEvent;
import com.icommerce.developer.historical.service.dto.UserActivitiesHistoricalEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class HistoricalEventConsumerTest {

    @Mock
    private UserActivitiesHistoricalRepository userActivitiesHistoricalRepository;

    @Mock
    private ProductChangelogHistoricalRepository productChangelogHistoricalRepository;

    @InjectMocks
    HistoricalEventConsumer historicalEventConsumer;

    @Test
    public void consumeUserActivitiesHistoricalEvent_should_work_correctly() {
        // Given
        UserActivitiesHistoricalEvent event = new UserActivitiesHistoricalEvent();
        event.setActionDate(LocalDate.now());
        event.setActionDescription("description");
        event.setActionId("id");
        event.setUserId("userId");
        // When
        historicalEventConsumer.consumeUserActivitiesHistoricalEvent(event);
        // Then
        ArgumentCaptor<UserActivitiesHistorical> catpor = ArgumentCaptor.forClass(UserActivitiesHistorical.class);
        verify(userActivitiesHistoricalRepository).save(catpor.capture());
        assertThat(catpor.getValue().getActionDate()).isEqualTo(LocalDate.now());
        assertThat(catpor.getValue().getActionDescription()).isEqualTo("description");
        assertThat(catpor.getValue().getActionId()).isEqualTo("id");
        assertThat(catpor.getValue().getUserId()).isEqualTo("userId");
    }

    @Test
    public void consumeUserProductChangelogEvent_should_work_correctly() {
        // Given
        ProductChangelogHistoricalEvent event = new ProductChangelogHistoricalEvent();
        Map map = mock(Map.class);
        event.setUpdateDate(LocalDate.now());
        event.setDetail(map);
        event.setProductId("productId");
        event.setVersion(0);
        event.setUserId("userId");
        // When
        historicalEventConsumer.consumeUserProductChangelogEvent(event);
        // Then
        ArgumentCaptor<ProductChangelogHistorical> catpor = ArgumentCaptor.forClass(ProductChangelogHistorical.class);
        verify(productChangelogHistoricalRepository).save(catpor.capture());
        assertThat(catpor.getValue().getDetail()).isSameAs(map);
        assertThat(catpor.getValue().getProductId()).isEqualTo("productId");
        assertThat(catpor.getValue().getVersion()).isEqualTo(0);
        assertThat(catpor.getValue().getUpdatedBy()).isEqualTo("userId");
    }
}
