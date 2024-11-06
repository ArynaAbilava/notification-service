package com.abilava.notificationservice.controllers;


import com.abilava.notificationservice.dtos.MessageRequest;
import com.abilava.notificationservice.dtos.UserDto;
import com.abilava.notificationservice.services.NotificationService;
import com.abilava.notificationservice.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    @PostMapping
    public void sendNotifications(@RequestBody @NonNull final MessageRequest request) {
        notificationService.sendNotifications(request);
    }

    @GetMapping("{id}")
    public UserDto getById(@PathVariable("id") String id) {
        return userService.findById(id);
    }

}
