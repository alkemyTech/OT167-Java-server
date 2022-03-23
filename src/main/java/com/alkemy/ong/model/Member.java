package com.alkemy.ong.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@SQLDelete(sql = "UPDATE members SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_member")
    private Long idMember;

    @NotNull(message = "Name cannot be null")
    private String name;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @NotNull(message = "Image cannot be null")
    private String image;

    private String description;

    @CreationTimestamp  
    @Column(name = "date_created")
    private LocalDate dateCreated;

    @UpdateTimestamp
    @Column(name = "date_modified")
    private LocalDate dateModified;

    private Boolean deleted = Boolean.FALSE;

   
}
