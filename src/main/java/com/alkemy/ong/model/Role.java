package com.alkemy.ong.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter @Setter
    private Long id;

    @Column(name = "name")
    @Getter @Setter
    @NotNull(message = "name can't be null")
    private String name;

    @Column(name = "description")
    @Getter @Setter
    @Nullable
    private String description;

    @Column(name = "CreatedDate", updatable=false)
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Getter @Setter
    private LocalDateTime createdDate;

    @Column(name = "ModifiedDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @UpdateTimestamp
    @Getter @Setter
    private LocalDateTime modifiedDate;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
