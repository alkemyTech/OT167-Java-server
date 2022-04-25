package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.security.dto.UserLoginResponse;
import com.alkemy.ong.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Activity")
@RestController
@RequiredArgsConstructor
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Operation(summary = "Save a new activity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity saved successfully",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityDto.class))}),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ActivityDto> createActivity(@RequestBody ActivityDto activityDto) throws IOException{
        ActivityDto result = activityService.createActivity(activityDto);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Update activity by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity updated successfully",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityDto.class))}),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ActivityDto> update(@PathVariable Long id, @Validated @RequestBody ActivityDto activityDto){
        ActivityDto result = this.activityService.update(id,activityDto);
        return ResponseEntity.ok().body(result);
    }

}
