package com.itcontest.skhuming.notice.exception;

public class NotFoundNoticeException extends RuntimeException{
    public NotFoundNoticeException(final String message) {
        super(message);
    }

    public NotFoundNoticeException() {
        this("공지를 찾을 수 없습니다.");
    }
}
