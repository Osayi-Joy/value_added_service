package com.digicore.billent.backoffice.service.modules.approvals.controller;



import com.digicore.api.helper.exception.ZeusRuntimeException;
import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.approvals.service.BackOfficeApprovalService;
import com.digicore.request.processor.annotations.TokenValid;
import com.digicore.request.processor.dto.ApprovalRequestsDTO;
import com.digicore.request.processor.processors.ApprovalRequestProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.digicore.billent.backoffice.service.modules.approvals.service.BackOfficeApprovalService.NO_MAKER_CHECKER;
import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.APPROVAL_API_V1;

/**
 * @author Oluwatobi Ogunwuyi
 * @createdOn Oct-30(Sun)-2022
 */

@RestController
@RequestMapping(APPROVAL_API_V1)
@RequiredArgsConstructor
public class ApprovalController {
    //todo this controller was copied from another project and should be refactored properly

    @Autowired(required = false)
    private  ApprovalRequestProcessor approvalRequestProcessor;

   private final BackOfficeApprovalService backOfficeApprovalService;

    @TokenValid()
    @PostMapping("treat-request-{requestId}")
    public ResponseEntity<Object> approveRequest(@PathVariable Long requestId) throws ZeusRuntimeException{
        ApprovalRequestsDTO approvalRequestsDTO = ApprovalRequestsDTO.builder()
                .approvalRequestType("approveRequest")
                .id(requestId)
                .build();
        if (approvalRequestProcessor != null)
         return ControllerResponse.buildSuccessResponse(approvalRequestProcessor.process(approvalRequestsDTO),null);
        else
            throw new ZeusRuntimeException(NO_MAKER_CHECKER);
    }

    @TokenValid()
    @PostMapping("decline-request-{requestId}")
    public ResponseEntity<Object> declineRequest(@PathVariable Long requestId) throws ZeusRuntimeException{
        ApprovalRequestsDTO approvalRequestsDTO = ApprovalRequestsDTO.builder()
                .approvalRequestType("declineRequest")
                .id(requestId)
                .build();
        if (approvalRequestProcessor != null)
            return ControllerResponse.buildSuccessResponse(approvalRequestProcessor.process(approvalRequestsDTO),null);
        else
            throw new ZeusRuntimeException(NO_MAKER_CHECKER);
    }

    @TokenValid()
    @GetMapping("get-request-{requestId}")
    public ResponseEntity<Object> getRequest(@PathVariable Long requestId) throws ZeusRuntimeException{
       return ControllerResponse.buildSuccessResponse(backOfficeApprovalService.getRequest(requestId),null);

    }


    @TokenValid()
    @GetMapping("get-treated-request")
    public ResponseEntity<Object> getTreatedRequest(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name = "pageSize", defaultValue = "5") int pageSize)  {
        return ControllerResponse.buildSuccessResponse(backOfficeApprovalService.getAllApprovalRequestsDTOS(pageNumber,pageSize),null);


    }

    @TokenValid()
    @GetMapping("get-pending-request")
    public ResponseEntity<Object> getPendingRequest(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name = "pageSize", defaultValue = "5") int pageSize)  {
        return ControllerResponse.buildSuccessResponse(backOfficeApprovalService.getPendingApprovalRequestsDTOS(pageNumber,pageSize),null);


    }






}
