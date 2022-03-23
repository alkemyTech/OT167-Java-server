package com.alkemy.ong.security.controller;

import com.alkemy.ong.security.mapper.UserMapper;
import com.alkemy.ong.security.service.JwtUtils;
import com.alkemy.ong.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<?> userData(HttpServletRequest request){
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        return ResponseEntity.ok(userMapper.convertUserToDto(userServiceImpl.findByUsername(username)));

    }
}
