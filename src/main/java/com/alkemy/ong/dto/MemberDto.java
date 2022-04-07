package com.alkemy.ong.dto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberDto {
        private Long id;
        private String name;
        private String facebookUrl;
        private String instagramUrl;
        private String linkedinUrl;
        private String image;
        private String description;
        private LocalDateTime creationDate;
        private LocalDateTime updateDate;
}
