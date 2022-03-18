package com.alkemy.ong.dto;

import com.alkemy.ong.model.Organization;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrganizationDto implements Serializable {
    private String name;
    private String image;
    private String address;
    private int phone;

    public static OrganizationDto organizationEntityToDto (Organization organization){
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setName(organization.getName());
        organizationDto.setImage(organization.getImage());
        organizationDto.setAddress(organization.getAddress());
        organizationDto.setPhone(organization.getPhone());
        return organizationDto;
    }
    public static List<OrganizationDto> organizationListEntityToDtoList (List<Organization> organization){
        List<OrganizationDto> orgDtoList = new ArrayList<>();
        organization.forEach(org -> orgDtoList.add(OrganizationDto.organizationEntityToDto(org)));
        return orgDtoList;
    }
}
