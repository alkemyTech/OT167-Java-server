package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.PhotoService;
import com.alkemy.ong.service.SlideService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class SlideServiceImpl implements SlideService{
    
    @Autowired
    private SlideMapper slideMapper;
    
    @Autowired
    private SlideRepository slideRepository;
    
    @Autowired 
    private OrganizationService organizationService;
    
    @Autowired
    private PhotoService photoService;
    
    @Override
    public SlideDto createSlide(SlideDto slideDto)throws IOException{
        
        Slide slide = new Slide();
        BASE64DecodedMultipartFile image = new BASE64DecodedMultipartFile(Base64.decodeBase64(slideDto.getImageUrl()));
        String urlImage = photoService.uploadImage(image);
        slide.setImageUrl(urlImage);
        slide.setText(slideDto.getText());
        slide.setOrganization(organizationService.findOrganization());
        if (slideDto.getOrder() == null) {
            slide.setOrder(1 + findAllSlide().size()); 
        }else{
            slide.setOrder(slideDto.getOrder());
        }
        Slide slideCreated = slideRepository.save(slide);
        return slideMapper.slideToDTO(slideCreated);
    }
    
    @Override
    public List<SlideDto> findAllSlide(){
        return slideMapper.listActivityDto(slideRepository.findAll());
    }
}
