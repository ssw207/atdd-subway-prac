package com.subway.common.exception.auth;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }
}
