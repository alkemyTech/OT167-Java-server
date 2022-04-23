package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.dto.SlideBasicDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideUpdateDto;
import com.alkemy.ong.service.SlideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Tag(name = "Slide")
@RestController
@RequiredArgsConstructor
@RequestMapping("/slides")
public class SlideController {

    private final MessageSource messageSource;
    @Autowired
    private SlideService slideService;

    @Operation(summary = "Save a new slide")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Slide saved successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SlideDto.class))}),
        @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content)
    })
    @PostMapping
    public ResponseEntity<SlideDto> createSlide(@RequestBody SlideDto slideDto) throws IOException {
        SlideDto result = slideService.createSlide(slideDto);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Update slide by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Slide updated successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SlideDto.class))}),
        @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<SlideDto> updateSlide(@PathVariable(value = "id") String id, @Valid @RequestBody SlideUpdateDto slideUpdateDto) {
        return ResponseEntity.ok(slideService.updateSlide(Long.valueOf(id), slideUpdateDto));
    }

    @Operation(summary = "Get slide by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get slide",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SlideDto.class))}),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content),
            @ApiResponse(responseCode = "404", description = "Slide not found", content = @Content)
            
    })
    @GetMapping("/{id}")
    public ResponseEntity<SlideDto> getSlide(@Valid @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(slideService.findById(id));
    }

    @Operation(summary = "Get slide")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get slide",content = @Content),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content),
                    
    })
    @GetMapping
    public ResponseEntity<List<SlideBasicDto>> getSlideBasic() {
        List<SlideBasicDto> listSlideBasic = slideService.getSlideBasic();
        return ResponseEntity.status(HttpStatus.OK).body(listSlideBasic);
    }

    @Operation(summary = "Delete slide by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Slide deleted successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = SlideDto.class))}),
        @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSlide(@PathVariable Long id) {
        slideService.deleteSlide(id);
        return ResponseEntity.status(HttpStatus.GONE).build();
    }
}
