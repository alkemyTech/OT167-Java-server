package com.alkemy.ong.auth.controller;

import com.alkemy.ong.auth.dto.UserRequestDTO;
import com.alkemy.ong.auth.model.UserEntity;
import com.alkemy.ong.auth.repository.UserRepository;
import com.alkemy.ong.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    UserDetailsCustomService userDetailsCustomService;

    @PostMapping("/login")
    //recive un mail y una contraseña, y devuelve un jwt
    public ResponseEntity<UserEntity> singIn(@Valid @RequestBody UserRequestDTO userRequest) throws Exception {
        /*toma el mail de usuario y contraseña y los autentica, UserDetails es una libreria de spring*/
        UserEntity userEntity = userDetailsCustomService.authenticateUser(userRequest);

        return ResponseEntity.ok(userEntity);
    }
}
