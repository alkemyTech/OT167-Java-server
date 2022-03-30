package com.alkemy.ong.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE organizations SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    @NotNull(message = "Name cannot be null")
    private String name;

    @NotEmpty(message = "Name cannot be empty")
    @NotNull(message = "Image cannot be null")
    private String image;

    @NotEmpty(message = "Name cannot be empty")
    @Nullable
    private String address;

    @NotEmpty(message = "Name cannot be empty")
    @Nullable
    private Integer phone;

    @NotEmpty(message = "Name cannot be empty")
    @NotNull(message = "email cannot be null")
    private String email;

    @NotNull(message = "Welcome text cannot be null")
    @Column(name = "welcome")
    private String welcomeText;

    @Nullable
    private String linkedInUrl;

    @Nullable
    private String facebookUrl;

    @Nullable
    private String instagramUrl;

    @Nullable
    @Column(name = "about_us")
    private String aboutUsText;

    @CreationTimestamp
    @Column(name = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDate creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDate updateDate;

    @Column(name = "deleted")
    private boolean softDeleted = Boolean.FALSE;

}
