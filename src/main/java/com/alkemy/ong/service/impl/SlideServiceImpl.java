package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.SlideBasicDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideUpdateDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.SlideService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SlideServiceImpl implements SlideService {
    @Autowired
    private SlideRepository slideRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private SlideMapper slideMapper;
    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public SlideDto updateSlide(Long id, SlideUpdateDto slideDto) {
        Slide slide = slideRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource.getMessage("slide.not.found", new Object[]{id.toString()}, Locale.ENGLISH)));
        slide.setOrder(slideDto.getOrder());
        slide.setImageUrl(slideDto.getImageUrl());
        slide.setText(slideDto.getText());
        if(slideDto.getOrgName() != null) setOrgInSlide(id, slideDto.getOrgName());
        return slideMapper.slideToDto(slideRepository.save(slide));
    }
    @Override
    public SlideDto saveSlide(SlideDto slideDto){
        Slide slide = slideRepository.save(slideMapper.creationSlide(slideDto));
        return slideMapper.slideToDto(slide);
    }

    @Override
    public void setOrgInSlide(Long idSlide, String nameOrg) {
        Optional<Slide> slide = slideRepository.findById(idSlide);
        Organization organization = Optional.ofNullable(organizationRepository.findByName(nameOrg)).orElseThrow(() -> new NotFoundException(messageSource.getMessage("organization.name.not.found", new Object[]{nameOrg}, Locale.ENGLISH)));
        slide.get().setOrganization(organization);
    }

    @Override
    public List<SlideBasicDto> getSlideBasic() {
        if (slideRepository.count()==0) {
            throw new NotFoundException(messageSource.getMessage
                    ("slide.list.not.found", null, Locale.ENGLISH));
        }
            List<Slide>listEntity = slideRepository.findAll();
            List<SlideBasicDto>result = slideMapper.slideBasicEntityList2DtoList(listEntity);
            return result;
    }

}
