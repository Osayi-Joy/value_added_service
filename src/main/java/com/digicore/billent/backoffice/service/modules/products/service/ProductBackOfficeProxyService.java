package com.digicore.billent.backoffice.service.modules.products.service;

import com.digicore.billent.data.lib.modules.billers.dto.ProductDto;
import com.digicore.billent.data.lib.modules.billers.model.Product;
import com.digicore.billent.data.lib.modules.billers.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * @author Joy Osayi
 * @createdOn Jul-26(Wed)-2023
 */
@Service
@RequiredArgsConstructor
public class ProductBackOfficeProxyService {
    private final ProductService<ProductDto, Product> productService;
    private final ProductBackOfficeValidatorService validatorService;

    public Object enableProduct(String productSystemId){
        ProductDto productDto = new ProductDto();
        productDto.setProductSystemId(productSystemId);
        productService.isProductPresent(productDto.getProductSystemId());
        return validatorService.enableProduct(productDto);
    }

    public Object disableProduct(String productSystemId){
        ProductDto productDto = new ProductDto();
        productDto.setProductSystemId(productSystemId);
        productService.isProductPresent(productDto.getProductSystemId());
        return validatorService.disableProduct(productDto);
    }
}
