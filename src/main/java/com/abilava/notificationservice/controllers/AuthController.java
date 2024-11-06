package com.abilava.notificationservice.controllers;

import com.abilava.notificationservice.dtos.AuthRequest;
import com.abilava.notificationservice.dtos.AuthResponse;
import com.abilava.notificationservice.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    @PostMapping("registration")
    public AuthResponse registration(@RequestBody final AuthRequest request) {
        return authService.registration(request);
    }

    @PostMapping("login")
    public AuthResponse login(@RequestBody final AuthRequest request) {
       return authService.login(request);
    }

}
