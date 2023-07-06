package com.digicore.billent.backoffice.service.modules.roles.controller;

import com.digicore.billent.backoffice.service.modules.roles.services.BackOfficeRoleService;
import com.digicore.billent.data.lib.modules.common.util.ControllerResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.ROLES_API_V1;

@RestController
@RequestMapping(ROLES_API_V1)
@RequiredArgsConstructor
public class RoleController {
    private final BackOfficeRoleService backOfficeRoleService;

    @GetMapping("get-all-roles")
    public ResponseEntity<Object> getAllRoles(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ControllerResponseUtil.buildSuccessResponse(backOfficeRoleService.getAllRoles(pageNumber, pageSize), "Roles retrieved successfully");
    }
}
