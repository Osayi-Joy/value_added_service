package com.digicore.billent.backoffice.service.modules.roles.controller;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.roles.services.BackOfficeRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.ROLES_API_V1;
import static com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil.*;

@RestController
@RequestMapping(ROLES_API_V1)
@RequiredArgsConstructor
public class RoleController {
    private final BackOfficeRoleService backOfficeRoleService;

    @GetMapping("get-all-roles")
    public ResponseEntity<Object> getAllRoles(@RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
                                              @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize) {
        return ControllerResponse.buildSuccessResponse(backOfficeRoleService.getAllRoles(pageNumber, pageSize), "Roles retrieved successfully");
    }
}
