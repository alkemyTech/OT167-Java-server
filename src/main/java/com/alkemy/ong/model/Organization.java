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
    private String name;

    @NotEmpty(message = "Image cannot be empty")
    private String image;

    @NotEmpty(message = "Address cannot be empty")
    private String address;

    @NotEmpty(message = "Phone cannot be empty")
    private String phone;

    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotNull(message = "Welcome text cannot be null")
    @Column(name = "welcome")
    private String welcomeText;

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
    
    @Column(name = "facebook_url")
    @Nullable
    private String facebookUrl;

    @Column(name = "instagram_url")
    @Nullable
    private String instagramUrl;

    @Column(name = "linkedin_url")
    @Nullable
    private String linkedInUrl;

}
