package com.alkemy.ong.service;

import com.alkemy.ong.security.model.UserEntity;
import com.sendgrid.Response;

public interface EmailService {
    
    void sendWelcomeEmailTo(UserEntity user);

    Response sendEmail(String subject, String recipentEmail, String message);    
}
