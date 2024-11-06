package com.abilava.notificationservice.exceptions;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super("exception.user.token_expired");
    }

}
