package com.digicore.billent.backoffice.service.test.integration.products;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.PRODUCTS_API_V1;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.model.BillerAggregator;
import com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.model.BillerCategory;
import com.digicore.billent.data.lib.modules.billers.model.Biller;
import com.digicore.billent.data.lib.modules.billers.model.Product;
import com.digicore.billent.data.lib.modules.billers.repository.ProductRepository;
import com.digicore.billent.data.lib.modules.common.contributor.dto.ProductDto;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;

import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.registhentication.registration.enums.Status;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.UnsupportedEncodingException;

import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

/*
 * @author Joy Osayi
 * @createdOn Jul-03(Mon)-2023
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropertyConfig propertyConfig;

    @Autowired
    private ProductRepository productRepository;

    private static PaginatedResponseDTO<ProductDto> getPaginatedResponseDTO(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ApiResponseJson<PaginatedResponseDTO<ProductDto>> response =
                ClientUtil.getGsonMapper()
                        .fromJson(result.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<PaginatedResponseDTO<ProductDto>>>() {}.getType());
        assertTrue(response.isSuccess());

        return response.getData();

    }

    @BeforeEach
    void  checkup() throws Exception {
        new H2TestConfiguration(propertyConfig);
        TestHelper testHelper = new TestHelper(mockMvc);
        testHelper.createTestRole();
    }

    @Test
    void testGetAllProducts() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc);


        MvcResult mvcResult = mockMvc.perform(get(PRODUCTS_API_V1 + "get-all")
                        .param(PAGE_NUMBER, PAGE_NUMBER_DEFAULT_VALUE)
                        .param(PAGE_SIZE, PAGE_SIZE_DEFAULT_VALUE)
                        .header("Authorization",testHelper.retrieveValidAccessToken()))
                .andExpect(status().isOk())
                .andReturn();

        PaginatedResponseDTO<ProductDto> paginatedResponseDTO = getPaginatedResponseDTO(mvcResult);

        assertNotNull(paginatedResponseDTO.getContent());
        assertTrue(paginatedResponseDTO.getIsFirstPage());
        assertTrue(paginatedResponseDTO.getIsLastPage());
    }

    @Test
    void testFetchProductsByStatus() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc);
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        Status productStatus = Status.INACTIVE;

        MvcResult mvcResult = mockMvc.perform(get(PRODUCTS_API_V1 + "filter-by-status")
                        .param(PAGE_NUMBER, PAGE_NUMBER_DEFAULT_VALUE)
                        .param(PAGE_SIZE, PAGE_SIZE_DEFAULT_VALUE)
                        .param(START_DATE, startDate)
                        .param(END_DATE, endDate)
                        .param(PRODUCT_STATUS, productStatus.toString())
                        .header("Authorization",testHelper.retrieveValidAccessToken()))
                .andExpect(status().isOk())
                .andReturn();

        PaginatedResponseDTO<ProductDto> paginatedResponseDTO = getPaginatedResponseDTO(mvcResult);

        assertNotNull(paginatedResponseDTO.getContent());
        assertTrue(paginatedResponseDTO.getIsFirstPage());
        assertTrue(paginatedResponseDTO.getIsLastPage());
    }

    @Test
    void testExportProductsAsCsv() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc);
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        Status productStatus = Status.ACTIVE;
        String downloadFormat = "csv";

        ResultActions result = mockMvc.perform(get(PRODUCTS_API_V1 + "export-to-csv")
                        .param(PAGE_NUMBER, PAGE_NUMBER_DEFAULT_VALUE)
                        .param(PAGE_SIZE, PAGE_SIZE_DEFAULT_VALUE)
                        .param(START_DATE, startDate)
                        .param(END_DATE, endDate)
                        .param(PRODUCT_STATUS, productStatus.toString())
                        .param(DOWNLOAD_FORMAT, downloadFormat)
                        .header("Authorization", testHelper.retrieveValidAccessToken()))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void testDisableProduct_ProductExists() throws Exception {
        Product product = new Product();
        product.setProductSystemId("PSID001");
        product.setProductId("PSID001");
        product.setProductStatus(Status.ACTIVE);
        Biller biller = new Biller();
        BillerCategory billerCategory = new BillerCategory();
        BillerAggregator billerAggregator = new BillerAggregator();
        billerAggregator.setAggregatorStatus(Status.ACTIVE);
        billerCategory.setAggregator(billerAggregator);
        biller.setCategory(billerCategory);
        product.setBiller(biller);

        productRepository.save(product);

        TestHelper testHelper = new TestHelper(mockMvc);
        ProductDto productDto = new ProductDto();
        productDto.setProductId("PSID001");

        MvcResult mvcResult = mockMvc.perform(patch(PRODUCTS_API_V1 + "disable-{productSystemId}", product.getProductSystemId())
                        .content(ClientUtil.getGsonMapper().toJson(productDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", testHelper.retrieveValidAccessToken()))
                .andExpect(status().isOk())
                .andReturn();
        ApiResponseJson<?> response =
                ClientUtil.getGsonMapper()
                        .fromJson(mvcResult.getResponse().getContentAsString(), ApiResponseJson.class);
        assertTrue(response.isSuccess());

    }
    @Test
    void testDisableProduct_ProductNotExists() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc);

        Product productDto = new Product();
        productDto.setProductSystemId("PSID004");
        productDto.setProductId("PSID004");
        productDto.setProductStatus(Status.INACTIVE);
        Biller biller = new Biller();
        BillerCategory billerCategory = new BillerCategory();
        BillerAggregator billerAggregator = new BillerAggregator();
        billerAggregator.setAggregatorStatus(Status.ACTIVE);
        billerCategory.setAggregator(billerAggregator);
        biller.setCategory(billerCategory);
        productDto.setBiller(biller);


        MvcResult mvcResult = mockMvc
                .perform(
                        patch(PRODUCTS_API_V1 + "disable-{productSystemId}", productDto.getProductId())
                                .content(ClientUtil.getGsonMapper().toJson(productDto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", testHelper.retrieveValidAccessToken()))
                .andExpect(status().isBadRequest())
                .andReturn();

        ApiResponseJson<?> response =
                ClientUtil.getGsonMapper()
                        .fromJson(mvcResult.getResponse().getContentAsString(), ApiResponseJson.class);

        assertFalse(response.isSuccess());

    }

    @Test
    void testEnableProduct_ProductExists() throws Exception {

        Product product = new Product();
        product.setProductSystemId("PSID006");
        product.setProductId("PSID006");
        product.setProductStatus(Status.INACTIVE);
        Biller biller = new Biller();
        BillerCategory billerCategory = new BillerCategory();
        BillerAggregator billerAggregator = new BillerAggregator();
        billerAggregator.setAggregatorStatus(Status.ACTIVE);
        billerCategory.setAggregator(billerAggregator);
        biller.setCategory(billerCategory);
        product.setBiller(biller);

        productRepository.save(product);

        TestHelper testHelper = new TestHelper(mockMvc);
        ProductDto productDto = new ProductDto();
        productDto.setProductId("PSID006");

        MvcResult mvcResult = mockMvc.perform(patch(PRODUCTS_API_V1 + "enable-{productSystemId}", productDto.getProductId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", testHelper.retrieveValidAccessToken()))
                .andExpect(status().isOk())
                .andReturn();
        ApiResponseJson<?> response =
                ClientUtil.getGsonMapper()
                        .fromJson(mvcResult.getResponse().getContentAsString(), ApiResponseJson.class);

        assertTrue(response.isSuccess());

    }
    @Test
    void testEnableProduct_ProductNotExists() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc);
        Product productDto = new Product();
        productDto.setProductSystemId("PSID001");
        productDto.setProductId("PSID001");
        productDto.setProductStatus(Status.INACTIVE);
        Biller biller = new Biller();
        BillerCategory billerCategory = new BillerCategory();
        BillerAggregator billerAggregator = new BillerAggregator();
        billerAggregator.setAggregatorStatus(Status.ACTIVE);
        billerCategory.setAggregator(billerAggregator);
        biller.setCategory(billerCategory);
        productDto.setBiller(biller);

        MvcResult mvcResult = mockMvc
                .perform(
                        patch(PRODUCTS_API_V1 + "enable-{productSystemId}", productDto.getProductId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", testHelper.retrieveValidAccessToken()))
                .andExpect(status().isBadRequest())
                .andReturn();

        ApiResponseJson<?> response =
                ClientUtil.getGsonMapper()
                        .fromJson(mvcResult.getResponse().getContentAsString(), ApiResponseJson.class);

        assertFalse(response.isSuccess());

    }


}
