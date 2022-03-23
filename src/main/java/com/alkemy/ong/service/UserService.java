package com.alkemy.ong.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.stereotype.Service;

public interface UserService {

    User findByUsername(String username);

    User findByEmail(User user);
}
