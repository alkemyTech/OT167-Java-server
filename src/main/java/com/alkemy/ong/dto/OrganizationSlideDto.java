package com.alkemy.ong.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class OrganizationSlideDto {

    @Schema(name = "name",example = "",type = "String",description = "description : name of the organization")
    private String name;

    @Schema(name = "image",example = "url.com/image.jpg", type = "String",description = "description : image of the organization")
    private String image;

    @Schema(name = "address",example = "", type = "String",description = "description : address of the organization")
    private String address;

    @Schema(name = "phone",example = "", type = "String",description = "description : phone of the organization")
    private String phone;

    @Schema(name = "instagramUrl",example = "", type = "String",description = "description : Organization instagram links")
    private String instagramUrl;

    @Schema(name = "facebookUrl",example = "", type = "String",description = "description : Organization facebook links")
    private String facebookUrl;

    @Schema(name = "linkedInUrl",example = "", type = "String",description = "description : Organization linkedin links")
    private String linkedInUrl;

    @Schema(name = "listSlide",example = "", type = "List",description = "description : List of slide")
    private List<SlideDto> listSlide;
    
}
