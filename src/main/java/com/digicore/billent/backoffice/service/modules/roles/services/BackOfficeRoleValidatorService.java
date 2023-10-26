package com.digicore.billent.backoffice.service.modules.roles.services;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-20(Thu)-2023
 */

import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;

public interface BackOfficeRoleValidatorService {

     Object createNewRole(Object requestDTO, Object... args);
     Object deleteRole(Object requestDTO, Object... args);
     Object updateRole(Object requestDTO, Object... args);
     Object disableRole(Object requestDTO, Object... args);
     Object enableRole(Object requestDTO, Object... args);
}
