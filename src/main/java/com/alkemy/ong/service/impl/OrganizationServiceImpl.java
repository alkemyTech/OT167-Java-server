package com.alkemy.ong.service.impl;
import com.alkemy.ong.dto.OrganizationCreationDto;
import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.UrlOrganizationDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final MessageSource messageSource;
    private final OrganizationRepository organizationRepository;
    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public OrganizationDto findById(Long id){
        if(organizationRepository.findById(id).isEmpty()) throw new NotFoundException(messageSource.getMessage("organization.not.found",null, Locale.ENGLISH));
        return organizationMapper.organizationToDto(organizationRepository.findById(id).get());
    }

    @Override
    public Organization editOrganization(OrganizationCreationDto organizationCreationDto) {

        Organization organization = organizationRepository.findAll().get(0);

        organization.setName(organizationCreationDto.getName());
        organization.setImage(organizationCreationDto.getImage());
        organization.setAddress(organizationCreationDto.getAddress());
        organization.setPhone(organizationCreationDto.getPhone());
        organization.setEmail(organizationCreationDto.getEmail());
        organization.setWelcomeText(organizationCreationDto.getWelcomeText());
        organization.setAboutUsText(organizationCreationDto.getAboutUsText());

        return organizationRepository.save(organization);
    }

    @Override
    public OrganizationDto editOrganizationUrl(UrlOrganizationDto urlOrganizationDto, Long id) {
        Organization organization = organizationRepository.findById(id).get();
        //caso en que no encuentra la organizacion
        organization.setLinkedInUrl(urlOrganizationDto.getLinkedInUrl());
        organization.setFacebookUrl(urlOrganizationDto.getFacebookUrl());
        organization.setInstagramUrl(urlOrganizationDto.getInstagramUrl());

        organization = organizationRepository.save(organization);
        return organizationMapper.organizationToDto(organization);
    }

}
