package com.itcontest.skhuming;

import com.itcontest.skhuming.email.application.ConsoleEmailService;
import com.itcontest.skhuming.email.application.EmailService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public EmailService emailService() {
        return new ConsoleEmailService();
    }
}
