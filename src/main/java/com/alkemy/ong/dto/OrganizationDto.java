package com.alkemy.ong.dto;
import lombok.*;

@RequiredArgsConstructor
@Getter @Setter
public class OrganizationDto{
    private String name;
    private String image;
    private String address;
    private String phone;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
}
