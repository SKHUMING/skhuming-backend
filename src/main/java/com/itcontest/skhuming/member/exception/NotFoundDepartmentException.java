package com.itcontest.skhuming.member.exception;

public class NotFoundDepartmentException extends RuntimeException {
    public NotFoundDepartmentException(String message) {
        super(message);
    }

    public NotFoundDepartmentException() {
        this("해당 ID의 랭킹이 존재하지 않습니다.");
    }
}
