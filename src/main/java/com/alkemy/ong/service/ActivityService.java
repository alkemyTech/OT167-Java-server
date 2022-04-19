package com.alkemy.ong.service;

import com.alkemy.ong.dto.ActivityDto;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;


public interface ActivityService {
    
    ActivityDto createActivity(ActivityDto activityDto) throws IOException;

    ActivityDto update(Long id, ActivityDto activityDto);
}
