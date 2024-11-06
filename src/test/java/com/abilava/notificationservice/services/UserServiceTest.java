package com.abilava.notificationservice.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.abilava.notificationservice.dtos.AuthRequest;
import com.abilava.notificationservice.entities.UserCredential;
import com.abilava.notificationservice.exceptions.UserExistsException;
import com.abilava.notificationservice.exceptions.UserNotFoundException;
import com.abilava.notificationservice.mappers.UserCredentialMapper;
import com.abilava.notificationservice.repositories.UserCredentialRepository;
import com.abilava.notificationservice.services.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserCredentialMapper userCredentialMapper;

    @Mock
    private UserCredentialRepository userCredentialRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Find user by username required success path")
    void findByUsernameRequiredSuccessPath() {
        var username = "username";
        var user = new UserCredential();

        //GIVEN
        when(userCredentialRepository.findByUsername(username)).thenReturn(Optional.of(user));

        //WHEN
        userService.findByUsernameRequired(username);

        //THEN
        verify(userCredentialRepository).findByUsername(username);
    }

    @Test
    @DisplayName("Find user by username required when user doesn't exist")
    void findByUsernameRequiredUserNotFount() {
        var username = "username";

        //GIVEN
        when(userCredentialRepository.findByUsername(username)).thenReturn(Optional.empty());

        //WHEN
        assertThrows(UserNotFoundException.class, () -> userService.findByUsernameRequired(username));

        //THEN
        verify(userCredentialRepository).findByUsername(username);
    }

    @Test
    @DisplayName("Create user success path")
    void createUserSuccessPath() {
        var username = "username";
        var password = "password";
        var user = new UserCredential();

        //GIVEN
        when(userCredentialMapper.toEntity(username, password)).thenReturn(user);
        when(userCredentialRepository.save(user)).thenReturn(user);

        //WHEN
        userService.create(username, password);

        //THEN
        verify(userCredentialMapper).toEntity(username, password);
        verify(userCredentialRepository).save(user);
    }

    @Test
    @DisplayName("User exists success path")
    void existsUserSuccessPath() {
        var username = "username";

        //GIVEN
        when(userCredentialRepository.existsByUsername(username)).thenReturn(true);

        //WHEN
        userService.existsByUsername(username);

        //THEN
        verify(userCredentialRepository).existsByUsername(username);
    }

}
