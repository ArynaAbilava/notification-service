package com.abilava.notificationservice.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("exception.user.invalid_credentials");
    }

}
