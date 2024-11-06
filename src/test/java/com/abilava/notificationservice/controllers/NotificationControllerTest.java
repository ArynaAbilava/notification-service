package com.abilava.notificationservice.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.abilava.notificationservice.dtos.MessageRequest;
import com.abilava.notificationservice.services.NotificationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    @DisplayName("Send notifications success path")
    void sendNotifications() {
        var request = new MessageRequest(1000);

        //GIVEN
        doNothing().when(notificationService).sendNotifications(request);

        //WHEN
        notificationController.sendNotifications(request);

        //THEN
        verify(notificationService).sendNotifications(request);
    }
}
