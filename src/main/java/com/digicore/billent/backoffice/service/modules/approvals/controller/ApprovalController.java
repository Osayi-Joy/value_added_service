package com.digicore.billent.backoffice.service.modules.approvals.controller;



import com.digicore.api.helper.exception.ZeusRuntimeException;
import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.approvals.service.BackOfficeApprovalService;
import com.digicore.billent.backoffice.service.util.SwaggerDocUtil;
import com.digicore.request.processor.annotations.TokenValid;
import com.digicore.request.processor.dto.ApprovalRequestsDTO;
import com.digicore.request.processor.processors.ApprovalRequestProcessor;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.digicore.billent.backoffice.service.modules.approvals.service.BackOfficeApprovalService.NO_MAKER_CHECKER;
import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.APPROVAL_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil.*;

/**
 * @author Oluwatobi Ogunwuyi
 * @createdOn Oct-30(Sun)-2022
 */

@RestController
@RequestMapping(APPROVAL_API_V1)
@RequiredArgsConstructor
@Tag(name = APPROVAL_CONTROLLER_TITLE,
        description = APPROVAL_CONTROLLER_DESCRIPTION)
public class ApprovalController {
    //todo this controller was copied from another project and should be refactored properly

    @Autowired(required = false)
    private  ApprovalRequestProcessor approvalRequestProcessor;

   private final BackOfficeApprovalService backOfficeApprovalService;

    @TokenValid()
    @PostMapping("treat-request-{requestId}")
    @Operation(summary = APPROVAL_CONTROLLER_APPROVE_REQUEST_TITLE,description = APPROVAL_CONTROLLER_APPROVE_REQUEST_DESCRIPTION)
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
    @Operation(summary = APPROVAL_CONTROLLER_DECLINE_REQUEST_TITLE,description = APPROVAL_CONTROLLER_DECLINE_REQUEST_DESCRIPTION)
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
    @Operation(summary = APPROVAL_CONTROLLER_GET_REQUEST_TITLE,description = APPROVAL_CONTROLLER_GET_REQUEST_DESCRIPTION)
    public ResponseEntity<Object> getRequest(@PathVariable Long requestId) throws ZeusRuntimeException{
       return ControllerResponse.buildSuccessResponse(backOfficeApprovalService.getRequest(requestId),null);

    }


    @TokenValid()
    @GetMapping("get-treated-request")
    @Operation(summary = APPROVAL_CONTROLLER_GET_TREATED_REQUEST_TITLE,description = APPROVAL_CONTROLLER_GET_TREATED_REQUEST_DESCRIPTION)
    public ResponseEntity<Object> getTreatedRequest(@RequestParam(name = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE) int pageNumber, @RequestParam(name = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE) int pageSize)  {
        return ControllerResponse.buildSuccessResponse(backOfficeApprovalService.getAllApprovalRequestsDTOS(pageNumber,pageSize),null);


    }

    @TokenValid()
    @GetMapping("get-pending-request")
    @Operation(summary = APPROVAL_CONTROLLER_GET_PENDING_REQUEST_TITLE,description = APPROVAL_CONTROLLER_GET_PENDING_REQUEST_DESCRIPTION)
    public ResponseEntity<Object> getPendingRequest(@RequestParam(name = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE) int pageNumber, @RequestParam(name = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE) int pageSize)  {
        return ControllerResponse.buildSuccessResponse(backOfficeApprovalService.getPendingApprovalRequestsDTOS(pageNumber,pageSize),null);


    }






}
