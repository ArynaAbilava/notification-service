package com.abilava.notificationservice.services.impl;

import com.abilava.notificationservice.dtos.UserDto;
import com.abilava.notificationservice.entities.UserCredential;
import com.abilava.notificationservice.exceptions.UserNotFoundException;
import com.abilava.notificationservice.mappers.UserCredentialMapper;
import com.abilava.notificationservice.repositories.UserCredentialRepository;
import com.abilava.notificationservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCredentialMapper userCredentialMapper;
    private final UserCredentialRepository userCredentialRepository;

    @Override
    @Transactional(readOnly = true)
    public UserCredential findByUsernameRequired(final String username) {
        return userCredentialRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional
    public UserCredential create(final String username, final String password) {
        var userCred = userCredentialMapper.toEntity(username, password);
        return userCredentialRepository.save(userCred);
    }

    @Override
    public boolean existsByUsername(final String username) {
        return userCredentialRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(String id) {
        var userProjection = userCredentialRepository.fetchById(id);
        return userCredentialMapper.toDto(userProjection);
    }

}
