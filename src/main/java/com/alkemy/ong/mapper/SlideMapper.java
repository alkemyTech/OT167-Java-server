package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideUpdateDto;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class SlideMapper {
  
    @Autowired
    private SlideService slideService;

    //public SlideDto slideToDTO(Slide slide) {

    //    SlideDto slideDto = new SlideDto();
    //    slideDto.setId(slide.getId());
    //   slideDto.setImageUrl(slide.getImageUrl());
    //   slideDto.setOrder(slide.getOrder());
    //    slideDto.setOrganization_id(slide.getOrganization().getId());
    //    slideDto.setText(slide.getText());

    //   return slideDto;
   // }

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

    public SlideDto slideToDto (Slide slide){
        SlideDto slideDto = new SlideDto();
        slideDto.setId(slide.getId());
        slideDto.setOrder(slide.getOrder());
        slideDto.setImageUrl(slide.getImageUrl());
        slideDto.setText(slide.getText());
        slideDto.setOrganization(slide.getOrganization());
        return slideDto;
    }
  
    public List<SlideDto> sliceListToDtoList (List<Slide> sliceList){
        List<SlideDto> sliceListDto = new ArrayList<>();
        sliceList.forEach(s -> sliceListDto.add(slideToDto(s)));
        return sliceListDto;
    }
  
    public Slide creationSlide(SlideDto slideDto) {
        Slide slide = new Slide();
        slide.setId(slideDto.getId());
        slide.setOrder(slideDto.getOrder());
        slide.setImageUrl(slideDto.getImageUrl());
        slide.setText(slideDto.getText());
        slide.setOrganization(slideDto.getOrganization());
        return slide;
    }
  
    public Slide updateSlade(Slide slide, SlideUpdateDto slideUpdate){
        slide.setOrder(slideUpdate.getOrder());
        slide.setImageUrl(slideUpdate.getImageUrl());
        slide.setText(slideUpdate.getText());
        slideService.setOrgInSlide(slide.getId(), slideUpdate.getOrgName());
        return slide;

    }
}
