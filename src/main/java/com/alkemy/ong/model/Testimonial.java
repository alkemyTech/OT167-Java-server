package com.alkemy.ong.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "testimonials")
@Data
@SQLDelete(sql = "UPDATE testimonials SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Testimonial implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @NotNull(message = "The name cannot be empty")
    private String name;
    @Nullable
    private String image;
    @Nullable
    private String content;
    
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "creation_date")
    private LocalDate creationDate;
    
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "update_date")
    private LocalDate updateDate;
    
    private boolean deleted = Boolean.FALSE;
    
}
