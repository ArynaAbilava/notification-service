package com.abilava.notificationservice.services;

import com.abilava.notificationservice.dtos.UserDto;
import com.abilava.notificationservice.entities.UserCredential;

public interface UserService {

    UserCredential findByUsernameRequired(String username);

    UserCredential create(String username, String password);

    boolean existsByUsername(String username);

    UserDto findById(String id);

}
