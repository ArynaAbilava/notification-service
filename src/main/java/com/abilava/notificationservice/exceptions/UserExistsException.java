package com.abilava.notificationservice.exceptions;

public class UserExistsException extends RuntimeException {

    public UserExistsException() {
        super("exception.user.exists");
    }

}
