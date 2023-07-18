package com.digicore.billent.backoffice.service.modules.onboarding.processor;

import com.digicore.billent.backoffice.service.modules.onboarding.services.BackOfficeUserOnboardingService;
import com.digicore.request.processor.annotations.RequestHandler;
import com.digicore.request.processor.annotations.RequestType;
import com.digicore.request.processor.enums.RequestHandlerType;
import lombok.RequiredArgsConstructor;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-04(Tue)-2023
 */
@RequestHandler(type = RequestHandlerType.PROCESS_MAKER_REQUESTS)
@RequiredArgsConstructor
public class BackOfficeUserOnboardingProcessor {
  private final BackOfficeUserOnboardingService backOfficeUserOnboardingService;

  @RequestType(name = "onboardNewBackOfficeUser")
  public Object onboardNewBackOfficeUser(Object request) {
    return backOfficeUserOnboardingService.onboardNewBackOfficeUser(request);
  }
}
