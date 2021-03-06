package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.ActivityService;
import com.alkemy.ong.service.PhotoService;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

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
public class ActivityServiceImpl implements ActivityService{
    
    @Autowired
    private ActivityMapper activityMapper;
    
    @Autowired
    private ActivityRepository activityRepository;
    
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private PhotoService photoService;

    private BASE64DecodedMultipartFile base64DecodedMultipartFile;
    @Override
    public ActivityDto createActivity(ActivityDto activityDto)throws IOException{

        BASE64DecodedMultipartFile image = new BASE64DecodedMultipartFile(Base64.decodeBase64(activityDto.getImage()));
        String urlImage = photoService.uploadImage(image);
        activityDto.setImage(urlImage);
        Activity activity = controlChanges(activityDto);
        Activity activityCreated = activityRepository.save(activity);
        
        return activityMapper.activityToDTO(activityCreated);
    }

    @Override
    public ActivityDto update(Long id, ActivityDto activityDto) {
        Optional<Activity> activity = this.activityRepository.findById(id);
        if (!activity.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("id.not.found", null, Locale.ENGLISH));
        }
        this.activityMapper.activityRefreshValues(activity.get(), activityDto);
        Activity activitySaved = this.activityRepository.save(activity.get());
        ActivityDto result = this.activityMapper.activityToDTO(activitySaved);
        return result;
    }

    public Activity controlChanges(ActivityDto activityDto)throws NotFoundException{
        
        if (activityDto.getName().isBlank()) {
            throw new NotFoundException(messageSource.getMessage("name.not.null",null, Locale.ENGLISH));
        } else if(activityDto.getContent().isBlank()){
            throw new NotFoundException(messageSource.getMessage("content.not.null",null, Locale.ENGLISH));
        } 
        
        return activityMapper.activityDtoToModel(activityDto);
    }
}
