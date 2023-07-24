package com.digicore.billent.backoffice.service.modules.approvals.service;

import com.digicore.api.helper.exception.ZeusRuntimeException;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.request.processor.dto.ApprovalRequestsDTO;
import com.digicore.request.processor.processors.ApprovalRequestService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Oluwatobi Ogunwuyi
 * @createdOn Nov-21(Mon)-2022
 */

@Service
@RequiredArgsConstructor
public class BackOfficeApprovalService {
    //todo this service was copied from another project and should be refactored properly

    public static final String NO_MAKER_CHECKER="no maker checker available";
    @Autowired(required = false)
    private  ApprovalRequestService approvalRequestService;

    private static PaginatedResponseDTO<ApprovalRequestsDTO> getApprovalRequestsDTOPaginatedUserApiModel(Map<String, Object> approvalRequests, List<ApprovalRequestsDTO> approvalRequestsDTOS) {
        PaginatedResponseDTO<ApprovalRequestsDTO> approvalRequestsDTOPaginatedUserApiModel = new PaginatedResponseDTO<>();
        approvalRequestsDTOPaginatedUserApiModel.setContent(approvalRequestsDTOS);
        approvalRequestsDTOPaginatedUserApiModel.setIsFirstPage((Boolean) approvalRequests.get("isLastPage"));
        approvalRequestsDTOPaginatedUserApiModel.setIsLastPage((Boolean) approvalRequests.get("isFirstPage"));
        approvalRequestsDTOPaginatedUserApiModel.setTotalItems((Long) approvalRequests.get("totalItems"));
        approvalRequestsDTOPaginatedUserApiModel.setCurrentPage((Integer) approvalRequests.get("currentPage"));
        approvalRequestsDTOPaginatedUserApiModel.setTotalPages((Integer) approvalRequests.get("totalPages"));
        return approvalRequestsDTOPaginatedUserApiModel;
    }

    public Object getRequest(@PathVariable Long requestId) throws ZeusRuntimeException{
        return approvalRequestService.getRequest(requestId);

    }

    public PaginatedResponseDTO<ApprovalRequestsDTO> getAllApprovalRequestsDTOS(int page, int size)  {
        if (approvalRequestService != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Map<String,Object> approvalRequests = approvalRequestService.getApprovalRequests(auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),page,size);

            List<ApprovalRequestsDTO> approvalRequestsDTOS = (List<ApprovalRequestsDTO>) approvalRequests.get("content");
            PaginatedResponseDTO<ApprovalRequestsDTO> approvalRequests1 = getRequestsDTOPaginatedUserApiModel(approvalRequests, approvalRequestsDTOS);
            return approvalRequests1 !=null ? approvalRequests1 : new PaginatedResponseDTO<>();
        }
        else
            throw new ZeusRuntimeException(NO_MAKER_CHECKER);

    }

    private PaginatedResponseDTO<ApprovalRequestsDTO> getRequestsDTOPaginatedUserApiModel(Map<String, Object> approvalRequests, List<ApprovalRequestsDTO> approvalRequestsDTOS) {
        if (approvalRequestsDTOS !=null && !approvalRequestsDTOS.isEmpty()) {
            return getApprovalRequestsDTOPaginatedUserApiModel(approvalRequests, approvalRequestsDTOS);
        }
        return null;
    }

    public PaginatedResponseDTO<ApprovalRequestsDTO> getPendingApprovalRequestsDTOS(int page,int size)  {
        if (approvalRequestService != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Map<String,Object> approvalRequests = approvalRequestService.getPendingApprovalRequests(
                    auth.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList()
                    ,page,size
            );

            List<ApprovalRequestsDTO> approvalRequestsDTOS = (List<ApprovalRequestsDTO>) approvalRequests.get("content");
            PaginatedResponseDTO<ApprovalRequestsDTO> approvalRequests1 = getRequestsDTOPaginatedUserApiModel(approvalRequests, approvalRequestsDTOS);
            return approvalRequests1 !=null ? approvalRequests1 : new PaginatedResponseDTO<>();
        }
        else
            throw new ZeusRuntimeException(NO_MAKER_CHECKER);

    }
}
