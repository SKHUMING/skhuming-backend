package com.itcontest.skhuming.email.application;

import com.itcontest.skhuming.email.exception.InvalidEmailAddressException;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import com.itcontest.skhuming.member.exception.InvalidMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

@Profile("prod")
@Transactional
@RequiredArgsConstructor
@Service
public class ProdEmailService implements EmailService {

    private static final String EMAIL_FORMAT = "^[a-zA-Z0-9._%+-]+@office\\.skhu\\.ac\\.kr$";

    private final JavaMailSender emailSender;
    private String authNum; // 인증 번호

    private final MemberRepository memberRepository;

    // 인증번호 6자리 무작위 생성
    private void createCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder key = new StringBuilder();

        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = characters.length();

        for (int i = 0; i < 6; i++) {
            int idx = random.nextInt(length);
            key.append(characters.charAt(idx));
        }

        authNum = key.toString();
    }

    // 메일 양식 작성
    private MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        createCode();
        String setFrom = "skhuming"; // 보내는 사람
        String toEmail = email;
        String title = "Skhuming 재학생 인증 메일";// 제목

        validateDuplicateEmail(email);

        boolean isMatch = toEmail.matches(EMAIL_FORMAT);
        validateEmail(isMatch);

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(title);

        // 메일 내용
        String msgOfEmail = mailContents();

        message.setFrom(setFrom);
        message.setText(msgOfEmail, "utf-8", "html");

        return message;
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new InvalidMemberException("이미 사용중인 이메일 입니다.");
        }
    }

    private void validateEmail(boolean isMatch) {
        if (!isMatch) {
            throw new InvalidEmailAddressException();
        }
    }

    private String mailContents() {
        return "<div style='margin:20px;'>" +
                "<h1> 👋🏻 Skhuming 재학생 인증 메일 </h1><br>" +
                "<p>Skhuming은 성공회대학교 학생만 사용할 수 있는 서비스로, </p>" +
                "<p>성공회대 office 365 메일로 재학생 인증 후 사용하실 수 있습니다. </p><br>" +
                "<p>아래의 코드를 인증 코드란에 적고 재학생 인증을 마쳐주세요.<p><br>" +
                "<div align='center' style='border:1px solid black; font-family:verdana';>" +
                "<div style='font-size:130%'>" +
                "<strong><br>" +
                authNum +
                "</strong><div><br/> " +
                "</div>";
    }

    @Override
    public String sendEmail(String email) throws MessagingException, UnsupportedEncodingException {

        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(email);
        //실제 메일 전송
        emailSender.send(emailForm);

        return authNum; //인증 코드 반환
    }
}
