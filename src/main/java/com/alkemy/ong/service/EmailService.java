package com.alkemy.ong.service;

import com.sendgrid.Response;
import com.alkemy.ong.model.User;

public interface EmailService {
    
    void sendWelcomeEmailTo(User user);

    Response sendEmail(String subject, String recipentEmail, String message);    
}
