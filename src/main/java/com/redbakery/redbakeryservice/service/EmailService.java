package com.redbakery.redbakeryservice.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String sendTo, String subject, String message);

    void sendEmailWithAttachment(String sendTo, String subject, String message, String attachmentPath) throws MessagingException;
}
