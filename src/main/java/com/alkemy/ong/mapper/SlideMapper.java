package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.SlideBasicDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideUpdateDto;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SlideMapper {

    @Autowired
    private SlideService slideService;
    
    @Autowired
    private OrganizationService organizationService;

    public SlideDto slideToDTO(Slide slide) {

        SlideDto slideDto = new SlideDto();
        slideDto.setId(slide.getId());
        slideDto.setImageUrl(slide.getImageUrl());
        slideDto.setOrder(slide.getOrder());
        slideDto.setOrganization_id(slide.getOrganization().getId());
        slideDto.setText(slide.getText());

        return slideDto;
    }

    public Slide slideDtoToModel(SlideDto slideDto) {

        Slide slide = new Slide();
        slide.setImageUrl(slideDto.getImageUrl());
        slide.setOrder(slideDto.getOrder());
        slide.setOrganization(organizationService.findOrganization());
        slide.setText(slideDto.getText());

        return slide;
    }

    public List<SlideDto> listActivityDto(List<Slide> listSlide) {

        return listSlide.stream()
                .map(slide -> slideToDTO(slide))
                .collect(Collectors.toList());
    }

    public Slide updateSlade(Slide slide, SlideUpdateDto slideUpdate) {
        slide.setOrder(slideUpdate.getOrder());
        slide.setImageUrl(slideUpdate.getImageUrl());
        slide.setText(slideUpdate.getText());
        slideService.setOrgInSlide(slide.getId(), slideUpdate.getOrgName());
        return slide;

    }

    public List<SlideBasicDto> slideBasicEntityList2DtoList(List<Slide> listEntity) {
        return listEntity.stream()
                .map(slide -> slideBasicEntity2Dto(slide))
                .collect(Collectors.toList());
    }

    private SlideBasicDto slideBasicEntity2Dto(Slide ent) {
        SlideBasicDto dto = new SlideBasicDto();
        dto.setImageUrl(ent.getImageUrl());
        dto.setOrder(ent.getOrder());
        return dto;
    }

}
