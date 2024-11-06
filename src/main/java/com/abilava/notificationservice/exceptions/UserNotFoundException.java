package com.abilava.notificationservice.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("exception.user.not_found");
    }

}
