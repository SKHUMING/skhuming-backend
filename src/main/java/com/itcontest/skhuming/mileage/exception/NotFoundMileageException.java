package com.itcontest.skhuming.mileage.exception;

public class NotFoundMileageException extends RuntimeException{

    public NotFoundMileageException(final String message) {
        super(message);
    }

    public NotFoundMileageException() {
        this("잘못된 정보입니다.");
    }
}
