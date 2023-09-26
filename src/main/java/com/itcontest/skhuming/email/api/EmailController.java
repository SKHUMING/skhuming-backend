package com.itcontest.skhuming.email.api;

import com.itcontest.skhuming.email.api.request.EmailCheckReq;
import com.itcontest.skhuming.email.application.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Operation(
            summary = "이메일 인증",
            description = "이메일 인증 코드 전송",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @PostMapping("/api/email-check")
    public ResponseEntity<String> emailCheck(@RequestBody EmailCheckReq emailCheckReq) throws MessagingException, UnsupportedEncodingException {
        String authCode = emailService.sendEmail(emailCheckReq.getEmail());
        return new ResponseEntity<>(authCode, HttpStatus.OK);
    }
}