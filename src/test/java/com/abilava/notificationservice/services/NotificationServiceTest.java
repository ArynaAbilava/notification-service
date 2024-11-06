package com.abilava.notificationservice.services;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.abilava.notificationservice.dtos.MessageRequest;
import com.abilava.notificationservice.services.impl.NotificationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    @DisplayName("Send notification success path")
    void sendNotificationSuccessPath() {
        MessageRequest request = new MessageRequest(5);

        // WHEN
        notificationService.sendNotifications(request);

        // THEN
        verify(taskService, times(5)).executeTask(anyInt());
    }

}
