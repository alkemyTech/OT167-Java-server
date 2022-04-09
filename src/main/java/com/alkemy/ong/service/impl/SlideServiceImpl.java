package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.SlideBasicDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.exception.NotFoundList;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.PhotoService;
import java.io.IOException;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
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
import java.util.Locale;
import java.util.Optional;

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

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private PhotoService photoService;

    @Override
    public SlideDto createSlide(SlideDto slideDto) throws IOException {

        Slide slide = new Slide();
        BASE64DecodedMultipartFile image = new BASE64DecodedMultipartFile(Base64.decodeBase64(slideDto.getImageUrl()));
        String urlImage = photoService.uploadImage(image);
        slide.setImageUrl(urlImage);
        slide.setText(slideDto.getText());
        slide.setOrganization(organizationService.findOrganization());
        if (slideDto.getOrder() == null) {
            slide.setOrder(1 + findAllSlide().size());
        } else {
            slide.setOrder(slideDto.getOrder());
        }
        Slide slideCreated = slideRepository.save(slide);
        return slideMapper.slideToDTO(slideCreated);
    }

    @Override
    public List<SlideDto> findAllSlide() {
        return slideMapper.listActivityDto(slideRepository.findAll());
    }

    @Override
    public SlideDto updateSlide(Long id, SlideUpdateDto slideDto) {
        Slide slide = slideRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource.getMessage("slide.not.found", new Object[]{id.toString()}, Locale.ENGLISH)));
        slide.setOrder(slideDto.getOrder());
        slide.setImageUrl(slideDto.getImageUrl());
        slide.setText(slideDto.getText());
        if (slideDto.getOrgName() != null) {
            setOrgInSlide(id, slideDto.getOrgName());
        }
        return slideMapper.slideToDTO(slideRepository.save(slide));
    }

    @Override
    public void setOrgInSlide(Long idSlide, String nameOrg) {
        Optional<Slide> slide = slideRepository.findById(idSlide);
        Organization organization = Optional.ofNullable(organizationRepository.findByName(nameOrg)).orElseThrow(() -> new NotFoundException(messageSource.getMessage("organization.name.not.found", new Object[]{nameOrg}, Locale.ENGLISH)));
        slide.get().setOrganization(organization);
    }

    @Override
    public SlideDto findById(Long id) {
        if (slideRepository.findById(id).isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("slide.not.found", new Object[]{id.toString()}, Locale.ENGLISH));
        }
        return slideMapper.slideToDTO(slideRepository.findById(id).get());
    }

    @Override
    public List<SlideBasicDto> getSlideBasic() {
        if (slideRepository.count()==0) {
            throw new NotFoundList(messageSource.getMessage
                    ("slide.list.empty", null, Locale.ENGLISH));
        }
        List<Slide>listEntity = slideRepository.findAll();
        List<SlideBasicDto>result = slideMapper.slideBasicEntityList2DtoList(listEntity);
        return result;
    }

    @Override
    public void deleteSlide(Long id) {
        SlideDto slideDto = this.findById(id);

        photoService.deleteObject(slideDto.getImageUrl());

        slideRepository.deleteById(slideDto.getId());
    }
}
