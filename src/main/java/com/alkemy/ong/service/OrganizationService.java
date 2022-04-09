package com.alkemy.ong.service;
import com.alkemy.ong.dto.OrganizationCreationDto;
import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.OrganizationSlideDto;
import com.alkemy.ong.model.Organization;

public interface OrganizationService {
  
    OrganizationDto findById(Long id);
    Organization findOrganization();
    OrganizationDto editOrganization(OrganizationCreationDto organizationCreationDto);
    OrganizationDto save(OrganizationCreationDto organizationCreationDto);
    OrganizationSlideDto organizationSlide ();

}
