package com.itcontest.skhuming.global.error;

import com.itcontest.skhuming.email.exception.InvalidEmailAddressException;
import com.itcontest.skhuming.global.error.dto.ErrorResponse;
import com.itcontest.skhuming.global.jwt.exception.NotMatchTokenException;
import com.itcontest.skhuming.member.exception.InvalidMemberException;
import com.itcontest.skhuming.member.exception.NotFoundDepartmentException;
import com.itcontest.skhuming.member.exception.NotFoundMemberException;
import com.itcontest.skhuming.mileage.exception.ExistsMemberHistoryMileageException;
import com.itcontest.skhuming.mileage.exception.NotAddMileageException;
import com.itcontest.skhuming.mileage.exception.NotFoundMileageException;
import com.itcontest.skhuming.notice.exception.NotFoundNoticeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({
            InvalidMemberException.class,
            InvalidEmailAddressException.class,
            InvalidEmailAddressException.class,
            NotFoundMemberException.class,
            NotFoundNoticeException.class,
            NotFoundMileageException.class,
            NotFoundDepartmentException.class,
            NotMatchTokenException.class,
            NotAddMileageException.class,
            ExistsMemberHistoryMileageException.class,
    })
    public ResponseEntity<ErrorResponse> handleInvalidData(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
