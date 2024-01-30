package com.redbakery.redbakeryservice.service;

public interface EmailService {
    void sendEmail(String sendTo, String subject, String message);
}
