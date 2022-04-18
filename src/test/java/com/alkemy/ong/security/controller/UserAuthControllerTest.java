package com.alkemy.ong.security.controller;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.UserDtoCreator;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.security.dto.UserRegisterResponse;
import com.alkemy.ong.security.mapper.UserMapper;
import com.alkemy.ong.security.model.UserEntity;
import com.alkemy.ong.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "")
@RunWith(MockitoJUnitRunner.Silent.class)
class
UserAuthControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Mock
    private MessageSource messageSource;
    protected MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    UserMapper userMapper;
    private UserEntity userEntity;
    private UserDtoCreator userDtoCreator;
    private UserDto userDto;
    private UserRegisterResponse userRegisterResponse;
    List<UserEntity> userEntityList = new ArrayList<>();
    List<UserDto> userDtoList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        UserEntity user = new UserEntity(Long.valueOf(1), false, "Andres", "Rodriguez", "andres@Email", "123456", "photo", null, null, null);
        UserDto userDto = userMapper.convertUserToDto(user);
        userEntityList.add(user);
        userDtoList.add(userDto);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
        void getAllUsersD()  throws Exception {
        when(userService.getAllUsers()).thenReturn(userDtoList);
        mockMvc.perform(get("/auth/users")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }
    @Test
    void getAllUserDfailNotAuthenticated() throws Exception {
        when(userService.getAllUsers()).thenReturn(userDtoList);
        mockMvc.perform(get("/auth/users")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
    @Test
    void whenNotFoundUserById() throws Exception {
        Long userId = 1L;
        given(userService.findUserById(userId)).willThrow(new NotFoundException(""));
        this.mockMvc.perform(get("/auth/users/{id}", userId))
                .andExpect(status().isNotFound());
    }
    @Test
    void findUserById() throws Exception {
        Long userId = 1L;
        UserEntity user = new UserEntity(userId, false, "Andres", "Rodriguez", "andres@Email", "123456", "photo", null, null, null);
        given(userService.findUserById(userId)).willReturn(Optional.of(user));
        this.mockMvc.perform(get("/auth/users/{id}", userId))
                .andExpect(status().isOk());
    }
}
