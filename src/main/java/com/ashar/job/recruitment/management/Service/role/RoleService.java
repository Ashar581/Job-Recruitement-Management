package com.ashar.job.recruitment.management.Service.role;

import com.ashar.job.recruitment.management.Dto.RoleDto;
import com.ashar.job.recruitment.management.Entity.Role;

import java.util.Set;

public interface RoleService {
    Role create(RoleDto dto);
    Set<String> getAllRoles();
    RoleDto delete(String roleCode);
}
