package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideUpdateDto;
import com.alkemy.ong.service.SlideService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/slide")
public class SlideController {
    
    private final MessageSource messageSource;
    @Autowired
    private SlideService slideService;
    
    @PostMapping("/slides")
    public ResponseEntity<SlideDto> createSlide(@RequestBody SlideDto slideDto) throws IOException{
        SlideDto result = slideService.createSlide(slideDto);
        return ResponseEntity.ok().body(result);
    }
        
    @PutMapping(value = "{id}")
    public ResponseEntity<SlideDto> updateSlide(@PathVariable(value = "id") String id, @Valid @RequestBody SlideUpdateDto slideUpdateDto) {
        return ResponseEntity.ok(slideService.updateSlide(Long.valueOf(id), slideUpdateDto));
    }
  
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<SlideDto> getSlide(@Valid @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(slideService.findById(id));
    }

}
