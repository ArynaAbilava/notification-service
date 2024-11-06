package com.abilava.notificationservice.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.abilava.notificationservice.dtos.AuthRequest;
import com.abilava.notificationservice.services.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    @DisplayName("Registration success path")
    void registration() {
        var request = new AuthRequest("username", "password");

        //GIVEN
        when(authService.registration(request)).thenReturn(any());

        //WHEN
        authController.registration(request);

        //THEN
        verify(authService).registration(request);
    }

    @Test
    @DisplayName("Login success path")
    void login() {
        var request = new AuthRequest("username", "password");

        //GIVEN
        when(authService.login(request)).thenReturn(any());

        //WHEN
        authController.login(request);

        //THEN
        verify(authService).login(request);
    }

}
