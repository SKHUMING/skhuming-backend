package com.itcontest.skhuming.email.application;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    String sendEmail(String email) throws MessagingException, UnsupportedEncodingException;
}
