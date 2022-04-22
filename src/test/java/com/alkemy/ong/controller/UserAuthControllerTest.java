package com.alkemy.ong.controller;

import com.alkemy.ong.controller.utils.AuthBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.NotFoundException;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class UserAuthControllerTest extends AuthBasic {

    /*---------------------------------------------------------------------------
                                /REGISTER TESTS
    ---------------------------------------------------------------------------*/
    @Test
    public void successfulRegistration() throws Exception {
        Mockito.when(userAuthService.register(request)).thenReturn(response);
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void emailAlreadyExist() throws Exception {
        request.setEmail("leonardo@gmail.com");
        Mockito.when(userAuthService.register(any())).thenThrow(new DataAlreadyExistException("The user already esist in our Data Base."));
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotAcceptable());
    }
    
    @Test
    public void dataCannotBeBlank() throws Exception {
        request.setEmail(null);
        Mockito.when(userAuthService.register(request)).thenReturn(null);
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    /*---------------------------------------------------------------------------
                                /LOGIN TESTS
    ---------------------------------------------------------------------------*/
    @Test
    public void successfulLogin() throws Exception {
        loginRequest.setEmail(user.getEmail());
        loginRequest.setPassword(user.getPassword());
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void loginEmailNotFoundException() throws Exception {
        Mockito.when(userAuthService.logIn(any())).thenThrow(new NotFoundException("The user is not registered."));
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void loginInvalidPassword() throws Exception {
        loginRequest.setEmail(user.getEmail());
        loginRequest.setPassword("wrongPassword");
        Mockito.when(userAuthService.logIn(any())).thenThrow(new NotFoundException("The password is wrong"));
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isNotFound());

    }

    /*---------------------------------------------------------------------------
                                /ME TESTS
    ---------------------------------------------------------------------------*/
    @Test
    public void unsuccessfulUserDataFetching() throws Exception {
        mockMvc.perform(get("/auth/me")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void successfulUserDataFetching() throws Exception {
        mockMvc.perform(get("/auth/me")
                .contentType(APPLICATION_JSON)
                .with(user("user").roles("USER"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

}
