package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.service.ActivityService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity")
public class ActivityController {
    
    @Autowired
    private ActivityService activityService;
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/activities")
    public ResponseEntity<ActivityDto> createActivity(@RequestBody ActivityDto activityDto, @RequestBody MultipartFile image) throws IOException{
        
        ActivityDto activityCreated = activityService.createActivity(activityDto, image);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(activityCreated);
    }
    
}
