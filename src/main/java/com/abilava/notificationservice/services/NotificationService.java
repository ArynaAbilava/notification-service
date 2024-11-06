package com.abilava.notificationservice.services;

import com.abilava.notificationservice.dtos.MessageRequest;
import com.abilava.notificationservice.dtos.UserDto;

public interface NotificationService {

    void sendNotifications(MessageRequest request);

}
