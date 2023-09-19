package com.digicore.billent.backoffice.service.modules.profiles.processor;

import com.digicore.billent.backoffice.service.modules.profiles.service.BackOfficeUserProfileOperations;
import com.digicore.request.processor.annotations.RequestHandler;
import com.digicore.request.processor.annotations.RequestType;
import com.digicore.request.processor.enums.RequestHandlerType;
import lombok.RequiredArgsConstructor;

/**
 * @author Joy Osayi
 * @createdOn Aug-15(Tue)-2023
 */
@RequestHandler(type = RequestHandlerType.PROCESS_MAKER_REQUESTS)
@RequiredArgsConstructor
public class BackOfficeUserProfileProcessor {
  private final BackOfficeUserProfileOperations backOfficeUserProfileOperations;

  @RequestType(name = "deleteBackofficeProfile")
  public Object deleteBackofficeProfile(Object request) {
    return backOfficeUserProfileOperations.deleteBackofficeProfile(request);
  }

    @RequestType(name = "updateBackofficeProfile")
    public Object updateBackofficeProfile(Object request){
        return backOfficeUserProfileOperations.updateBackofficeProfile(request);
    }

  @RequestType(name = "disableBackofficeProfile")
  public Object disableBackofficeProfile(Object request) {
    return backOfficeUserProfileOperations.disableBackofficeProfile(request);
  }

  @RequestType(name = "enableBackofficeProfile")
  public Object enableBackofficeProfile(Object request) {
    return backOfficeUserProfileOperations.enableBackofficeProfile(request);
  }
}
