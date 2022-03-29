package com.alkemy.ong.service;

import com.sendgrid.Response;

public interface EmailService {
    
    void sendWelcomeEmailTo(String to);
    Response sendEmail(String subject, String recipentEmail, String message);
    
}
