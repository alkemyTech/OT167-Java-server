package com.alkemy.ong.service;
import com.alkemy.ong.model.Organization;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface OrganizationService {
    Optional<Organization> findById(Long id);
}
