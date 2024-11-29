package com.pascal.ecommerce.backend.controller;

import com.pascal.ecommerce.backend.model.Role;
import com.pascal.ecommerce.backend.request.RoleRequest;
import com.pascal.ecommerce.backend.response.ApiResponse;
import com.pascal.ecommerce.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/roles")
@CrossOrigin(origins = "http://localhost:5173")
public class RoleController {

    private final RoleService RoleService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addRole(@RequestBody RoleRequest request) {

        Role newRole = RoleService.createRole(request);

        return ResponseEntity.ok(new ApiResponse("Create"+request.getName()+"Role Success!", newRole));
    }
}
