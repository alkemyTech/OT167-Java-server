package com.alkemy.ong.auth.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
@Getter @Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idUser;

    @NotNull(message = "user mail cannot be empty")
    private String mail;

    @NotNull(message = "password cannot be empty")
    private String password;
}
