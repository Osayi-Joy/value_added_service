package com.digicore.billent.backoffice.service.modules.roles.services;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-20(Thu)-2023
 */

public interface BackOfficeRoleValidatorService {

     Object createNewRole(Object requestDTO, Object... args);
     Object deleteRole(Object requestDTO, Object... args);
     Object updateRole(Object requestDTO, Object... args);
}
