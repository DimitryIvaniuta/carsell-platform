package com.carsell.platform.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationProcessingException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public JwtAuthenticationProcessingException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationProcessingException(String msg) {
        super(msg);
    }

}
