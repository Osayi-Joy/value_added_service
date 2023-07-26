package com.digicore.billent.backoffice.service.modules.roles.controller;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.ROLES_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil.*;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.roles.services.BackOfficeRoleProxyService;
import com.digicore.billent.backoffice.service.modules.roles.services.BackOfficeRoleService;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleCreationDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ROLES_API_V1)
@RequiredArgsConstructor
@Tag(name = ROLE_CONTROLLER_TITLE, description = ROLE_CONTROLLER_DESCRIPTION)
public class RoleController {
  private final BackOfficeRoleService backOfficeRoleService;
  private final BackOfficeRoleProxyService backOfficeRoleProxyService;

  @GetMapping("get-all")
  @PreAuthorize("hasAuthority('view-roles')")
  @Operation(
      summary = ROLE_CONTROLLER_GET_ALL_ROLES_TITLE,
      description = ROLE_CONTROLLER_GET_ALL_ROLES_DESCRIPTION)
  public ResponseEntity<Object> getAllRoles(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize) {
    return ControllerResponse.buildSuccessResponse(
        backOfficeRoleService.getAllRoles(pageNumber, pageSize), "Roles retrieved successfully");
  }

    @GetMapping("get-system-permissions")
    @PreAuthorize("hasAuthority('view-permissions')")
    @Operation(
            summary = ROLE_CONTROLLER_GET_ALL_PERMISSIONS_TITLE,
            description = ROLE_CONTROLLER_GET_ALL_PERMISSIONS_DESCRIPTION)
    public ResponseEntity<Object> getAllPermissions() {
        return ControllerResponse.buildSuccessResponse(
                backOfficeRoleService.getAllPermissions(), "Permissions retrieved successfully");
    }

    @PostMapping("creation")
    @PreAuthorize("hasAuthority('create-roles')")
    @Operation(
            summary = ROLE_CONTROLLER_CREATE_A_ROLE_TITLE,
            description = ROLE_CONTROLLER_CREATE_A_ROLE_DESCRIPTION)
    public ResponseEntity<Object> createRole(@Valid @RequestBody RoleCreationDTO roleDTO){
      return ControllerResponse.buildSuccessResponse(backOfficeRoleProxyService.createNewRole(roleDTO));
    }

  @DeleteMapping("remove-{roleName}")
  @PreAuthorize("hasAuthority('delete-role')")
  @Operation(
          summary = ROLE_CONTROLLER_CREATE_A_ROLE_TITLE,
          description = ROLE_CONTROLLER_CREATE_A_ROLE_DESCRIPTION)
  public ResponseEntity<Object> createRole(@PathVariable String roleName){
    return ControllerResponse.buildSuccessResponse(backOfficeRoleProxyService.createNewRole(roleDTO));
  }
}
