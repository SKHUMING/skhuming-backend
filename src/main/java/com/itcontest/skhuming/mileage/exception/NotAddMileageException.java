package com.itcontest.skhuming.mileage.exception;

public class NotAddMileageException extends RuntimeException {
    public NotAddMileageException(final String message) {
        super(message);
    }

    public NotAddMileageException() {
        this("활동한 비교과 프로그램을 선택해주세요");
    }


}
