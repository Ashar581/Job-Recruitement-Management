package com.ashar.job.recruitment.management.Model;

import com.ashar.job.recruitment.management.Dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private UserDto userDto;
    private String token;
}
