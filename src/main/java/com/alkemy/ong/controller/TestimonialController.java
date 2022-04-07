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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TestimonialDto> update(@PathVariable Long id, @Validated @RequestBody TestimonialDto testimonialDto){
        TestimonialDto result = this.testimonialService.update(id,testimonialDto);
        return ResponseEntity.ok().body(result);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        this.testimonialService.delete(id);
        Map<String, String> message = new HashMap<>() {{
            put("message: ", messageSource
                    .getMessage("testimonial.delete.ok", new Object[]{id}, Locale.ENGLISH));
        }};
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}


