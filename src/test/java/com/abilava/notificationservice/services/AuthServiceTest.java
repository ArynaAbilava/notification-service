package com.abilava.notificationservice.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.abilava.notificationservice.dtos.AuthRequest;
import com.abilava.notificationservice.entities.UserCredential;
import com.abilava.notificationservice.exceptions.UserExistsException;
import com.abilava.notificationservice.exceptions.UserNotFoundException;
import com.abilava.notificationservice.security.JwtUtil;
import com.abilava.notificationservice.services.impl.AuthServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @DisplayName("Registration success path")
    void registrationSuccessPath() {
        var request = new AuthRequest("username", "password");
        var encodedPassword = "EncodedPassword";
        var user = new UserCredential();

        //GIVEN
        when(userService.existsByUsername(request.username())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn(encodedPassword);
        when(userService.create(request.username(), encodedPassword)).thenReturn(user);
        when(userService.findByUsernameRequired(request.username())).thenReturn(user);
        when(authenticationManager.authenticate(any())).thenReturn(any());
        when(jwtUtil.createToken(user)).thenReturn(any());

        //WHEN
        authService.registration(request);

        //THEN
        verify(userService).existsByUsername(request.username());
        verify(passwordEncoder).encode(request.password());
        verify(userService).create(request.username(), encodedPassword);
        verify(userService).findByUsernameRequired(request.username());
        verify(authenticationManager).authenticate(any());
        verify(jwtUtil).createToken(user);
    }

    @Test
    @DisplayName("Registration when user exists")
    void registrationUserExists() {
        var request = new AuthRequest("username", "password");
        var encodedPassword = "EncodedPassword";
        var user = new UserCredential();

        //GIVEN
        when(userService.existsByUsername(request.username())).thenReturn(true);

        //WHEN
        assertThrows(UserExistsException.class, () -> authService.registration(request));

        //THEN
        verify(userService).existsByUsername(request.username());
        verify(passwordEncoder, never()).encode(request.password());
        verify(userService, never()).create(request.username(), encodedPassword);
        verify(userService, never()).findByUsernameRequired(request.username());
        verify(authenticationManager, never()).authenticate(any());
        verify(jwtUtil, never()).createToken(user);
    }

    @Test
    @DisplayName("Login success path")
    void loginSuccessPath() {
        var request = new AuthRequest("username", "password");
        var user = new UserCredential();

        //GIVEN
        when(userService.findByUsernameRequired(request.username())).thenReturn(user);
        when(authenticationManager.authenticate(any())).thenReturn(any());
        when(jwtUtil.createToken(user)).thenReturn(any());

        //WHEN
        authService.login(request);

        //THEN
        verify(userService).findByUsernameRequired(request.username());
        verify(authenticationManager).authenticate(any());
        verify(jwtUtil).createToken(user);
    }

    @Test
    @DisplayName("Login when user not found")
    void loginWhenUserNotFound() {
        var request = new AuthRequest("username", "password");
        var user = new UserCredential();

        //GIVEN
        doThrow(new UserNotFoundException()).when(userService).findByUsernameRequired(request.username());

        //WHEN
        assertThrows(UserNotFoundException.class, () -> authService.login(request));

        //THEN
        verify(userService).findByUsernameRequired(request.username());
        verify(authenticationManager, never()).authenticate(any());
        verify(jwtUtil, never()).createToken(user);
    }

}
