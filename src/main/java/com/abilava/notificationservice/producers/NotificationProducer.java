package com.abilava.notificationservice.producers;

import com.abilava.dtos.SendNotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationProducer {

    @Value(value = "${topics.outbound.send-notification}")
    private String sendNotificationTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendNotificationEvent(SendNotificationEvent event) {
        this.kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        this.kafkaTemplate.send(sendNotificationTopic, "notification-key", event);
    }

}
