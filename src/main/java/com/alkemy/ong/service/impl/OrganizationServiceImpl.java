package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.OrganizationCreationDto;
import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.OrganizationSlideDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.NotFoundList;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.SlideService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final MessageSource messageSource;
    private final OrganizationRepository organizationRepository;
    @Autowired
    private OrganizationMapper organizationMapper;
    
    @Autowired
    private SlideService slideService;

    @Override
    public OrganizationDto findById(Long id) {
        if (organizationRepository.findById(id).isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("organization.not.found", null, Locale.ENGLISH));
        }
        return organizationMapper.organizationToDto(organizationRepository.findById(id).get());
    }

    @Override
    public OrganizationDto editOrganization(OrganizationCreationDto organizationCreationDto) {

        Organization organization = organizationRepository.findAll().get(0);
        if(organization == null) {
            throw new NotFoundException(messageSource.getMessage("organization.not.found", null, Locale.ENGLISH));
        }
        organization = organizationMapper.editInformationOrganization(organization,organizationCreationDto);

        organization = organizationRepository.save(organization);
        return organizationMapper.organizationToDto(organization);
    }

    @Override
    public Organization findOrganization() {

        List<Organization> findAll = organizationRepository.findAll();
        Optional<Organization> result = findAll.stream().findFirst();
        
        if (result.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("organization.not.found", null, Locale.ENGLISH));
        }

        return result.get();
    }
  
    @Override
    public OrganizationDto save(OrganizationCreationDto organizationCreationDto) {

        Organization organization = organizationMapper.creationOrgFromOrganizationDto(organizationCreationDto);

        return organizationMapper.organizationToDto(organizationRepository.save(organization));
    }
    
    @Override
    public OrganizationSlideDto organizationSlide (){
        
        OrganizationSlideDto organizationSlide = organizationMapper.organizationSlideToDto(findOrganization());
        
        List<SlideDto> listSlide = slideService.findAllSlide();
        if (listSlide.isEmpty()) {
            throw new NotFoundList(messageSource.getMessage
                    ("slide.list.empty", null, Locale.ENGLISH));
        }
        Collections.sort(listSlide, (o1, o2) -> o1.getOrder().compareTo(o2.getOrder()));
        organizationSlide.setListSlide(listSlide);
        
        return organizationSlide;
    }
}
