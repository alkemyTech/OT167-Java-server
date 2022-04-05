package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.service.SlideService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/slide")
public class SlideController {
    
    @Autowired
    private SlideService slideService;
    
    @PostMapping("/slides")
    public ResponseEntity<SlideDto> createSlide(@RequestBody SlideDto slideDto) throws IOException{
        SlideDto result = slideService.createSlide(slideDto);
        return ResponseEntity.ok().body(result);
    }
    
}
