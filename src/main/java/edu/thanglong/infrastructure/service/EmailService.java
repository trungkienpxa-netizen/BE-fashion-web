package edu.thanglong.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOtp(String toEmail, String otp, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText("Mã OTP của bạn là: " + otp + "\nMã có hiệu lực trong 5 phút. Vui lòng không chia sẻ mã này với ai.");
        mailSender.send(message);
    }
}