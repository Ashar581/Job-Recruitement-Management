package com.ashar.job.recruitment.management.Controller;

import com.ashar.job.recruitment.management.ApiResponse.BaseApiResponse;
import com.ashar.job.recruitment.management.Dto.RoleDto;
import com.ashar.job.recruitment.management.Service.role.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController extends BaseApiResponse {
    @Autowired
    private RoleService roleService;
    @PostMapping("create")
    public ResponseEntity createRole(@Valid @RequestBody RoleDto dto){
        return sendSuccessfulApiResponse(roleService.create(dto),"Role added.");
    }
    @GetMapping("")
    public ResponseEntity getAllRoles(){
        return sendSuccessfulApiResponse(roleService.getAllRoles(),"All Roles");
    }
    @DeleteMapping("")
    public ResponseEntity deleteRole(@PathVariable("{roleCode}")String roleCode){
        return sendSuccessfulApiResponse(roleService.delete(roleCode),"");
    }
}