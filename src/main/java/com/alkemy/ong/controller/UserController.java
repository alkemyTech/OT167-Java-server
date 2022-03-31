package com.alkemy.ong.controller;

import com.alkemy.ong.service.UserService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @DeleteMapping("/{id}")
    public ResponseEntity<TypeResolutionContext.Empty> delete(@PathVariable Long id) throws EntityNotFoundException {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
