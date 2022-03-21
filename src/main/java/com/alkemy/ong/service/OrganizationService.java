package com.alkemy.ong.service;
import java.util.Optional;

public interface OrganizationService {
    Optional<Organization> findById(Long id);
}
