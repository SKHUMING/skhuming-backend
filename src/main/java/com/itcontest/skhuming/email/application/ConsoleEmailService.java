package com.itcontest.skhuming.email.application;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Profile("local")
@Service
public class ConsoleEmailService implements EmailService {

    @Override
    public String sendEmail(String email) throws MessagingException, UnsupportedEncodingException {

        // TODO(아무것도 안할거임!)

        return "LOCAL_CODE";
    }
}
