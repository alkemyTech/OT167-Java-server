package com.alkemy.ong.service;

import java.io.IOException;
import com.alkemy.ong.dto.UserRegisterRequest;
import com.alkemy.ong.dto.UserRegisterResponse;
import com.alkemy.ong.exception.DataAlreadyExistException;


public interface UserService {

    User findByEmail(String email);
    UserRegisterResponse register(UserRegisterRequest userReq) throws DataAlreadyExistException, IOException;
     


}
