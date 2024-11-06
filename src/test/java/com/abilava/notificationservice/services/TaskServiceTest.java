package com.abilava.notificationservice.services;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

import com.abilava.notificationservice.producers.NotificationProducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private NotificationProducer notificationProducer;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("Execute notification sending task success path")
    void executeTaskSuccessPath() {
        int messageNumber = 1;

        // WHEN
        taskService.executeTask(messageNumber);

        // THEN
        verify(notificationProducer).sendNotificationEvent(
                argThat(event -> event.content().equals("Message number 1"))
        );
    }

}
