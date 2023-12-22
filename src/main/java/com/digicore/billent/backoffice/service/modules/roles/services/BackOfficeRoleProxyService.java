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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.digicore.billent.data.lib.modules.exception.messages.AuthorizationErrorMessage.*;
import static com.digicore.billent.data.lib.modules.exception.messages.AuthorizationErrorMessage.PERMISSION_NOT_IN_SYSTEM_CODE_KEY;

@Service
@Slf4j
@RequiredArgsConstructor
public class BackOfficeRoleProxyService {
 private final BackOfficeRoleValidatorService validatorService;
 private final RoleService<RoleDTO, BackOfficeRole> backOfficeRoleServiceImpl;
 private final BackOfficeRoleRepository backOfficeRoleRepository;
 private final PermissionService<PermissionDTO, BackOfficePermission> backOfficePermissionServiceImpl;
 private final ExceptionHandler<String, String, HttpStatus, String> exceptionHandler;
 private final SettingService settingService;

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
     if (!backOfficePermissionServiceImpl.retrieveAllSystemPermissionNames().containsAll(roleDTO.getPermissions())){
         throw exceptionHandler.processBadRequestException(
                 settingService.retrieveValue(PERMISSION_NOT_IN_SYSTEM_MESSAGE_KEY),
                 settingService.retrieveValue(PERMISSION_NOT_IN_SYSTEM_CODE_KEY));
     }
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
     BackOfficeRole role = backOfficeRoleRepository.findFirstByNameAndIsDeletedOrderByCreatedDate(
             roleName, false).orElseThrow(() ->
             exceptionHandler.processBadRequestException(
                     INVALID_ROLE_MESSAGE_KEY,
                     INVALID_ROLE_CODE_KEY
             ));
     log.info("****{}**{}***", role.getName(), role.getPermissions());
     if (role.isActive()) {
         throw exceptionHandler.processBadRequestException(
                 ROLE_ALREADY_ACTIVE_MESSAGE_KEY,
                 ROLE_ALREADY_ACTIVE_CODE_KEY);
     }
     RoleDTO roleDTO = new RoleDTO();
     roleDTO.setName(roleName);
     validatorService.enableRole(roleDTO);
    }
}
