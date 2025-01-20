package com.ashar.job.recruitment.management.Controller;

import com.ashar.job.recruitment.management.ApiResponse.BaseApiResponse;
import com.ashar.job.recruitment.management.Model.LoginRequest;
import com.ashar.job.recruitment.management.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController extends BaseApiResponse {
    @Autowired
    private UserService userService;
    @PostMapping("login")
    public ResponseEntity authenticate(@RequestBody LoginRequest request){
        return sendSuccessfulApiResponse(userService.authenticate(request),"User logged in successfully");
    }
}
