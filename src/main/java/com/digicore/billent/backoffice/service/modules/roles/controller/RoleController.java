package com.digicore.billent.backoffice.service.modules.roles.controller;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.ROLES_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.roles.services.BackOfficeRoleProxyService;
import com.digicore.billent.backoffice.service.modules.roles.services.BackOfficeRoleService;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleCreationDTO;
import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.request.processor.annotations.LogActivity;
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
          int pageSize,
      @RequestParam(value = "paginated", defaultValue = "false", required = false)
          String paginated) {
    return ControllerResponse.buildSuccessResponse(
        backOfficeRoleService.getAllRoles(pageNumber, pageSize, paginated),
        "Roles retrieved successfully");
  }

  @GetMapping("get-{roleName}-details")
  @PreAuthorize("hasAuthority('view-role-details')")
  @Operation(
      summary = ROLE_CONTROLLER_GET_ROLE_TITLE,
      description = ROLE_CONTROLLER_GET_ROLE_DESCRIPTION)
  public ResponseEntity<Object> getRole(@PathVariable String roleName) {
    return ControllerResponse.buildSuccessResponse(
        backOfficeRoleService.getRole(roleName), "Roles retrieved successfully");
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

  @LogActivity(
      activity = AuditLogActivity.CREATE_NEW_ROLE,
      auditType = AuditLogActivity.BACKOFFICE,
      auditDescription = AuditLogActivity.CREATE_NEW_ROLE_DESCRIPTION)
  @PostMapping("creation")
 @PreAuthorize("hasAuthority('create-roles')")
  @Operation(
      summary = ROLE_CONTROLLER_CREATE_A_ROLE_TITLE,
      description = ROLE_CONTROLLER_CREATE_A_ROLE_DESCRIPTION)
  public ResponseEntity<Object> createRole(@Valid @RequestBody RoleCreationDTO roleDTO) {

    return ControllerResponse.buildSuccessResponse(
        backOfficeRoleProxyService.createNewRole(roleDTO));
  }

  @LogActivity(
          activity = AuditLogActivity.DELETE_ROLE,
          auditType = AuditLogActivity.BACKOFFICE,
          auditDescription = AuditLogActivity.DELETE_ROLE_DESCRIPTION)
  @DeleteMapping("remove-{roleName}")
  @PreAuthorize("hasAuthority('delete-role')")
  @Operation(
      summary = ROLE_CONTROLLER_DELETE_A_ROLE_TITLE,
      description = ROLE_CONTROLLER_DELETE_A_ROLE_DESCRIPTION)
  public ResponseEntity<Object> createRole(@PathVariable String roleName) {
    backOfficeRoleProxyService.deleteRole(roleName);
    return ControllerResponse.buildSuccessResponse();
  }

    @LogActivity(
            activity = AuditLogActivity.EDIT_ROLE,
            auditType = AuditLogActivity.BACKOFFICE,
            auditDescription = AuditLogActivity.EDIT_ROLE_DESCRIPTION)
  @PatchMapping("edit")
  @PreAuthorize("hasAuthority('edit-role')")
  @Operation(
      summary = ROLE_CONTROLLER_UPDATE_A_ROLE_TITLE,
      description = ROLE_CONTROLLER_UPDATE_A_ROLE_DESCRIPTION)
  public ResponseEntity<Object> updateRole(@Valid @RequestBody RoleCreationDTO roleDTO) {
    return ControllerResponse.buildSuccessResponse(
        backOfficeRoleProxyService.updateRole(roleDTO), "Role updated successfully");
  }

    @LogActivity(
            activity = AuditLogActivity.DISABLE_ROLE,
            auditType = AuditLogActivity.BACKOFFICE,
            auditDescription = AuditLogActivity.DISABLE_ROLE_DESCRIPTION)
    @PatchMapping("disable-{roleName}")
    @PreAuthorize("hasAuthority('disable-role')")
    @Operation(
            summary = ROLE_CONTROLLER_DISABLE_A_ROLE_TITLE,
            description = ROLE_CONTROLLER_DISABLE_A_ROLE_DESCRIPTION)
    public ResponseEntity<Object> disableRole(@PathVariable String roleName){
        backOfficeRoleProxyService.disableRole(roleName);
        return ControllerResponse.buildSuccessResponse();
    }

    @LogActivity(
            activity = AuditLogActivity.ENABLE_ROLE,
            auditType = AuditLogActivity.BACKOFFICE,
            auditDescription = AuditLogActivity.ENABLE_ROLE_DESCRIPTION)
    @PatchMapping("enable-{roleName}")
    @PreAuthorize("hasAuthority('enable-role')")
    @Operation(
            summary = ROLE_CONTROLLER_ENABLE_A_ROLE_TITLE,
            description = ROLE_CONTROLLER_ENABLE_A_ROLE_DESCRIPTION)
    public ResponseEntity<Object> enableRole(@PathVariable String roleName){
        backOfficeRoleProxyService.enableRole(roleName);
        return ControllerResponse.buildSuccessResponse();
    }
}
