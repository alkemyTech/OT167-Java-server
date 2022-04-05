package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.model.Slide;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SlideMapper {

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
//        slide.setOrganization(organization);
        slide.setText(slideDto.getText());

        return slide;
    }

    public List<SlideDto> listActivityDto(List<Slide> listSlide) {

        return listSlide.stream()
                .map(slide -> slideToDTO(slide))
                .collect(Collectors.toList());
    }
}
