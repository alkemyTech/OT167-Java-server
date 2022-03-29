package com.alkemy.ong.dto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class OrganizationCreationDto {

    private String name;
    private String image;
    private String address;
    private int phone;
    private String email;
    private String instagramUrl;
    private String facebookUrl;
    private String linkedInUrl;
    private String welcomeText;
    private String aboutUsText;
}
