package com.abilava.notificationservice.services;

import com.abilava.notificationservice.dtos.AuthRequest;
import com.abilava.notificationservice.dtos.AuthResponse;

public interface AuthService {

    AuthResponse registration(final AuthRequest request);

    AuthResponse login(final AuthRequest request);

}
