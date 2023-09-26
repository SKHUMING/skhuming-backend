package com.itcontest.skhuming.global.jwt.exception;

public class NotMatchTokenException extends RuntimeException {
    public NotMatchTokenException(String message) {
        super(message);
    }

    public NotMatchTokenException() {
        this("토큰이 일치하지 않습니다.");
    }
}
