package com.github.rxbertoo.auth.exception;

import lombok.Getter;

@Getter
public class BusinessRuleException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessRuleException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessRuleException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
    }

    public BusinessRuleException(String message) {
        super(message);
        this.errorCode = ErrorCode.BUSINESS_RULE_VIOLATION;
    }
}