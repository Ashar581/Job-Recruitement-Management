package com.ashar.job.recruitment.management.Service.user;

import com.ashar.job.recruitment.management.Dto.DocumentDto;
import com.ashar.job.recruitment.management.Dto.UserDto;
import com.ashar.job.recruitment.management.Entity.User;
import com.ashar.job.recruitment.management.Model.LoginRequest;
import com.ashar.job.recruitment.management.Model.LoginResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto create(UserDto dto);
    List<UserDto> allUsers();
    User deleteUser(String email);
    UserDto getUser(UUID uuid);
    UserDto update(UserDto dto);

    LoginResponse authenticate(LoginRequest loginRequest);
}
