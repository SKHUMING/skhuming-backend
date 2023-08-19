package com.itcontest.skhuming.mileage.exception;

public class ExistsMemberHistoryMileageException extends RuntimeException{
    public ExistsMemberHistoryMileageException(final String message) {
        super(message);
    }

    public ExistsMemberHistoryMileageException() {
        this("이미 추가되었습니다!");
    }
}
