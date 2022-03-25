package com.alkemy.ong.service;
import com.alkemy.ong.model.Organization;

import java.util.Optional;

public interface OrganizationService {
    Optional<Organization> findById(Long id);
}
