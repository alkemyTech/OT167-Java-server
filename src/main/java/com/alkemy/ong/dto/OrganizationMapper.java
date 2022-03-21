package com.alkemy.ong.dto;
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
    public Organization creationOrganization(OrganizationCreationDto dto) {
        return new Organization(null, dto.getName(), dto.getImage(), dto.getAddress(), dto.getPhone(), dto.getEmail(), dto.getWelcomeText(), dto.getAboutUsText(), null, null);
    }
}
