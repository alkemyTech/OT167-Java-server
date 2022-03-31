package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.model.Activity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {
    
    public ActivityDto activityToDTO (Activity activity){
        
        ActivityDto activityDTO = new ActivityDto();
        activityDTO.setId(activity.getId());
        activityDTO.setName(activity.getName());
        activityDTO.setContent(activity.getContent());
        activityDTO.setImage(activity.getImage());
        
        return activityDTO;
    }
    
    public Activity activityDtoToModel (ActivityDto activityDTO){
        
        Activity activity = new Activity();
        activity.setName(activityDTO.getName());
        activity.setContent(activityDTO.getContent());
        activity.setImage(activityDTO.getImage());
        
        return activity;
    }
    
    public List<ActivityDto> listActivityDto(List<Activity> listActivity){
        
        return listActivity.stream()
                .map(activity -> activityToDTO(activity))
                .collect(Collectors.toList());
    }
}
