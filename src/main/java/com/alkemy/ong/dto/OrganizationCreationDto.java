package com.alkemy.ong.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class OrganizationCreationDto {

    @Schema(name = "name",example = "Somos Más ONG",type = "String",description = "description : name of the organization")
    private String name;

    @Schema(name = "image",example = "url.com/image.jpg", type = "String",description = "description : image of the organization")
    private String image;

    @Schema(name = "address",example = "addressOrganization", type = "String",description = "description : address of the organization")
    private String address;

    @Schema(name = "phone",example = "2604014516", type = "String",description = "description : phone of the organization")
    private String phone;

    @Schema(name = "email",example = "organization@mail.com", type = "String",description = "description : email of the organization")
    private String email;

    @Schema(name = "instagramUrl",example = "instagram.com/organization", type = "String",description = "description : Organization instagram links")
    private String instagramUrl;

    @Schema(name = "facebookUrl",example = "facebook.com/organization", type = "String",description = "description : Organization facebook links")
    private String facebookUrl;

    @Schema(name = "linkedInUrl",example = "linkedin.com/organization", type = "String",description = "description : Organization linkedin links")
    private String linkedInUrl;

    @Schema(name = "welcomeText",example = "welcomeOrganization", type = "String",description = "description : welcome text")
    private String welcomeText;

    @Schema(name = "aboutUsText",example = "aboutUsOrganization", type = "String",description = "description : text about us")
    private String aboutUsText;
}