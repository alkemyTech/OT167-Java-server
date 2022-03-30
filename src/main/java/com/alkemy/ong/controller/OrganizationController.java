package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationCreationDto;
import com.alkemy.ong.dto.OrganizationDto;
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

    @GetMapping(value = "/public/{id}")
    public ResponseEntity<OrganizationDto> findOrganizationById(@Valid @PathVariable Long id){
        return ResponseEntity.ok(organizationService.findById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/public")
    public ResponseEntity<?> editOrganization(@Valid @RequestBody OrganizationCreationDto organizationCreationDto){
        return ResponseEntity.ok(organizationService.editOrganization(organizationCreationDto));
    }
}
