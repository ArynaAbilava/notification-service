package com.abilava.notificationservice.mappers;

import com.abilava.notificationservice.dtos.UserDto;
import com.abilava.notificationservice.dtos.UserProjection;
import com.abilava.notificationservice.entities.UserCredential;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserCredentialMapper.class)
public interface UserCredentialMapper {

    UserCredential toEntity(String username, String password);

    UserDto toDto(UserProjection projection);
}
