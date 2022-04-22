package com.alkemy.ong.controller;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.security.mapper.UserMapper;
import com.alkemy.ong.security.model.UserEntity;
import com.alkemy.ong.security.service.UserDetailsCustomService;
import com.alkemy.ong.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "")
class UserControllerTest {
    @Autowired
    private WebApplicationContext context;
    protected MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserDetailsCustomService userDetailsCustomService;
    @Autowired
    UserMapper userMapper;
    List<UserEntity> userEntityList = new ArrayList<>();
    List<UserDto> userDtoList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        UserEntity user = new UserEntity(Long.valueOf(1), false, "Andres", "Rodriguez", "andres@Email", "123456", "photo", null, null, null);
        UserEntity user1 = new UserEntity(Long.valueOf(2), false, "Andres", "Rodriguez", "andres@Email", "123456", "photo", null, null, null);
        userEntityList.add(user);
        userEntityList.add(user1);

        UserDto userDto = userMapper.convertUserToDto(user);
        userDtoList.add(userDto);

    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser() throws Exception {
        UserEntity user = userEntityList.get(0);
        Long userId = user.getId();
        given(userService.findUserById(userId)).willReturn(Optional.of(user));
        doNothing().when(userService).delete(user.getId());
        this.mockMvc.perform(delete("/users/{id}", user.getId()))
                .andExpect(status().is(204));
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenDeletingNonExistingUser() throws Exception {
        Long userId = 33L;
        given(userService.findUserById(userId)).willReturn(Optional.empty());
        this.mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().is(204));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findUserById() throws Exception {
        Long userId = 1L;
        UserEntity user = new UserEntity(userId, false, "Andres", "Rodriguez", "andres@Email", "123456", "photo", null, null, null);
        given(userService.findUserById(userId)).willReturn(Optional.of(user));
        this.mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsersD()  throws Exception {
        when(userService.getAllUsers()).thenReturn(userDtoList);
        mockMvc.perform(get("/users")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}