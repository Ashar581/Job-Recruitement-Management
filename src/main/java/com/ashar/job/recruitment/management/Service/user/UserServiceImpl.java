package com.ashar.job.recruitment.management.Service.user;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public UserDto create(@Valid UserDto dto) throws NullException{
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
                .map(user -> UserDto.entityToDto(user))
                .toList();
    }

    @Override
    @Transactional
    public User deleteUser(String email) {
        if (email==null || email.isEmpty()) throw new NullException("Please give the email.");
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->{
                    throw new NotFoundException("User not found.");
                });
        userRepository.deleteUserRoleMapping(user.getUuid());
        userRepository.delete(user);
        return user;
    }
}
