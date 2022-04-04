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
        return organization;
    }
}
