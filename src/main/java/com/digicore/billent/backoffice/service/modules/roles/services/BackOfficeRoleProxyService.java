package com.digicore.billent.backoffice.service.modules.roles.services;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-20(Thu)-2023
 */

import com.digicore.billent.data.lib.modules.common.authentication.dtos.UserRegistrationDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.PermissionDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleCreationDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.common.authorization.model.Permission;
import com.digicore.billent.data.lib.modules.common.authorization.model.Role;
import com.digicore.billent.data.lib.modules.common.authorization.service.PermissionService;
import com.digicore.billent.data.lib.modules.common.authorization.service.RoleService;
import com.digicore.registhentication.exceptions.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.digicore.billent.data.lib.modules.exception.messages.AuthorizationErrorMessage.PERMISSIONS_REQUIRED_CODE;
import static com.digicore.billent.data.lib.modules.exception.messages.AuthorizationErrorMessage.PERMISSIONS_REQUIRED_MESSAGE;

@Service
@RequiredArgsConstructor
public class BackOfficeRoleProxyService {
 private final BackOfficeRoleValidatorService validatorService;
 private final RoleService<RoleDTO, Role> roleService;
 private final PermissionService<PermissionDTO, Permission> permissionService;
 private final ExceptionHandler<String, String, HttpStatus, String> exceptionHandler;

 public Object createNewRole(RoleCreationDTO roleDTO) {
  if (null == roleDTO.getPermissions() || roleDTO.getPermissions().isEmpty())
        exceptionHandler.processBadRequestException(PERMISSIONS_REQUIRED_MESSAGE,PERMISSIONS_REQUIRED_CODE,PERMISSIONS_REQUIRED_CODE);
  roleService.checkIfRoleIsNotSystemRole(roleDTO.getName());
  permissionService.getValidPermissions(roleDTO.getPermissions());
  return validatorService.createNewRole(roleDTO);
 }
}
