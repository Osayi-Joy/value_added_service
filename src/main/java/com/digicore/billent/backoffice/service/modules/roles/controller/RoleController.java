package com.digicore.billent.backoffice.service.modules.roles.controller;

import com.digicore.billent.backoffice.service.modules.roles.services.RoleService;
import com.digicore.billent.data.lib.modules.common.authentication.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/get-all-roles")
    public ResponseEntity<Object> getAllRoles() {
        List<Role> allRoles = roleService.getAllRoles();
        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }
}
