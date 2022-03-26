package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "contacts")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE contacts SET deleted_at = true WHERE id=?")
@Where(clause = "deleted_at = false")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contact")
    private Long id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Phone cannot be null")
    private String phone;

    @Email(message = "Email format error")
    @NotNull(message = "Email cannot be null")
    private String email;

    private String message;

    @Column(name = "deleted_at")
    private boolean deletedAt = Boolean.FALSE;

}
