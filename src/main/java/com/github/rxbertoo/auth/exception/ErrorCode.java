package com.github.rxbertoo.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Auth errors (AUTH_xxx)
    INVALID_CREDENTIALS("AUTH_001", "Email or password is invalid", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("AUTH_002", "JWT token has expired", HttpStatus.UNAUTHORIZED),

    // User errors (USER_xxx)
    USER_NOT_FOUND("USER_001", "User not found", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS("USER_002", "Email already exists", HttpStatus.BAD_REQUEST),

    // General Business errors (GEN_xxx)
    BUSINESS_RULE_VIOLATION("GEN_001", "Business rule violated", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
