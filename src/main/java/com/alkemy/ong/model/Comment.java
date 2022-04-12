package com.alkemy.ong.model;

import com.alkemy.ong.security.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE comments SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user_id;

    @NotBlank(message = "Body cannot be empty")
    @NotNull(message = "Body cannot be null")
    private String body;


    @ManyToOne(fetch = FetchType.EAGER)
    private News news_id;

    private Boolean deleted = Boolean.FALSE;

}
