package com.abilava.notificationservice.services;

import com.abilava.dtos.SendNotificationEvent;
import com.abilava.notificationservice.producers.NotificationProducer;
import com.abilava.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskService {

    private final NotificationProducer notificationProducer;

    @Async
    public void executeTask(int messageNumber) {
        LoggerUtil.info(String.format("Send %s message", messageNumber));
        notificationProducer.sendNotificationEvent(
                new SendNotificationEvent(String.format("Message number %s", messageNumber))
        );
    }
}
