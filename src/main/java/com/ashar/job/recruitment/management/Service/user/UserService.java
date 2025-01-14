package com.ashar.job.recruitment.management.Service.user;

import com.ashar.job.recruitment.management.Dto.UserDto;
import com.ashar.job.recruitment.management.Entity.User;

import java.util.List;

public interface UserService {
    UserDto create(UserDto dto);
    List<UserDto> allUsers();
    User deleteUser(String email);
}
