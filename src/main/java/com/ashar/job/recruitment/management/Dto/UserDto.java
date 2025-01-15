package com.ashar.job.recruitment.management.Dto;

import com.ashar.job.recruitment.management.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private UUID uuid;
    @NotNull(message = "Name cannot be empty.")
    private String name;
    @Email
    private String email;
    private String phone;
    private String password;
    private Set<String> roles;

    public static User dtoToEntity(UserDto dto){
        User entity = new User();

        entity.setEmail(dto.getEmail());
        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setPhone(dto.getPhone());

        return entity;
    }

    public static UserDto entityToDto(User entity){
        UserDto dto = new UserDto();
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setPassword(entity.getPassword());
        dto.setPhone(entity.getPhone());
        dto.setRoles(entity.getRoles()==null?new HashSet<>() : entity.getRoles().stream().map(roleMapping -> roleMapping.getRoleCode()).collect(Collectors.toSet()));
        return dto;
    }
}
