package com.digicore.billent.backoffice.service.modules.billers.service;

import com.digicore.billent.data.lib.modules.common.contributor.dto.ProductDto;
import com.digicore.billent.data.lib.modules.common.contributor.service.ContributorOperationService;
import com.digicore.billent.data.lib.modules.common.contributor.service.ContributorProductService;
import com.digicore.billent.data.lib.modules.common.dto.CsvDto;
import com.digicore.billent.data.lib.modules.common.services.CsvService;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.registhentication.registration.enums.Status;
import com.digicore.request.processor.annotations.MakerChecker;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductBackOfficeService implements ProductBackOfficeValidatorService {
  private final ContributorProductService<ProductDto> backOfficeProductServiceImpl;
  private final ContributorOperationService backOfficeProductOperationServiceImpl;
  private final CsvService csvService;

  public PaginatedResponseDTO<ProductDto> getAllProducts(int pageNumber, int pageSize) {
    return backOfficeProductServiceImpl.retrieveAllSystemProducts(pageNumber, pageSize);
  }

  public PaginatedResponseDTO<ProductDto> fetchProductsByStatus(
      BillentSearchRequest billentSearchRequest) {
    return backOfficeProductServiceImpl.filterAllSystemProductsByStatus(billentSearchRequest);
  }

  public void downloadAllProductsInCSV(
      HttpServletResponse response,
      Status productStatus,
      String startDate,
      String endDate,
      String downLoadFormat,
      int pageNumber,
      int pageSize) {
    BillentSearchRequest searchRequest = new BillentSearchRequest();
    searchRequest.setStatus(productStatus);
    searchRequest.setStartDate(startDate);
    searchRequest.setEndDate(endDate);
    searchRequest.setDownloadFormat(downLoadFormat);

    CsvDto<ProductDto> csvDto = new CsvDto<>();
    csvDto.setBillentSearchRequest(searchRequest);
    csvDto.setResponse(response);
    csvDto.setPage(pageNumber);
    csvDto.setPageSize(pageSize);
    csvService.prepareCSVExport(csvDto, backOfficeProductServiceImpl::prepareAllSystemProductCSV);
  }

  @MakerChecker(
      checkerPermission = "approve-enable-biller-product",
      makerPermission = "enable-biller-product",
      requestClassName = "com.digicore.billent.data.lib.modules.common.contributor.dto.ProductDto")
  public Object enableProduct(Object request, Object... args) {
    ProductDto productDto = (ProductDto) request;
    backOfficeProductOperationServiceImpl.enableContributorProduct(productDto.getProductSystemId());
    return Optional.empty();
  }

  @MakerChecker(
      checkerPermission = "approve-disable-biller-product",
      makerPermission = "disable-biller-product",
      requestClassName = "com.digicore.billent.data.lib.modules.common.contributor.dto.ProductDto")
  public Object disableProduct(Object request, Object... args) {
    ProductDto productDto = (ProductDto) request;
    backOfficeProductOperationServiceImpl.disableContributorProduct(
        productDto.getProductSystemId());
    return Optional.empty();
  }
}
