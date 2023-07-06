package com.digicore.billent.backoffice.service.modules.roles.controller;

import com.digicore.billent.backoffice.service.modules.roles.services.RoleService;
import com.digicore.billent.data.lib.modules.common.util.ControllerResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.ROLES_API_V1;

@RestController
@RequestMapping(ROLES_API_V1)
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("get-all-roles")
    public ResponseEntity<Object> getAllRoles(Pageable pageable) {
        return ControllerResponseUtil.buildSuccessResponse(roleService.getAllRoles(pageable, null), "Successful");
    }
}
