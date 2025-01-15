package com.ashar.job.recruitment.management.Service.user;

import com.ashar.job.recruitment.management.Dto.DocumentDto;
import com.ashar.job.recruitment.management.Dto.UserDto;
import com.ashar.job.recruitment.management.Entity.Role;
import com.ashar.job.recruitment.management.Entity.User;
import com.ashar.job.recruitment.management.Exception.ExistsException;
import com.ashar.job.recruitment.management.Exception.NotFoundException;
import com.ashar.job.recruitment.management.Exception.NullException;
import com.ashar.job.recruitment.management.Repository.RoleRepository;
import com.ashar.job.recruitment.management.Repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.bouncycastle.util.Strings;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserDto create(@Valid UserDto dto) throws NullException, NotFoundException{
        if (dto.getEmail()==null) throw new NullException("Please enter email");
        if (dto.getName()==null) throw new NullException("Please enter Name");
        if (dto.getPassword()==null) throw new NullException("Please enter password");
        if (dto.getRoles()==null ) dto.setRoles(Set.of("GUEST"));
        if (userRepository.findByEmail(dto.getEmail()).orElse(null)!=null){
            throw new ExistsException("User already exits.");
        }
        User create = UserDto.dtoToEntity(dto);
        //add role
        Set<Role> roles = dto.getRoles().stream()
                .map(roleCode -> roleRepository.findByRoleCode(roleCode)
                        .orElseThrow(()-> new NotFoundException("Role not found")))
                .collect(Collectors.toSet());
        create.setRoles(roles);
        create.setPassword(passwordEncoder.encode(create.getPassword()));
        //trigger email

        return UserDto.entityToDto(userRepository.save(create));
    }

    @Override
    public List<UserDto> allUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    return UserDto.entityToDto(user);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User deleteUser(String email) throws NullException, NotFoundException{
        if (email==null || email.isEmpty()) throw new NullException("Please give the email.");
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->{
                    throw new NotFoundException("User not found.");
                });
        userRepository.deleteUserRoleMapping(user.getUuid());
        userRepository.delete(user);
        return user;
    }

    @Override
    public UserDto getUser(UUID uuid) {
        return UserDto.entityToDto(userRepository.findById(uuid).orElseThrow(()->new NotFoundException("User not found.")));
    }

    @Override
    @Transactional
    public UserDto update(UserDto dto) throws NullException, NotFoundException{
        if (dto.getUuid()==null && dto.getEmail()==null) throw new NullException("Some items were not filled.");
        User current = new User();
        if (dto.getEmail()!=null) current = userRepository.findByEmail(dto.getEmail()).orElseThrow(()->new NotFoundException("User not found."));
        else current = userRepository.findById(dto.getUuid()).orElseThrow(()->new NotFoundException("User not found"));

        if (dto.getName()!=null && !dto.getName().isEmpty()) current.setName(dto.getName());
        if (dto.getPhone()!=null && !dto.getPhone().isEmpty()) current.setPhone(dto.getPhone());

        if (dto.getRoles()!=null && !dto.getRoles().isEmpty()){
            Set<Role> updatedRole = dto.getRoles()
                    .stream()
                    .filter(f -> roleRepository.findByRoleCode(f).orElse(null)!=null)
                    .map(r -> {
                        return roleRepository.findByRoleCode(r).orElseThrow(()->new NotFoundException("Role not found. Update failed."));
                    })
                    .collect(Collectors.toSet());
            current.setRoles(updatedRole);
        }
        return UserDto.entityToDto(userRepository.save(current));
    }
}
