package com.alkemy.ong.service;

import java.io.IOException;
import com.alkemy.ong.dto.UserRegisterRequest;
import com.alkemy.ong.dto.UserRegisterResponse;
import com.alkemy.ong.exception.dataAlreadyExistException;
import com.alkemy.ong.model.User;


public interface UserService {

    User findByUsername(String username);
    User findByEmail(String email);
    UserRegisterResponse register(UserRegisterRequest userReq) throws dataAlreadyExistException, IOException;
    

}