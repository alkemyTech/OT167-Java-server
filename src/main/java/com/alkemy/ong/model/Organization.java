package com.alkemy.ong.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;


@Entity
@Table(name = "organizations")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE organizations SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "cannot be empty")
    @NotNull(message = "cannot be null")
    private String name;

    @NotEmpty(message = "cannot be empty")
    @NotNull(message = "cannot be null")
    private String image;

    @NotEmpty(message = "cannot be empty")
    @Nullable
    private String address;

    @NotEmpty(message = "cannot be empty")
    @Pattern(regexp = "^(?:(?:00)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$", message = "number no valid")
    @NotNull(message = "cannot be null")
    private String phone;

    @NotEmpty(message = "cannot be empty")
    @Email(regexp = "^[\\w!#$%&'+/=?`{|}~^-]+(?:\\.[\\w!#$%&'+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message="Email format error")
    @NotNull(message = "cannot be null")
    private String email;

    @NotNull(message = "cannot be null")
    @Column(name = "welcome")
    private String welcomeText;

    @Nullable
    @Column(name = "about_us")
    private String aboutUsText;

    @CreationTimestamp
    @Column(name = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updateDate;

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