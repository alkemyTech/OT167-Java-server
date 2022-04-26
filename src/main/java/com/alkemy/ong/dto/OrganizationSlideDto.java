package com.alkemy.ong.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class OrganizationSlideDto {

    @Schema(name = "name", example = "Somos Más ONG", type = "String", description = "description : name of the organization")
    private String name;

    @Schema(name = "image", example = "url.com/image.jpg", type = "String", description = "description : image of the organization")
    private String image;

    @Schema(name = "address", example = "addressOrganization", type = "String", description = "description : address of the organization")
    private String address;

    @Schema(name = "phone", example = "15879658", type = "String", description = "description : phone of the organization")
    private String phone;

    @Schema(name = "instagramUrl", example = "instagram.com/organization", type = "String", description = "description : Organization instagram links")
    private String instagramUrl;

    @Schema(name = "facebookUrl", example = "facebook.com/organization", type = "String", description = "description : Organization facebook links")
    private String facebookUrl;

    @Schema(name = "linkedInUrl", example = "linkedin.com/organization", type = "String", description = "description : Organization linkedin links")
    private String linkedInUrl;

    @Schema(name = "listSlide", example = " [\n"+ "{\n"+ "\"imageUrl\": \"url.com/image.jpg\",\n"+ "\"text\": \"Welcome to the web side!\",\n"+ "\"order\": 1\n"+ "}\n"+ "]", type = "String", description = "description : List of slide")
    private List<SlideDto> listSlide;

}
