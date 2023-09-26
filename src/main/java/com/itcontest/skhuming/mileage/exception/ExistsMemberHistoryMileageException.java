package com.itcontest.skhuming.mileage.exception;

public class ExistsMemberHistoryMileageException extends RuntimeException{
    public ExistsMemberHistoryMileageException(final String message) {
        super(message);
    }

    public ExistsMemberHistoryMileageException() {
        this("이미 추가된 내역입니다. 하단의 mileage history를 확인해주세요!");
    }
}
