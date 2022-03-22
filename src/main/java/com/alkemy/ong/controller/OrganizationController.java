package com.alkemy.ong.controller;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrganizationMapper organizationMapper;

    @GetMapping(value = "/public/{id}")
    public ResponseEntity<?> findOrganizationById(@PathVariable Long id){
        return ResponseEntity.ok(organizationMapper.organizationToDto(organizationService.findById(id).get()));
    }

}
