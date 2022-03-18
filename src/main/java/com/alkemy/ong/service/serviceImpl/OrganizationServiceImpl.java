package com.alkemy.ong.service.serviceImpl;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    @Override
    public Optional<Organization> getOrganizationById(Long id){
        if(organizationRepository.findById(id).isEmpty()) throw new NotFoundException("no one organization was add or created");
        return organizationRepository.findById(id);
    }
}
