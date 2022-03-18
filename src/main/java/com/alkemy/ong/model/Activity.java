package com.alkemy.ong.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "activities")
@Data @NoArgsConstructor @AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String content;
    @NotNull
    private String image;
    @CreationTimestamp
    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @UpdateTimestamp
    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "update_date")
    private LocalDate updateDate;
}
