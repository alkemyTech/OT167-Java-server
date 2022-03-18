package com.alkemy.ong.controller;
import com.alkemy.ong.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/organization/public")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAllOrganizations(@PathVariable Long id){
        return ResponseEntity.ok(organizationService.getOrganizationById(id).get());
    }
}
