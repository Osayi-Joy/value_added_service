package com.digicore.billent.backoffice.service.modules.roles.processor;

import com.digicore.billent.backoffice.service.modules.onboarding.services.BackOfficeUserOnboardingService;
import com.digicore.billent.backoffice.service.modules.roles.services.BackOfficeRoleService;
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
public class BackOfficeRoleProcessor {
  private final BackOfficeRoleService backOfficeRoleService;

  @RequestType(name = "createNewRole")
  public Object createNewRole(Object request) {
    return backOfficeRoleService.createNewRole(request);
  }

  @RequestType(name = "updateRole")
  public Object updateRole(Object request) {
    return backOfficeRoleService.updateRole(request);
  }

  @RequestType(name = "deleteRole")
  public Object deleteRole(Object request) {
    return backOfficeRoleService.deleteRole(request);
  }

  @RequestType(name = "disableRole")
  public Object disableRole(Object request) {
    return backOfficeRoleService.disableRole(request);
  }

  @RequestType(name = "enableRole")
  public Object enableRole(Object request) {
    return backOfficeRoleService.enableRole(request);
  }

}
