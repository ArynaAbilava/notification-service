package com.abilava.notificationservice.services.impl;

import com.abilava.notificationservice.dtos.AuthRequest;
import com.abilava.notificationservice.dtos.AuthResponse;
import com.abilava.notificationservice.dtos.UserDto;
import com.abilava.notificationservice.exceptions.UserExistsException;
import com.abilava.notificationservice.security.JwtUtil;
import com.abilava.notificationservice.services.AuthService;
import com.abilava.notificationservice.services.UserService;
import com.abilava.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    @Override
    @Transactional
    public AuthResponse registration(final AuthRequest request) {
        var exists = userService.existsByUsername(request.username());
        if (exists) {
            throw new UserExistsException();
        }
        var encodedPassword = passwordEncoder.encode(request.password());
        userService.create(request.username(), encodedPassword);
        LoggerUtil.info(String.format("User with username %s has been registered", request.username()));
        return login(request);
    }

    @Override
    public AuthResponse login(final AuthRequest request) {

        var user = userService.findByUsernameRequired(request.username());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        var token = jwtUtil.createToken(user);
        return new AuthResponse(token);
    }


}
