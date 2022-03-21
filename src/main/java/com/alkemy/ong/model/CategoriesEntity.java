package com.alkemy.ong.model;


import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import javax.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//chequear que esta bien cuando se realize softDelete de una categoria
@SQLDelete(sql = "UPDATE categories SET deleted = true WHERE id_categories=?")
@Where(clause = "deleted=false")
public class CategoriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categories")
    private Long idCategories;

    @NotNull(message = "the name cannot be empty")
    private String name;

    @NotNull(message = "the description cannot be empty")
    private String description;

    @NotNull(message = "the image cannto be empty")
    private String image;

    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(name = "creation_date",updatable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDate creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDate updateDate;
}
