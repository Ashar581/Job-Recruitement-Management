package com.ashar.job.recruitment.management.Service.role;

import com.ashar.job.recruitment.management.Dto.RoleDto;
import com.ashar.job.recruitment.management.Entity.Role;
import com.ashar.job.recruitment.management.Exception.ExistsException;
import com.ashar.job.recruitment.management.Exception.NotFoundException;
import com.ashar.job.recruitment.management.Exception.NullException;
import com.ashar.job.recruitment.management.Repository.RoleRepository;
import com.ashar.job.recruitment.management.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Role create(RoleDto dto) {
        if (dto.getRoleName()==null || dto.getRoleName().isEmpty()) throw new NullException("Role cannot be empty.");
        if (roleRepository.findByRoleName(dto.getRoleName())
                .orElse(null)!=null) throw new ExistsException("Role already exists.");
        dto.setRoleCode(dto.getRoleName().toUpperCase().replaceAll("[^a-zA-z]",""));

        return roleRepository.save(RoleDto.dtoToEntity(dto));
    }
    @Override
    public Set<String> getAllRoles(){
        return roleRepository.findAll()
                .stream()
                .map(roleCode -> roleCode.getRoleCode())
                .collect(Collectors.toSet());
    }

    @Override
    public RoleDto delete(String roleCode) {
        if (roleCode==null || roleCode.isEmpty()) throw new NullException("Role code cannot be null.");
        Role role = roleRepository.findByRoleCode(roleCode).orElseThrow(() -> {
            throw new NotFoundException("Role not found.");
        });
        roleRepository.delete(role);
        //also update the users wilt the role.
        role.getUser()
                .forEach(user -> {
                    user.getRoles().remove(role);
                    userRepository.save(user);
                });
        return RoleDto.entityToDto(role);
    }
}
