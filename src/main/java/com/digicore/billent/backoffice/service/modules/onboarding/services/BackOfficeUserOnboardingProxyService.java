package com.digicore.billent.backoffice.service.modules.onboarding.services;


import com.digicore.billent.data.lib.modules.backoffice.authorization.model.BackOfficeRole;
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


    public Object onboardNewBackOfficeUser(UserRegistrationDTO userRegistrationDTO) {
        backOfficeRoleServiceImpl.checkIfRoleIsNotSystemRole(userRegistrationDTO.getAssignedRole());
        backOfficeRoleServiceImpl.roleCheck(userRegistrationDTO.getAssignedRole());
        return validatorService.onboardNewBackOfficeUser(userRegistrationDTO);
    }
}
