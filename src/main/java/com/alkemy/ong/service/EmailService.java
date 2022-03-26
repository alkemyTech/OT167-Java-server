package com.alkemy.ong.service;

import com.alkemy.ong.model.User;

public interface EmailService {
    
    void sendWelcomeEmailTo(User user, Long id);
    
}
