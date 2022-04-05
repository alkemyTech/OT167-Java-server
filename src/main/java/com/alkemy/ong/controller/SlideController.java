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
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<SlideBasicDto>> getSlideBasic(){
        List<SlideBasicDto> listSlideBasic = slideService.getSlideBasic();
        return ResponseEntity.status(HttpStatus.OK).body(listSlideBasic);
    }

}
