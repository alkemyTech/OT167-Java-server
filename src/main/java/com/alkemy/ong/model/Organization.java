package com.alkemy.ong.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "organization")
@Getter
@Setter
@SQLDelete(sql = "UPDATE organization SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String image;

    @Nullable
    private String address;

    @Nullable
    private Integer phone;

    @NotNull
    private String email;

    @NotNull
    @Column(name = "welcome")
    private String welcomeText;

    @Nullable
    @Column(name = "about_us")
    private String aboutUsText;

    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @Column(name = "update_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateDate;

    @Column(name = "deleted")
    private boolean softDeleted = Boolean.FALSE;

}
