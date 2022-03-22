package com.alkemy.ong.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@SQLDelete(sql = "UPDATE organizations SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Image cannot be null")
    private String image;

    @Nullable
    private String address;

    @Nullable
    private Integer phone;

    @NotNull(message = "email cannot be null")
    private String email;

    @NotNull(message = "Welcome text cannot be null")
    @Column(name = "welcome")
    private String welcomeText;

    @Nullable
    @Column(name = "about_us")
    private String aboutUsText;

    @CreationTimestamp
    @Column(name = "creation_date")
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate updateDate;

    @Column(name = "deleted")
    private boolean softDeleted = Boolean.FALSE;

}
