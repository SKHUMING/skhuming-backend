package com.itcontest.skhuming.error;

import com.itcontest.skhuming.email.exception.InvalidEmailAddressException;
import com.itcontest.skhuming.error.dto.ErrorResponse;
import com.itcontest.skhuming.jwt.exception.NotMatchTokenException;
import com.itcontest.skhuming.member.exception.InvalidMemberException;
import com.itcontest.skhuming.member.exception.NotFoundMemberException;
import com.itcontest.skhuming.notice.exception.NotFoundNoticeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({
            InvalidMemberException.class,
            InvalidEmailAddressException.class,
            NotFoundMemberException.class,
            NotFoundNoticeException.class,
            NotMatchTokenException.class
    })
    public ResponseEntity<ErrorResponse> handleInvalidate(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
