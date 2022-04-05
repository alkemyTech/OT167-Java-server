package com.alkemy.ong.mapper;
import com.alkemy.ong.dto.SlideBasicDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideUpdateDto;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SlideMapper {

    @Autowired
    private SlideService slideService;

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



