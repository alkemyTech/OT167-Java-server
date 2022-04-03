package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter @Setter
public class UrlOrganizationDto {

    private Long id;
    private String linkedInUrl;
    private String facebookUrl;
    private String instagramUrl;


}
