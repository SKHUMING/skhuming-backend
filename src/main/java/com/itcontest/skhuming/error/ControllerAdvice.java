package com.itcontest.skhuming.error;

import com.itcontest.skhuming.email.exception.InvalidEmailAddressException;
import com.itcontest.skhuming.error.dto.ErrorResponse;
import com.itcontest.skhuming.jwt.exception.NotMatchTokenException;
import com.itcontest.skhuming.member.exception.InvalidMemberException;
import com.itcontest.skhuming.member.exception.NotFoundMemberException;
import com.itcontest.skhuming.mileage.exception.ExistsMemberHistoryMileageException;
import com.itcontest.skhuming.mileage.exception.NotFoundMileageException;
import com.itcontest.skhuming.notice.exception.NotFoundNoticeException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({
            InvalidMemberException.class,
            InvalidEmailAddressException.class,
            NotFoundMemberException.class,
            NotFoundNoticeException.class,
            NotFoundMileageException.class,
            NotMatchTokenException.class,
            ExistsMemberHistoryMileageException.class
    })
    public ResponseEntity<ErrorResponse> handleInvalidate(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
