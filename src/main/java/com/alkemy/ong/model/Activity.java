package com.alkemy.ong.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "activities")
@Data @NoArgsConstructor @AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @Column(nullable = false)
    private String name;
    @NotNull @Column(nullable = false)
    private String content;
    @NotNull @Column(nullable = false)
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
