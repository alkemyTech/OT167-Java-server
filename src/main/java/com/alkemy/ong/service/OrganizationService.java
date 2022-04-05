package com.alkemy.ong.service;
import com.alkemy.ong.dto.OrganizationCreationDto;
import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.model.Organization;

public interface OrganizationService {
    OrganizationDto findById(Long id);
    Organization editOrganization(OrganizationCreationDto organizationCreationDto);
    Organization findOrganization();
}
