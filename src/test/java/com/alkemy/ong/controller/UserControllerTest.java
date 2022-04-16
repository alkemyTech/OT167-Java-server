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
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        UserEntity user = new UserEntity(Long.valueOf(1), false, "Andres", "Rodriguez", "andres@Email", "123456", "photo", null, null, null);
        UserEntity user1 = new UserEntity(Long.valueOf(2), false, "Andres", "Rodriguez", "andres@Email", "123456", "photo", null, null, null);
        userEntityList.add(user);
        userEntityList.add(user1);

    }
    @Test
    void deleteUser() throws Exception {
        UserEntity user = userEntityList.get(0);
        Long userId = user.getId();
        given(userService.findUserById(userId)).willReturn(Optional.of(user));
        doNothing().when(userService).delete(user.getId());
        this.mockMvc.perform(delete("/users/{id}", user.getId()))
                .andExpect(status().is(204));
    }
    @Test
    void shouldReturn404WhenDeletingNonExistingUser() throws Exception {
        Long userId = 33L;
        given(userService.findUserById(userId)).willReturn(Optional.empty());
        this.mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().is(204));
    }
}