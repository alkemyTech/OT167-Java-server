package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationCreationDto;
import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.OrganizationSlideDto;
import com.alkemy.ong.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
@Tag(name = "Organization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    
    @Operation(summary = "Get organization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully get organization",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OrganizationSlideDto.class))}),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content)                     
    })
    @GetMapping("/public")
    public ResponseEntity<OrganizationSlideDto> findOrganization(){
        return ResponseEntity.ok(organizationService.organizationSlide());
    }

    @Operation(summary = "Update Organization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OrganizationDto.class))}),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content),
            @ApiResponse(responseCode = "404", description = "Organization not found", content = @Content)
            
    })
    @PutMapping("/public")
    public ResponseEntity<?> editOrganization(@Valid @RequestBody OrganizationCreationDto organizationCreationDto){
        return ResponseEntity.ok(organizationService.editOrganization(organizationCreationDto));
    }

    @Operation(summary = "Add new organization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organization save successfully",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OrganizationDto.class))}),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content)
            
    })
    @PostMapping
    public ResponseEntity<OrganizationDto> save(@Valid @RequestBody OrganizationCreationDto organizationCreationDto){
        return ResponseEntity.ok(organizationService.save(organizationCreationDto));
    }
}
