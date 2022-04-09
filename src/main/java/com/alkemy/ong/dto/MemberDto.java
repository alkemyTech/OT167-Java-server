package com.alkemy.ong.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberDto {
        private Long id;
        @Schema(nullable = false, description = "name: cannot allow null")
        private String name;
        private String facebookUrl;
        private String instagramUrl;
        private String linkedinUrl;
        @Schema(nullable = false, description = "image: cannot allow null")
        private String image;
        private String description;
        private LocalDateTime creationDate;
        private LocalDateTime updateDate;
}
