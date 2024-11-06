package com.abilava.notificationservice.entities;

import com.abilava.dtos.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "user_cred")
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserCredential extends BaseEntity {

    private String username;

    private String password;

}
