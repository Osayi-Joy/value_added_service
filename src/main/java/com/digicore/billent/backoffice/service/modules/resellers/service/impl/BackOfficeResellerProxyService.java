package com.digicore.billent.backoffice.service.modules.resellers.service.impl;

import com.digicore.billent.backoffice.service.modules.resellers.service.BackOfficeUserResellerValidatorService;
import com.digicore.billent.data.lib.modules.backoffice.reseller.implementation.BackOfficeResellerServiceImpl;
import com.digicore.billent.data.lib.modules.common.contributor.dto.BackOfficeResellerUserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Ezenwa Opara
 * @createdOn 05/09/2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeResellerProxyService {

  private final BackOfficeResellerServiceImpl backOfficeResellerService;
  private final BackOfficeUserResellerValidatorService backOfficeUserResellerValidatorService;

  public void enableBackofficeResellerUser(BackOfficeResellerUserProfileDto backOfficeResellerUserProfileDto) {
    backOfficeResellerService.profileExistenceCheckByEmail(backOfficeResellerUserProfileDto.getResellerUserEmail());
    backOfficeUserResellerValidatorService.enableContributorUser(backOfficeResellerUserProfileDto);
  }
}
