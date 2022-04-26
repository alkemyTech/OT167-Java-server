package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.RoleDto;
import com.alkemy.ong.model.Role;
import org.springframework.stereotype.Component;
@Component
public class RoleMapper {
    public Role createRole(RoleDto dto) {
        Role role = new Role();
        role.setId(role.getId());
        role.setId(dto.getId());
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        return role;
    }
}
