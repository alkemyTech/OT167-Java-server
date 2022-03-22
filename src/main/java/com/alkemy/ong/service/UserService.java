package com.alkemy.ong.service;

<<<<<<< HEAD
import java.io.IOException;
import com.alkemy.ong.dto.UserRegisterRequest;
import com.alkemy.ong.dto.UserRegisterResponse;
import com.alkemy.ong.exception.DataAlreadyExistException;


public interface UserService {

    User findByEmail(String email);
    UserRegisterResponse register(UserRegisterRequest userReq) throws DataAlreadyExistException, IOException;
     


=======
import org.springframework.beans.factory.annotation.Autowired;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername();
    }
>>>>>>> develop
}
