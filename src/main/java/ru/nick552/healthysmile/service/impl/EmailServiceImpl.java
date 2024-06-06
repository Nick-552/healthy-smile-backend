package ru.nick552.healthysmile.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.nick552.healthysmile.service.EmailService;

@Service
@Log4j2
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final String from;

    public EmailServiceImpl(JavaMailSender javaMailSender, @Value("${spring.mail.username}")String from) {
        this.javaMailSender = javaMailSender;
        this.from = from;
    }

    @Override
    @Async
    public void sendEmail(String to, String subject, String body) {
        log.info("Sending email to: {}, subject: {}, body: {}", to, subject, body);
        try {
            var message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            log.info("Email message created: {}", message);
            javaMailSender.send(message);
            log.info("Email sent successfully");
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
        }
    }
}
