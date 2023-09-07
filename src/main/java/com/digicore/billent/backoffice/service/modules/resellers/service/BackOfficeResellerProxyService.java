package com.digicore.billent.backoffice.service.modules.resellers.service;

import com.digicore.billent.data.lib.modules.common.contributor.service.BackOfficeContributorService;
import com.digicore.billent.data.lib.modules.reseller.dto.ResellerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Ezenwa Opara
 * @createdOn 07/09/2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeResellerProxyService {
  private final BackOfficeResellerValidatorService validatorService;
  private final BackOfficeContributorService backOfficeContributorService;

  public void enableReseller(String resellerId) {
    ResellerDTO resellerDTO = new ResellerDTO();
    resellerDTO.setResellerId(resellerId);
//    backOfficeContributorService.isContributorPresent(resellerId);
     validatorService.enableReseller(resellerDTO);
  }
}
