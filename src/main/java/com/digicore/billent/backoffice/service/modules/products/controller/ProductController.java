package com.digicore.billent.backoffice.service.modules.products.controller;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.PRODUCTS_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.billers.service.ProductBackOfficeProxyService;
import com.digicore.billent.backoffice.service.modules.billers.service.ProductBackOfficeService;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.registration.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(PRODUCTS_API_V1)
@Tag(name = PRODUCT_CONTROLLER_TITLE, description = PRODUCT_CONTROLLER_DESCRIPTION)
public class ProductController {
  private final ProductBackOfficeService productBackOfficeService;
  private final ProductBackOfficeProxyService productBackOfficeProxyService;

  @GetMapping("get-all")
  @PreAuthorize("hasAuthority('view-biller-products')")
  @Operation(
      summary = PRODUCT_CONTROLLER_GET_ALL_PRODUCTS_TITLE,
      description = PRODUCT_CONTROLLER_GET_ALL_PRODUCTS_DESCRIPTION)
  public ResponseEntity<Object> viewAllProducts(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize) {
    return ControllerResponse.buildSuccessResponse(
        productBackOfficeService.getAllProducts(pageNumber, pageSize),
        "Retrieved All Products Successfully");
  }

  @GetMapping("export-to-csv")
  @PreAuthorize("hasAuthority('export-biller-products')")
  @Operation(
      summary = PRODUCT_CONTROLLER_EXPORT_PRODUCTS_IN_CSV_TITLE,
      description = PRODUCT_CONTROLLER_EXPORT_PRODUCTS_IN_CSV_DESCRIPTION)
  public void downloadListOfProductsInCSV(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize,
      @RequestParam(value = START_DATE, required = false) String startDate,
      @RequestParam(value = END_DATE, required = false) String endDate,
      @RequestParam(value = PRODUCT_STATUS, required = false) Status productStatus,
      @RequestParam(value = DOWNLOAD_FORMAT, required = false) String downloadFormat,
      HttpServletResponse response) {
    productBackOfficeService.downloadAllProductsInCSV(
        response, productStatus, startDate, endDate, downloadFormat, pageNumber, pageSize);
  }

  @GetMapping("filter-by-status")
  @PreAuthorize("hasAuthority('view-biller-products')")
  @Operation(
      summary = PRODUCT_CONTROLLER_FETCH_PRODUCTS_BY_STATUS_TITLE,
      description = PRODUCT_CONTROLLER_FETCH_PRODUCTS_BY_STATUS_DESCRIPTION)
  public ResponseEntity<Object> filterProductsByStatus(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize,
      @RequestParam(value = START_DATE, required = false) String startDate,
      @RequestParam(value = END_DATE, required = false) String endDate,
      @RequestParam(value = PRODUCT_STATUS, required = false) Status productStatus) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setStatus(productStatus);
    billentSearchRequest.setStartDate(startDate);
    billentSearchRequest.setEndDate(endDate);
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);
    return ControllerResponse.buildSuccessResponse(
        productBackOfficeService.fetchProductsByStatus(billentSearchRequest),
        "Retrieved All Products by Status Successfully");
  }

  @PatchMapping("enable-{productSystemId}")
  @PreAuthorize("hasAuthority('enable-biller-product')")
  @Operation(
      summary = PRODUCT_CONTROLLER_ENABLE_A_PRODUCT_TITLE,
      description = PRODUCT_CONTROLLER_ENABLE_A_PRODUCT_DESCRIPTION)
  public ResponseEntity<Object> enableProduct(@PathVariable String productSystemId) {
    return ControllerResponse.buildSuccessResponse(
        productBackOfficeProxyService.enableProduct(productSystemId),
        "Product enabled successfully");
  }

  @PatchMapping("disable-{productSystemId}")
  @PreAuthorize("hasAuthority('disable-biller-product')")
  @Operation(
      summary = PRODUCT_CONTROLLER_DISABLE_A_PRODUCT_TITLE,
      description = PRODUCT_CONTROLLER_DISABLE_A_PRODUCT_DESCRIPTION)
  public ResponseEntity<Object> disableProduct(@PathVariable String productSystemId) {
    return ControllerResponse.buildSuccessResponse(
        productBackOfficeProxyService.disableProduct(productSystemId),
        "Product disabled successfully");
  }
}
