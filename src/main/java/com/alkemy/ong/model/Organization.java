package com.alkemy.ong.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.validation.constraints.Email;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "organizations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "name cant be null")
    private String name;
    @NotNull(message = "image cant be null")
    private String image;
    private String address;
    private int phone;
    @NotNull(message = "email cant be null")
    @Email(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message="No cumple con los requisitos de email valido")
    private String email;
    @NotNull(message = "welcome text cant be null")
    private String welcomeText;
    private String aboutUsText;
    @CreationTimestamp
//    @JsonFormat(pattern="dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @UpdateTimestamp
    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "update_date")
    private LocalDate updateDate;
}
