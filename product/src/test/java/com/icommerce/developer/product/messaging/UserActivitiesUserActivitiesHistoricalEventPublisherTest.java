package com.icommerce.developer.product.messaging;

import com.icommerce.developer.product.service.dto.UserActivitiesHistoricalEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.MimeTypeUtils;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserActivitiesUserActivitiesHistoricalEventPublisherTest {

    @Mock
    HistoricalEventChannel historicalEventChannel;

    @InjectMocks
    UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher;

    @Test
    public void publish_should_work_correctly() {
        // Given
        MessageChannel messageChannel = mock(MessageChannel.class);
        when(historicalEventChannel.userActivitiesHistoricalEventOutputChannel()).thenReturn(messageChannel);
        // When
        userActivitiesHistoricalEventPublisher.publish("actionId", "actionDescription");
        // Then
        ArgumentCaptor<Message<UserActivitiesHistoricalEvent>> captor = ArgumentCaptor.forClass(Message.class);
        verify(historicalEventChannel).userActivitiesHistoricalEventOutputChannel();
        verify(messageChannel).send(captor.capture());
        Message<UserActivitiesHistoricalEvent> result = captor.getValue();
        assertThat(result.getHeaders().get(MessageHeaders.CONTENT_TYPE)).isEqualTo(MimeTypeUtils.APPLICATION_JSON);
        assertThat(result.getPayload().getActionId()).isEqualTo("actionId");
        assertThat(result.getPayload().getActionDate()).isEqualTo(LocalDate.now());
        assertThat(result.getPayload().getActionDescription()).isEqualTo("actionDescription");
        assertThat(result.getPayload().getUserId()).isEqualTo("");
    }
}
