package com.digicore.billent.backoffice.service.modules.roles.services;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-20(Thu)-2023
 */

import com.digicore.billent.data.lib.modules.common.authentication.dtos.UserRegistrationDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.common.authorization.model.Role;
import com.digicore.billent.data.lib.modules.common.authorization.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackOfficeRoleProxyService {
 private final BackOfficeRoleValidatorService validatorService;
 private final RoleService<RoleDTO, Role> roleService;

 public Object createNewRole(RoleDTO roleDTO) {
  roleService.roleCheck(roleDTO.getName());
  roleService.checkIfRoleIsNotSystemRole(roleDTO.getName());
  return validatorService.createNewRole(roleDTO);
 }
}
