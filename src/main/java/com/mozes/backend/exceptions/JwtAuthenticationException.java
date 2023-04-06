package com.mozes.backend.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtAuthenticationException(String message) {
        super(message);
    }
}
