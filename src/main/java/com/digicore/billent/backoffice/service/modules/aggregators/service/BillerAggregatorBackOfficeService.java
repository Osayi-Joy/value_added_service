package com.digicore.billent.backoffice.service.modules.aggregators.service;


import com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.service.BillerAggregatorService;
import com.digicore.billent.data.lib.modules.billers.dto.BillerDto;
import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.registhentication.exceptions.ExceptionHandler;
import com.digicore.registhentication.registration.enums.Status;
import com.digicore.request.processor.annotations.MakerChecker;
import com.digicore.request.processor.processors.AuditLogProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.digicore.billent.data.lib.modules.exception.messages.BillerAggregatorErrorMessage.INVALID_AGGREGATOR_STATUS_CODE_KEY;
import static com.digicore.billent.data.lib.modules.exception.messages.BillerAggregatorErrorMessage.INVALID_AGGREGATOR_STATUS_MESSAGE_KEY;

/**
 * @author Ezenwa Opara
 * @createdOn 15/08/2023
 */
@Service
@RequiredArgsConstructor
public class BillerAggregatorBackOfficeService
    implements BillerAggregatorBackOfficeValidatorService {
  private final BillerAggregatorService billerAggregatorServiceImpl;
  private final AuditLogProcessor auditLogProcessor;
  private final ExceptionHandler<String, String, HttpStatus, String> exceptionHandler;




  @MakerChecker(
      checkerPermission = "approve-enable-biller-aggregator",
      makerPermission = "enable-biller-aggregator",
      requestClassName = "com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.dto.BillerAggregatorDTO")
  @Override
  public Object enableBillerAggregator(Object request, Object... args) {
    BillerAggregatorDTO billerAggregatorDTO = (BillerAggregatorDTO) request;
    billerAggregatorServiceImpl.enableBillerAggregator(billerAggregatorDTO);
    auditLogProcessor.saveAuditWithDescription(
            AuditLogActivity.APPROVE_ENABLE_BILLER_AGGREGATOR,
            AuditLogActivity.BACKOFFICE,
            AuditLogActivity.APPROVE_ENABLE_BILLER_AGGREGATOR_DESCRIPTION.replace("{}", billerAggregatorDTO.getAggregatorSystemId()));
    return Optional.empty();
  }

  @MakerChecker(
          checkerPermission = "approve-disable-biller-aggregator",
          makerPermission = "disable-biller-aggregator",
          requestClassName = "com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.dto.BillerAggregatorDTO")
  @Override
  public Object disableBillerAggregator(Object request, Object... args) {
    BillerAggregatorDTO billerAggregatorDTO = (BillerAggregatorDTO) request;
    billerAggregatorServiceImpl.disableBillerAggregator((BillerAggregatorDTO) request);
    auditLogProcessor.saveAuditWithDescription(
            AuditLogActivity.APPROVE_DISABLE_BILLER_AGGREGATOR,
            AuditLogActivity.BACKOFFICE,
            AuditLogActivity.APPROVE_DISABLE_BILLER_AGGREGATOR_DESCRIPTION.replace("{}", billerAggregatorDTO.getAggregatorSystemId()));
    return Optional.empty();
  }

  @MakerChecker(
          checkerPermission = "approve-edit-biller-aggregator",
          makerPermission = "edit-biller-aggregator",
          requestClassName = "com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.dto.BillerAggregatorDTO")
  @Override
  public Object updateBillerAggregatorDetail(Object request, Object... args) {
    BillerAggregatorDTO billerAggregatorDTO = (BillerAggregatorDTO) request;
    List<Status> statusList = Arrays.asList(Status.values());

    if(!statusList.contains(billerAggregatorDTO.getAggregatorStatus())){
      throw exceptionHandler.processBadRequestException(INVALID_AGGREGATOR_STATUS_MESSAGE_KEY,INVALID_AGGREGATOR_STATUS_CODE_KEY);
    }
    billerAggregatorServiceImpl.editBillerAggregator((BillerAggregatorDTO)request);
    auditLogProcessor.saveAuditWithDescription(
            AuditLogActivity.APPROVE_EDIT_BILLER_AGGREGATOR,
            AuditLogActivity.BACKOFFICE,
            AuditLogActivity.APPROVE_EDIT_BILLER_AGGREGATOR_DESCRIPTION.replace("{}", billerAggregatorDTO.getAggregatorName()));
    return Optional.empty();
  }

  public PaginatedResponseDTO<BillerDto> viewBillersUnderAnAggregator(int pageNumbe, int pageSize, String aggregatorSystemId){
    return billerAggregatorServiceImpl.retrieveAllBillersUnderAnAggregator(pageNumbe, pageSize, aggregatorSystemId);
  }
  public PaginatedResponseDTO<BillerAggregatorDTO> fetchFilteredAggregators(BillentSearchRequest billentSearchRequest){
    return billerAggregatorServiceImpl.filterBillerAggregator(billentSearchRequest);
  }
  public PaginatedResponseDTO<BillerAggregatorDTO> searchAggregators(BillentSearchRequest billentSearchRequest){
    return billerAggregatorServiceImpl.searchBillerAggregators(billentSearchRequest);
  }
}
