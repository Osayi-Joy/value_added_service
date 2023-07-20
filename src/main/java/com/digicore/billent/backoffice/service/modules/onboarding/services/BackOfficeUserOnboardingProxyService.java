package com.digicore.billent.backoffice.service.modules.onboarding.services;

import com.digicore.billent.data.lib.modules.common.authentication.dtos.UserRegistrationDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.common.authorization.model.Role;
import com.digicore.billent.data.lib.modules.common.authorization.service.RoleService;
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
    private final RoleService<RoleDTO, Role> roleService;


    public Object onboardNewBackOfficeUser(UserRegistrationDTO userRegistrationDTO) {
        roleService.roleCheck(userRegistrationDTO.getAssignedRole());
        return validatorService.onboardNewBackOfficeUser(userRegistrationDTO);
    }
}
