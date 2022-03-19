package com.alkemy.ong.dto;

import com.alkemy.ong.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String descripcion;

    public static RoleDto RoleDto(Role role){
        RoleDto dto = new RoleDto();
        dto.setName(role.getName());
        dto.setDescripcion(role.getDescripcion());
        return dto;
    }
    public static List<RoleDto> ListaUsuarioDto(List<Role> roles){
        List<RoleDto> roleDtoList = new ArrayList<>();
        roles.forEach(role -> roleDtoList.add(RoleDto.RoleDto(role)));
        return roleDtoList;
    }
}