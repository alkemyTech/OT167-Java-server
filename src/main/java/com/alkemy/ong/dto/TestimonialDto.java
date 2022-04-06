package com.alkemy.ong.dto;

import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class TestimonialDto {

    private Long id;

    @NotNull(message = "The name cannot be empty")
    private String name;
    
    private String image;
    
    @NotNull(message = "The content cannot be empty")
    private String content;
}
