package com.alkemy.ong.mapper;
import com.alkemy.ong.dto.OrganizationCreationDto;
import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.model.Organization;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrganizationMapper {
    public OrganizationDto organizationToDto (Organization organization){
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setName(organization.getName());
        organizationDto.setImage(organization.getImage());
        organizationDto.setAddress(organization.getAddress());
        organizationDto.setPhone(organization.getPhone());
        organizationDto.setFacebookUrl(organization.getFacebookUrl());
        organizationDto.setInstagramUrl(organization.getInstagramUrl());
        organizationDto.setLinkedinUrl(organization.getLinkedInUrl());
        return organizationDto;
    }
    public List<OrganizationDto> organizationListToDtoList (List<Organization> organization){
        List<OrganizationDto> orgDtoList = new ArrayList<>();
        organization.forEach(org -> orgDtoList.add(organizationToDto(org)));
        return orgDtoList;
    }
    public Organization creationOrgFromOrganizationDto(OrganizationCreationDto dto) {
        Organization organization = new Organization();
        organization.setName(dto.getName());
        organization.setEmail(dto.getEmail());
        organization.setImage(dto.getImage());
        organization.setAddress(dto.getAddress());
        organization.setPhone(dto.getPhone());
        organization.setWelcomeText(dto.getWelcomeText());
        organization.setAboutUsText(dto.getAboutUsText());
        organization.setFacebookUrl(dto.getFacebookUrl());
        organization.setInstagramUrl(dto.getInstagramUrl());
        organization.setLinkedInUrl(dto.getLinkedInUrl());
        return organization;
    }

    public Organization editInformationOrganization(Organization organization, OrganizationCreationDto organizationCreationDto) {

        organization.setName(organizationCreationDto.getName());
        organization.setImage(organizationCreationDto.getImage());
        organization.setAddress(organizationCreationDto.getAddress());
        organization.setPhone(organizationCreationDto.getPhone());
        organization.setEmail(organizationCreationDto.getEmail());
        organization.setWelcomeText(organizationCreationDto.getWelcomeText());
        organization.setAboutUsText(organizationCreationDto.getAboutUsText());
        organization.setFacebookUrl(organizationCreationDto.getFacebookUrl());
        organization.setInstagramUrl(organizationCreationDto.getInstagramUrl());
        organization.setLinkedInUrl(organizationCreationDto.getLinkedInUrl());

        return organization;

    }
}
