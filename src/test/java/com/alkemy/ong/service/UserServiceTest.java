package com.alkemy.ong.service;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.security.model.UserEntity;
import com.alkemy.ong.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
class UserServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserServiceImpl underTest1;

    @Mock
    private MessageSource messageSource;

    Role role = new Role("ROLE_ADMIN", "descrption");
    UserEntity user = new UserEntity(Long.valueOf(1), false, "Andres", "Rodriguez", "andres@Email", "123456", "photo", null, null, null);
    @Test
    void getUsers() {
            underTest1.getUsers();
            verify(userRepository).findAll();
        }
    @Test
    void getAllUsers() {
        underTest1.getAllUsers();
        verify(userRepository).findAll();
    }
    @Test
    void getUserByEmail() {
        userRepository.save(user);
        underTest1.findByEmail(user.getEmail());
        verify(userRepository).findByEmail(user.getEmail());
    }
    @Test
    void notFoundUserById(){
        userRepository.save(user);
        underTest1.findUserById(44L);
        verify(userRepository).findById(44L);

    }
    @Test
    void foundUserById(){
        userRepository.save(user);
        underTest1.findUserById(user.getId());
        verify(userRepository).findById(user.getId());
    }
    @Test
    void findById() {
        userRepository.save(user);
        underTest1.findById(user.getId());
        verify(userRepository).findById(user.getId());
    }
    @Test
    void deleteUser() {
            Optional<UserEntity> optionalEntityType = Optional.of(user);
            Mockito.when(userRepository.findById(1L)).thenReturn(optionalEntityType);
            underTest1.delete(user.getId());
            Mockito.verify(userRepository, times(0)).delete(user);
    }
    @Test
    void getRolByName() {
        roleRepository.save(role);
        underTest1.getRole(role.getName());
        verify(roleRepository).findByName(role.getName());
    }
}


