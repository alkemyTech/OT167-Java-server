package com.alkemy.ong.controller;
import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organization/public")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    private OrganizationDto organizationDto;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAllOrganizations(@PathVariable Long id){
        return ResponseEntity.ok(organizationDto.organizationEntityToDto(organizationService.getOrganizationById(id).get()));
    }
    @PostMapping(value = "/")
    public ResponseEntity<?> createOrganization(@Valid @RequestBody Organization organization){
        organizationService.createOrganization(organization);
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationDto.organizationEntityToDto(organization));
    }
}
