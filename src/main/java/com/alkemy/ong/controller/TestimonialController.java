package com.alkemy.ong.controller;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.service.TestimonialService;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testimonial")
public class TestimonialController {
    
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private TestimonialService testimonialService;
        
    @PostMapping() 
    public ResponseEntity<Map<String,String>> saveTestimonial(@Valid @RequestBody TestimonialDto testimonialDto) { 
        testimonialService.save(testimonialDto); 
        Map<String, String> message = new HashMap<>(){{put("message: ", messageSource.getMessage("testimonial.created.successfully",new Object[]{testimonialDto.getName()}, Locale.ENGLISH));}};
        return ResponseEntity.status(HttpStatus.CREATED).body(message);}
}
