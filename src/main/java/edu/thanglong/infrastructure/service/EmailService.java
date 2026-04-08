package edu.thanglong.infrastructure.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    public void sendOtp(String toEmail, String otp, String subject) {
        if (mailSender == null) {
            log.error("JavaMailSender chưa được cấu hình");
            throw new RuntimeException("Không thể gửi email, vui lòng thử lại sau");
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText("Mã OTP của bạn là: " + otp
                + "\nMã có hiệu lực trong 5 phút."
                + "\nVui lòng không chia sẻ mã này với ai.");
            mailSender.send(message);
            log.info("Gửi OTP thành công tới: {}", toEmail);
        } catch (Exception e) {
            // Log lỗi gốc đầy đủ để debug
            log.error("Gửi email thất bại tới {} — Nguyên nhân: {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Không thể gửi email, vui lòng thử lại sau");
        }
    }
}