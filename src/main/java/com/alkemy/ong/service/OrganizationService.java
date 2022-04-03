package com.alkemy.ong.service;
import com.alkemy.ong.dto.OrganizationCreationDto;
import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.UrlOrganizationDto;
import com.alkemy.ong.model.Organization;

public interface OrganizationService {
    OrganizationDto findById(Long id);
    OrganizationDto editOrganization(OrganizationCreationDto organizationCreationDto);
    OrganizationDto save(OrganizationCreationDto organizationCreationDto);
}
