package com.alkemy.ong.service.impl;
import com.alkemy.ong.dto.OrganizationCreationDto;
import com.alkemy.ong.exception.BadRequest;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final MessageSource messageSource;
    private final OrganizationRepository organizationRepository;
    @Override
    public Optional<Organization> findById(Long id){
        if(organizationRepository.findById(id).isEmpty()) throw new NotFoundException(messageSource.getMessage("organization.not.found",null, Locale.ENGLISH));
        return organizationRepository.findById(id);
    }

    @Override
    public Organization editOrganization(OrganizationCreationDto organizationCreationDto) {

        Organization organization = organizationRepository.findAll().get(0);

        String phone = String.valueOf(organizationCreationDto.getPhone());

        organization.setName(organizationCreationDto.getName());
        organization.setImage(organizationCreationDto.getImage());
        organization.setAddress(organizationCreationDto.getAddress());
        organization.setPhone(organizationCreationDto.getPhone());
        organization.setEmail(organizationCreationDto.getEmail());
        organization.setWelcomeText(organizationCreationDto.getWelcomeText());
        organization.setAboutUsText(organizationCreationDto.getAboutUsText());

        return organizationRepository.save(organization);
    }

}
