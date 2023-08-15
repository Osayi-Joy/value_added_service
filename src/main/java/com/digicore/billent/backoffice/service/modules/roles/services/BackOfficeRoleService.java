package com.digicore.billent.backoffice.service.modules.roles.services;

import com.digicore.billent.data.lib.modules.backoffice.authorization.model.BackOfficeRole;
import com.digicore.billent.data.lib.modules.common.authorization.dto.PermissionDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleCreationDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.common.authorization.model.Permission;
import com.digicore.billent.data.lib.modules.common.authorization.service.PermissionService;

import com.digicore.billent.data.lib.modules.common.authorization.service.RoleService;
import com.digicore.request.processor.annotations.MakerChecker;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackOfficeRoleService implements BackOfficeRoleValidatorService {

  private final RoleService<RoleDTO, BackOfficeRole> backOfficeRoleServiceImpl;
  private final PermissionService<PermissionDTO, Permission> permissionService;

  public Object getAllRoles(int pageNumber, int pageSize, String paginated) {
    if ("false".equalsIgnoreCase(paginated)) return backOfficeRoleServiceImpl.retrieveAllRoles();
    return backOfficeRoleServiceImpl.retrieveAllRoles(pageNumber, pageSize);
  }

  public Set<PermissionDTO> getAllPermissions() {
    return permissionService.retrieveAllSystemPermissions("");
  }

  @MakerChecker(
      checkerPermission = "approve-create-roles",
      makerPermission = "create-roles",
      requestClassName =
          "com.digicore.billent.data.lib.modules.common.authorization.dto.RoleCreationDTO")
  public Object createNewRole(Object requestDTO, Object... args) {
    RoleCreationDTO roleDTO = (RoleCreationDTO) requestDTO;
    return backOfficeRoleServiceImpl.createNewRole(roleDTO);
  }

  @MakerChecker(
      checkerPermission = "approve-delete-role",
      makerPermission = "delete-role",
      requestClassName = "com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO")
  public Object deleteRole(Object requestDTO, Object... args) {
    RoleCreationDTO roleDTO = (RoleCreationDTO) requestDTO;
     backOfficeRoleServiceImpl.deleteRole(roleDTO.getName());
     return null;
  }
}
