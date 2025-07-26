package com.external.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // 400 상태 코드 자동 반환
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}