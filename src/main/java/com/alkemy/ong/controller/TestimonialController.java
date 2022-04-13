package com.alkemy.ong.controller;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.exception.MessagesInfo;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.service.TestimonialService;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new Testimonial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Testimonial created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Testimonial.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = @Content)})
    @PostMapping()
    public ResponseEntity<Map<String,String>> saveTestimonial(@Valid @RequestBody TestimonialDto testimonialDto) {
        testimonialService.save(testimonialDto);
        Map<String, String> message = new HashMap<>(){{put("message: ", messageSource.getMessage("testimonial.created.successfully",new Object[]{testimonialDto.getName()}, Locale.ENGLISH));}};
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @Operation(summary = "Edit a testimonial by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Testimonial edited succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Testimonial.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Testimonial not found",
                    content = @Content) })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TestimonialDto> update(@PathVariable Long id, @Validated @RequestBody TestimonialDto testimonialDto){
        TestimonialDto result = this.testimonialService.update(id,testimonialDto);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Delete a testimonial by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Testimonial deleted succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Testimonial.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Testimonialnot found",
                    content = @Content) })
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


