package com.alkemy.ong.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class OrganizationSlideDto {
    
    private String name;
    private String image;
    private String address;
    private String phone;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    private List<SlideDto> listSlide;
    
}
