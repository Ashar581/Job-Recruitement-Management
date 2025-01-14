package com.ashar.job.recruitment.management.Dto;

import com.ashar.job.recruitment.management.Entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private UUID uuid;
    private String roleName;
    private String roleCode;

    public static Role dtoToEntity(RoleDto dto){
        Role entity = new Role();
        entity.setRoleCode(dto.getRoleCode());
        entity.setRoleName(dto.getRoleName());

        return entity;
    }
    public static RoleDto entityToDto(Role entity){
        RoleDto dto = new RoleDto();
        dto.setRoleCode(entity.getRoleCode());
        dto.setRoleName(entity.getRoleName());
        dto.setUuid(entity.getUuid());

        return dto;
    }
}
