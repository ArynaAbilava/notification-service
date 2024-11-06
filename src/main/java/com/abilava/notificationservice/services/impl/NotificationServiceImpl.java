package com.abilava.notificationservice.services.impl;

import com.abilava.notificationservice.dtos.MessageRequest;
import com.abilava.notificationservice.dtos.UserDto;
import com.abilava.notificationservice.services.TaskService;
import com.abilava.notificationservice.services.NotificationService;
import com.abilava.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final TaskService taskService;

    @Async
    @Override
    public void sendNotifications(final MessageRequest request) {
        LoggerUtil.info("Start notification sending");
        IntStream.range(0, request.count()).forEach(taskService::executeTask);
    }

}
