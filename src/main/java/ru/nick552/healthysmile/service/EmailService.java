package ru.nick552.healthysmile.service;

public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
