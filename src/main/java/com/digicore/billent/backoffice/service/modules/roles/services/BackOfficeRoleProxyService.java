package com.digicore.billent.backoffice.service.modules.roles.services;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-20(Thu)-2023
 */

import com.digicore.billent.data.lib.modules.backoffice.authorization.model.BackOfficePermission;
import com.digicore.billent.data.lib.modules.backoffice.authorization.model.BackOfficeRole;
import com.digicore.billent.data.lib.modules.backoffice.authorization.repository.BackOfficeRoleRepository;
import com.digicore.billent.data.lib.modules.common.authorization.dto.PermissionDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleCreationDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;


import com.digicore.billent.data.lib.modules.common.authorization.service.PermissionService;
import com.digicore.billent.data.lib.modules.common.authorization.service.RoleService;
import com.digicore.billent.data.lib.modules.common.settings.service.SettingService;
import com.digicore.registhentication.exceptions.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.digicore.billent.data.lib.modules.exception.messages.AuthorizationErrorMessage.*;

@Service
@RequiredArgsConstructor
public class BackOfficeRoleProxyService {
 private final BackOfficeRoleValidatorService validatorService;
 private final RoleService<RoleDTO, BackOfficeRole> backOfficeRoleServiceImpl;
 private final BackOfficeRoleRepository backOfficeRoleRepository;
 private final PermissionService<PermissionDTO, BackOfficePermission> backOfficePermissionServiceImpl;
 private final ExceptionHandler<String, String, HttpStatus, String> exceptionHandler;

 public Object createNewRole(RoleCreationDTO roleDTO) {
  backOfficeRoleServiceImpl.permissionCheck(roleDTO);
  backOfficeRoleServiceImpl.checkRoleIfExist(roleDTO.getName());
  backOfficeRoleServiceImpl.checkIfRoleIsNotSystemRole(roleDTO.getName());
  backOfficePermissionServiceImpl.getValidPermissions(roleDTO.getPermissions());
  return validatorService.createNewRole(roleDTO);
 }

 public void deleteRole(String roleName) {
  backOfficeRoleServiceImpl.roleCheck(roleName);
  RoleDTO roleDTO = new RoleDTO();
  roleDTO.setName(roleName);
  validatorService.deleteRole(roleDTO);
 }

 public Object updateRole(RoleCreationDTO roleDTO) {
    backOfficeRoleServiceImpl.roleCheck(roleDTO.getName());
    backOfficePermissionServiceImpl.getValidPermissions(roleDTO.getPermissions());
  return validatorService.updateRole(roleDTO);
 }

 public void disableRole(String roleName) {
     backOfficeRoleServiceImpl.checkIfRoleStatusIsInactive(roleName);
   backOfficeRoleServiceImpl.roleCheck(roleName);
   RoleDTO roleDTO = new RoleDTO();
   roleDTO.setName(roleName);
   validatorService.disableRole(roleDTO);
    }
 public void enableRole(String roleName) {
     backOfficeRoleServiceImpl.checkIfRoleStatusIsActive(roleName);
     backOfficeRoleServiceImpl.roleCheck(roleName);
     RoleDTO roleDTO = new RoleDTO();
     roleDTO.setName(roleName);
     validatorService.enableRole(roleDTO);
    }

}
