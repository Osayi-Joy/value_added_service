package com.digicore.billent.backoffice.service.modules.billers.service.impl;

import com.digicore.billent.backoffice.service.modules.billers.service.ProductBackOfficeValidatorService;
import com.digicore.billent.data.lib.modules.billers.dto.BillerDto;
import com.digicore.billent.data.lib.modules.billers.dto.ProductDto;
import com.digicore.billent.data.lib.modules.billers.model.Biller;
import com.digicore.billent.data.lib.modules.billers.model.Product;
import com.digicore.billent.data.lib.modules.billers.service.BillerService;
import com.digicore.billent.data.lib.modules.billers.service.ProductService;
import com.digicore.billent.data.lib.modules.common.dto.CsvDto;
import com.digicore.billent.data.lib.modules.common.services.CsvService;
import com.digicore.billent.data.lib.modules.common.util.SearchRequest;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.registhentication.registration.enums.Status;
import com.digicore.request.processor.annotations.MakerChecker;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/*
 * @author Joy Osayi
 * @createdOn Jul-07(Fri)-2023
 */
@Service
@RequiredArgsConstructor
public class ProductBackOfficeService implements ProductBackOfficeValidatorService {
    private final ProductService<ProductDto, Product> productService;
    private final CsvService csvService;


    public PaginatedResponseDTO<ProductDto> getAllProducts(int pageNumber, int pageSize){
        return productService.retrieveAllProducts(pageNumber, pageSize);
    }
    public PaginatedResponseDTO<ProductDto> fetchProductsByStatus(Status productStatus, String startDate, String endDate, int pageNumber, int pageSize){
        return productService.filterProductsByStatus(productStatus, startDate, endDate, pageNumber, pageSize);
    }

    public void downloadAllProductsInCSV(HttpServletResponse response,
                                        Status productStatus, String startDate, String endDate, String downLoadFormat,
                                        int pageNumber, int pageSize) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setStatus(productStatus);
        searchRequest.setStartDate(startDate);
        searchRequest.setEndDate(endDate);
        searchRequest.setDownloadFormat(downLoadFormat);

        CsvDto<ProductDto> csvDto = new CsvDto<>();
        csvDto.setSearchRequest(searchRequest);
        csvDto.setResponse(response);
        csvDto.setPage(pageNumber);
        csvDto.setPageSize(pageSize);
        csvService.prepareCSVExport(csvDto, productService::prepareProductCSV);
    }

    @MakerChecker(
            checkerPermission = "approve-enable-biller-product",
            makerPermission = "enable-biller-product",
            requestClassName = "com.digicore.billent.data.lib.modules.billers.dto.ProductDto")
    public Object enableProduct(Object request, Object... args) {
        return productService.enableProduct((ProductDto) request);
    }

    @MakerChecker(
            checkerPermission = "approve-disable-biller-product",
            makerPermission = "disable-biller-product",
            requestClassName = "com.digicore.billent.data.lib.modules.billers.dto.ProductDto")
    public Object disableProduct(Object request, Object... args) {
        return productService.disableProduct((ProductDto) request);
    }

}
