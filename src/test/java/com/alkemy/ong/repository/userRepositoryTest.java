package com.alkemy.ong.repository;

import com.alkemy.ong.security.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class userRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }
    @Test
    void existByEmail() {
        UserEntity user = new UserEntity("Andres", "Rodriugez", "andresRod@gmail.com", "123456", "urlfoto", null);
        underTest.save(user);
        String email = "andresRod@gmail.com";
        UserEntity result = underTest.findByEmail(email);
        log.info("RESULT: a user was found with the email: " + result.getEmail());
        assertThat(result != null).isTrue();
    }
    @Test
    void notExistByEmail() {
        String email = "cualquierEmail@gmail.com";
        UserEntity result = underTest.findByEmail(email);
        log.info("RESULT: the resul is a null (" + result + ")");
        assertThat(result != null).isFalse();
    }

}
