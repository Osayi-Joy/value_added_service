package com.digicore.billent.backoffice.service.modules.roles.services;

import com.digicore.billent.data.lib.modules.common.authorization.dto.PermissionDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleCreationDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTOWithTeamMembers;
import com.digicore.billent.data.lib.modules.common.authorization.model.Permission;
import com.digicore.billent.data.lib.modules.common.authorization.model.Role;
import com.digicore.billent.data.lib.modules.common.authorization.projection.RoleProjection;
import com.digicore.billent.data.lib.modules.common.authorization.service.PermissionService;
import com.digicore.billent.data.lib.modules.common.authorization.service.RoleService;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.request.processor.annotations.MakerChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BackOfficeRoleService implements BackOfficeRoleValidatorService{

  private final RoleService<RoleDTO, Role> roleService;
  private final PermissionService<PermissionDTO, Permission> permissionService;

  public Object getAllRoles(int pageNumber, int pageSize,String paginated) {
    if ("false".equalsIgnoreCase(paginated))
      return roleService.retrieveAllRoles();
    return roleService.retrieveAllRoles(pageNumber, pageSize);
  }


  public Set<PermissionDTO> getAllPermissions() {
    return permissionService.retrieveAllSystemPermissions();
  }
  @MakerChecker(
          checkerPermission = "approve-create-roles",
          makerPermission = "create-roles",
          requestClassName =
                  "com.digicore.billent.data.lib.modules.common.authorization.dto.RoleCreationDTO")
  public Object createNewRole(Object requestDTO, Object... args){
    RoleCreationDTO roleDTO = (RoleCreationDTO) requestDTO;
    return roleService.createNewRole(roleDTO);
  }


  @MakerChecker(
          checkerPermission = "approve-delete-role",
          makerPermission = "delete-role",
          requestClassName =
                  "com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO")
  public Object deleteRole(Object requestDTO, Object... args){
    RoleCreationDTO roleDTO = (RoleCreationDTO) requestDTO;
    return roleService.deleteRole(roleDTO.getName());
  }
}
