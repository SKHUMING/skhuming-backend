package com.itcontest.skhuming.notice.exception;

public class NotScheduleFormatException extends RuntimeException{

    public NotScheduleFormatException(final String message) {
        super(message);
    }

    public NotScheduleFormatException() {
        this("날짜 형식이 일치하지 않습니다.");
    }
}
