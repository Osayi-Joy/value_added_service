package com.digicore.billent.backoffice.service.modules.roles.services;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-20(Thu)-2023
 */

import com.digicore.billent.data.lib.modules.backoffice.authorization.model.BackOfficePermission;
import com.digicore.billent.data.lib.modules.backoffice.authorization.model.BackOfficeRole;
import com.digicore.billent.data.lib.modules.common.authorization.dto.PermissionDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleCreationDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;


import com.digicore.billent.data.lib.modules.common.authorization.service.PermissionService;
import com.digicore.billent.data.lib.modules.common.authorization.service.RoleService;
import com.digicore.registhentication.exceptions.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.digicore.billent.data.lib.modules.exception.messages.AuthorizationErrorMessage.PERMISSIONS_REQUIRED_CODE;
import static com.digicore.billent.data.lib.modules.exception.messages.AuthorizationErrorMessage.PERMISSIONS_REQUIRED_MESSAGE;

@Service
@RequiredArgsConstructor
public class BackOfficeRoleProxyService {
 private final BackOfficeRoleValidatorService validatorService;
 private final RoleService<RoleDTO, BackOfficeRole> backOfficeRoleServiceImpl;
 private final PermissionService<PermissionDTO, BackOfficePermission> backOfficePermissionServiceImpl;
 private final ExceptionHandler<String, String, HttpStatus, String> exceptionHandler;

 @CacheEvict(value = {"roleDetailsCache", "allRolesCache"}, allEntries = true)
 public Object createNewRole(RoleCreationDTO roleDTO) {
  backOfficeRoleServiceImpl.permissionCheck(roleDTO);
  backOfficeRoleServiceImpl.checkRoleIfExist(roleDTO.getName());
  backOfficeRoleServiceImpl.checkIfRoleIsNotSystemRole(roleDTO.getName());
  backOfficePermissionServiceImpl.getValidPermissions(roleDTO.getPermissions());
  return validatorService.createNewRole(roleDTO);
 }

 @CacheEvict(value = {"roleDetailsCache", "allRolesCache"}, allEntries = true)
 public void deleteRole(String roleName) {
  backOfficeRoleServiceImpl.roleCheck(roleName);
  RoleDTO roleDTO = new RoleDTO();
  roleDTO.setName(roleName);
  validatorService.deleteRole(roleDTO);
 }

 @CacheEvict(value = {"roleDetailsCache", "allRolesCache"}, allEntries = true)
 public Object updateRole(RoleCreationDTO roleDTO) {
    backOfficeRoleServiceImpl.roleCheck(roleDTO.getName());
  return validatorService.updateRole(roleDTO);
 }

 public void disableRole(String roleName) {
   backOfficeRoleServiceImpl.roleCheck(roleName);
   RoleDTO roleDTO = new RoleDTO();
   roleDTO.setName(roleName);
   validatorService.disableRole(roleDTO);
    }
 public void enableRole(String roleName) {
     backOfficeRoleServiceImpl.roleCheck(roleName);
     RoleDTO roleDTO = new RoleDTO();
     roleDTO.setName(roleName);
     validatorService.enableRole(roleDTO);
    }
}
