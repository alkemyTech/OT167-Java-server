package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationCreationDto;
import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.OrganizationSlideDto;
import com.alkemy.ong.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;
    
    @GetMapping(value = "/public")
    public ResponseEntity<OrganizationSlideDto> findOrganization(){
        return ResponseEntity.ok(organizationService.organizationSlide());
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/public")
    public ResponseEntity<?> editOrganization(@Valid @RequestBody OrganizationCreationDto organizationCreationDto){
        return ResponseEntity.ok(organizationService.editOrganization(organizationCreationDto));
    }

    @PostMapping
    public ResponseEntity<OrganizationDto> save(@Valid @RequestBody OrganizationCreationDto organizationCreationDto){
        return ResponseEntity.ok(organizationService.save(organizationCreationDto));
    }
}
