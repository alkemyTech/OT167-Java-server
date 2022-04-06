package com.alkemy.ong.controller;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideUpdateDto;
import com.alkemy.ong.service.SlideService;
import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/slides")
public class SlideController {
    private final MessageSource messageSource;
    @Autowired
    private SlideService slideService;
    @PutMapping(value = "{id}")
    public ResponseEntity<SlideDto> updateSlide(@PathVariable(value = "id") String id, @Valid @RequestBody SlideUpdateDto slideUpdateDto) {
        return ResponseEntity.ok(slideService.updateSlide(Long.valueOf(id), slideUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSlide(@PathVariable Long id){
        slideService.deleteSlide(id);

        return (ResponseEntity<HttpStatus>) ResponseEntity.status(HttpStatus.GONE);
    }

    @PostMapping
    public ResponseEntity<?> saveSlide(@Valid @RequestBody SlideDto slideDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(slideService.saveSlide(slideDto));
    }
}
