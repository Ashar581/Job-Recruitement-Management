package com.ashar.job.recruitment.management.Controller;

import com.ashar.job.recruitment.management.ApiResponse.BaseApiResponse;
import com.ashar.job.recruitment.management.Dto.UserDto;
import com.ashar.job.recruitment.management.Service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController extends BaseApiResponse {
    @Autowired
    private UserService userService;
    @PostMapping("create")
    public ResponseEntity create(@Valid @RequestBody UserDto dto){
        return sendSuccessfulApiResponse(userService.create(dto),"User created.");
    }
    @DeleteMapping("{email}")
    public ResponseEntity delete(@PathVariable("email")String email){
        return sendSuccessfulApiResponse(userService.deleteUser(email),"User removed.");
    }
    @GetMapping("")
    public ResponseEntity viewAll(){
        return sendSuccessfulApiResponse(userService.allUsers(),"All users view.");
    }
    @GetMapping("{id}")
    public ResponseEntity view(@PathVariable("id")UUID uuid){
        return sendSuccessfulApiResponse(userService.getUser(uuid),"User viewed.");
    }
    @PutMapping("")
    public ResponseEntity update(@RequestBody UserDto dto){
        return sendSuccessfulApiResponse(userService.update(dto),"User updated.");
    }
}
