package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideBasicDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideUpdateDto;
import com.alkemy.ong.service.SlideService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/slides")
public class SlideController {
    
    private final MessageSource messageSource;
    @Autowired
    private SlideService slideService;
    
    @PostMapping("/slide")
    public ResponseEntity<SlideDto> createSlide(@RequestBody SlideDto slideDto) throws IOException{
        SlideDto result = slideService.createSlide(slideDto);
        return ResponseEntity.ok().body(result);
    }
        
    @PutMapping(value = "{id}")
    public ResponseEntity<SlideDto> updateSlide(@PathVariable Long id, @Valid @RequestBody SlideUpdateDto slideUpdateDto) {
        return ResponseEntity.ok(slideService.updateSlide(Long.valueOf(id), slideUpdateDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlideDto> getSlide(@Valid @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(slideService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<SlideBasicDto>> getSlideBasic(){
        List<SlideBasicDto> listSlideBasic = slideService.getSlideBasic();
        return ResponseEntity.status(HttpStatus.OK).body(listSlideBasic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSlide(@PathVariable Long id){
        slideService.deleteSlide(id);
        return ResponseEntity.status(HttpStatus.GONE).build();
    }
}
