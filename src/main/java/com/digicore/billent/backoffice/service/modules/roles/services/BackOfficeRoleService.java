package com.digicore.billent.backoffice.service.modules.roles.services;

import com.digicore.billent.data.lib.modules.backoffice.authorization.model.BackOfficePermission;
import com.digicore.billent.data.lib.modules.backoffice.authorization.model.BackOfficeRole;
import com.digicore.billent.data.lib.modules.common.authorization.dto.*;
import com.digicore.billent.data.lib.modules.common.authorization.service.PermissionService;
import com.digicore.billent.data.lib.modules.common.authorization.service.RoleService;
import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.request.processor.annotations.MakerChecker;
import com.digicore.request.processor.processors.AuditLogProcessor;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackOfficeRoleService implements BackOfficeRoleValidatorService {

  private final RoleService<RoleDTO, BackOfficeRole> backOfficeRoleServiceImpl;
  private final PermissionService<PermissionDTO, BackOfficePermission>
      backOfficePermissionServiceImpl;
  private final AuditLogProcessor auditLogProcessor;

  public Object getAllRoles(int pageNumber, int pageSize, boolean paginated) {
    if (!paginated) return backOfficeRoleServiceImpl.retrieveAllRoles();
    return backOfficeRoleServiceImpl.retrieveAllRoles(pageNumber, pageSize);
  }

  public RoleDTOWithTeamMembers getRole(String roleName) {
    return backOfficeRoleServiceImpl.retrieveSystemRole(roleName);
  }

  public Set<PermissionDTO> getAllPermissions() {
    return backOfficePermissionServiceImpl.retrieveAllSystemPermissions();
  }

  @MakerChecker(
      checkerPermission = "approve-create-roles",
      makerPermission = "create-roles",
      requestClassName =
          "com.digicore.billent.data.lib.modules.common.authorization.dto.RoleCreationDTO")
  public Object createNewRole(Object requestDTO, Object... args) {
    RoleCreationDTO roleDTO = (RoleCreationDTO) requestDTO;
    backOfficeRoleServiceImpl.createNewRole(roleDTO);
    auditLogProcessor.saveAuditWithDescription(
        AuditLogActivity.APPROVE_CREATE_NEW_ROLE,
        AuditLogActivity.BACKOFFICE,
        AuditLogActivity.APPROVE_CREATE_NEW_ROLE_DESCRIPTION.replace("{}", roleDTO.getName()));
    return Optional.empty();
  }

  @MakerChecker(
      checkerPermission = "approve-delete-role",
      makerPermission = "delete-role",
      requestClassName = "com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO")
  public Object deleteRole(Object requestDTO, Object... args) {
    RoleDTO roleDTO = (RoleDTO) requestDTO;
    backOfficeRoleServiceImpl.deleteRole(roleDTO.getName());
    auditLogProcessor.saveAuditWithDescription(
        AuditLogActivity.APPROVE_DELETE_ROLE,
        AuditLogActivity.BACKOFFICE,
        AuditLogActivity.APPROVE_DELETE_ROLE_DESCRIPTION.replace("{}", roleDTO.getName()));
    return Optional.empty();
  }

  @MakerChecker(
      checkerPermission = "approve-edit-role",
      makerPermission = "edit-role",
      requestClassName =
          "com.digicore.billent.data.lib.modules.common.authorization.dto.RoleCreationDTO")
  public Object updateRole(Object requestDTO, Object... args) {
    RoleCreationDTO roleDTO = (RoleCreationDTO) requestDTO;
    backOfficeRoleServiceImpl.updateExistingRole(roleDTO);
    auditLogProcessor.saveAuditWithDescription(
        AuditLogActivity.APPROVE_EDIT_ROLE,
        AuditLogActivity.BACKOFFICE,
        AuditLogActivity.APPROVE_EDIT_ROLE_DESCRIPTION.replace("{}", roleDTO.getName()));
    return Optional.empty();
  }
  @MakerChecker(
          checkerPermission = "approve-disable-role",
          makerPermission = "disable-role",
          requestClassName = "com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO")
  @Override
  public Object disableRole(Object requestDTO, Object... args) {
    RoleDTO roleDTO = (RoleDTO) requestDTO;
    backOfficeRoleServiceImpl.disableRole(roleDTO.getName());
    auditLogProcessor.saveAuditWithDescription(
            AuditLogActivity.APPROVE_DISABLE_ROLE,
            AuditLogActivity.BACKOFFICE,
            AuditLogActivity.APPROVE_DISABLE_ROLE_DESCRIPTION.replace("{}", roleDTO.getName()));
    return Optional.empty();
  }

  @MakerChecker(
          checkerPermission = "approve-enable-role",
          makerPermission = "enable-role",
          requestClassName = "com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO")
  @Override
  public Object enableRole(Object requestDTO, Object... args) {
    RoleDTO roleDTO = (RoleDTO) requestDTO;
    backOfficeRoleServiceImpl.enableRole(roleDTO.getName());
    auditLogProcessor.saveAuditWithDescription(
            AuditLogActivity.APPROVE_ENABLE_ROLE,
            AuditLogActivity.BACKOFFICE,
            AuditLogActivity.APPROVE_ENABLE_ROLE_DESCRIPTION.replace("{}", roleDTO.getName()));
    return Optional.empty();
  }
}
