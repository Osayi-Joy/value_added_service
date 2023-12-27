package com.digicore.billent.backoffice.service.modules.onboarding.services;


import com.digicore.billent.data.lib.modules.backoffice.authentication.service.BackofficeUsernameEmailValidationService;
import com.digicore.billent.data.lib.modules.backoffice.authorization.model.BackOfficeRole;
import com.digicore.billent.data.lib.modules.backoffice.registration.services.BackOfficeServiceUserRegistrationService;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.common.authorization.service.RoleService;
import com.digicore.billent.data.lib.modules.common.registration.dto.UserRegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-20(Thu)-2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeUserOnboardingProxyService {

    private final BackOfficeUserOnboardingValidatorService validatorService;
    private final RoleService<RoleDTO, BackOfficeRole> backOfficeRoleServiceImpl;
    private final BackOfficeServiceUserRegistrationService backOfficeServiceUserRegistrationService;
    private  final BackofficeUsernameEmailValidationService backofficeUsernameEmailValidationServiceImpl;
    public Object onboardNewBackOfficeUser(UserRegistrationDTO userRegistrationDTO) {
        backofficeUsernameEmailValidationServiceImpl.validateUsernameAndEmail(userRegistrationDTO.getUsername(), userRegistrationDTO.getEmail());
        backofficeUsernameEmailValidationServiceImpl.validateUsernameExist(userRegistrationDTO.getUsername());
        backOfficeRoleServiceImpl.checkIfRoleIsNotSystemRole(userRegistrationDTO.getAssignedRole());
        backOfficeRoleServiceImpl.roleCheck(userRegistrationDTO.getAssignedRole());
        backOfficeServiceUserRegistrationService.doProfileCheck(userRegistrationDTO.getEmail());
        return validatorService.onboardNewBackOfficeUser(userRegistrationDTO);
    }

}
