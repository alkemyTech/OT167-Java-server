package com.alkemy.ong.model;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

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

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String image;

    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateDate;
}
