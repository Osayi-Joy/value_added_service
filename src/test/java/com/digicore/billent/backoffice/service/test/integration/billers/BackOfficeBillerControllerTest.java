package com.digicore.billent.backoffice.service.test.integration.billers;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLERS_API_V1;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.billers.dto.BillerDto;
import com.digicore.billent.data.lib.modules.billers.dto.ProductDto;
import com.digicore.billent.data.lib.modules.billers.model.Biller;
import com.digicore.billent.data.lib.modules.billers.repository.BillerRepository;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.service.AuthProfileService;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.registhentication.registration.enums.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
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

/*
 * @author Joy Osayi
 * @createdOn Jul-03(Mon)-2023
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class BackOfficeBillerControllerTest {
    //todo rewrite this test when the controller to save billers is available
//    mvn test -Dspring.profiles.active=test -Dtest="BillerControllerTest"
    @Autowired
    private MockMvc mockMvc;



    @Autowired private BillerRepository billerRepository;

    @Autowired
    private PropertyConfig propertyConfig;

    private static PaginatedResponseDTO<BillerDto> getPaginatedResponseDTO(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ApiResponseJson<PaginatedResponseDTO<BillerDto>> response =
                ClientUtil.getGsonMapper()
                        .fromJson(result.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<PaginatedResponseDTO<BillerDto>>>() {}.getType());
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
    void testGetAllBillers() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc);

        MvcResult mvcResult = mockMvc.perform(get(BILLERS_API_V1 + "get-all")
                        .param(PAGE_NUMBER, PAGE_NUMBER_DEFAULT_VALUE)
                        .param(PAGE_SIZE, PAGE_SIZE_DEFAULT_VALUE)
                        .header("Authorization",testHelper.retrieveValidAccessToken()))
                .andExpect(status().isOk())
                .andReturn();

        PaginatedResponseDTO<BillerDto> paginatedResponseDTO = getPaginatedResponseDTO(mvcResult);

        assertNotNull(paginatedResponseDTO.getContent());
        assertTrue(paginatedResponseDTO.getIsFirstPage());
        assertTrue(paginatedResponseDTO.getIsLastPage());
    }

//    @Test
//    void testExportBillersAsCsv() throws Exception {
//        TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
//        testHelper.updateMakerSelfPermissionByAddingNeededPermission("export-billers");
//        int pageNumber = 0;
//        int pageSize = 10;
//        String startDate = "2023-01-01";
//        String endDate = "2023-12-31";
//        Status billerStatus = Status.ACTIVE;
//        String downloadFormat = "csv";
//
//        ResultActions result = mockMvc.perform(get(BILLERS_API_V1 + "export-to-csv")
//                        .param(PAGE_NUMBER, PAGE_NUMBER_DEFAULT_VALUE)
//                        .param(PAGE_SIZE, PAGE_SIZE_DEFAULT_VALUE)
//                        .param(START_DATE, startDate)
//                        .param(END_DATE, endDate)
//                        .param(BILLER_STATUS, billerStatus.toString())
//                        .param(DOWNLOAD_FORMAT, downloadFormat)
//                        .header("Authorization", testHelper.retrieveValidAccessToken()))
//                .andExpect(status().is2xxSuccessful());
//
//    }

    @Test
    void testFetchBillersByStatus() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc);
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        Status billerStatus = Status.INACTIVE;

        MvcResult mvcResult = mockMvc.perform(get(BILLERS_API_V1 + "filter-by-status")
                        .param(PAGE_NUMBER, PAGE_NUMBER_DEFAULT_VALUE)
                        .param(PAGE_SIZE, PAGE_SIZE_DEFAULT_VALUE)
                        .param(START_DATE, startDate)
                        .param(END_DATE, endDate)
                        .param(BILLER_STATUS, billerStatus.toString())
                        .header("Authorization",testHelper.retrieveValidAccessToken()))
                .andExpect(status().isOk())
                .andReturn();

        PaginatedResponseDTO<BillerDto> paginatedResponseDTO = getPaginatedResponseDTO(mvcResult);

        assertNotNull(paginatedResponseDTO.getContent());
        assertTrue(paginatedResponseDTO.getIsFirstPage());
        assertTrue(paginatedResponseDTO.getIsLastPage());
    }

    @Test
    void testUpdateBiller() throws Exception {
        Biller biller = new Biller();
        biller.setBillerId("EnableBiller_01");
        biller.setBillerName("EnableBiller_01");
        biller.setBillerSystemName("EnableBiller_01");
        biller.setBillerSystemId("BSID009");
        biller.setBillerStatus(Status.ACTIVE);

        billerRepository.save(biller);

        TestHelper testHelper = new TestHelper(mockMvc);
        BillerDto billerDto = new BillerDto();
        billerDto.setBillerSystemId("BSID009");
        billerDto.setBillerId("BILL009");
        billerDto.setBillerName("Biller Name");
        billerDto.setBillerStatus(Status.ACTIVE);

        MvcResult mvcResult = mockMvc.perform(patch(BILLERS_API_V1 + "edit")
                        .content(ClientUtil.getGsonMapper().toJson(billerDto))
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
    void testEnableBiller_BillerExists() throws Exception {
        Biller biller = new Biller();
        biller.setBillerId("EnableBiller_01");
        biller.setBillerName("EnableBiller_01");
        biller.setBillerSystemName("EnableBiller_01");
        biller.setBillerSystemId("BSID001");
        biller.setBillerStatus(Status.INACTIVE);

        billerRepository.save(biller);

        TestHelper testHelper = new TestHelper(mockMvc);
        BillerDto billerDto = new BillerDto();
        billerDto.setBillerSystemId("BSID001");

        MvcResult mvcResult = mockMvc.perform(patch(BILLERS_API_V1 + "enable-{billerSystemId}", billerDto.getBillerSystemId())
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
    void testEnableBiller_BillerNotExists() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc);
        BillerDto billerDto = new BillerDto();
        billerDto.setBillerSystemId("BSID004");

        MvcResult mvcResult = mockMvc
                .perform(
                        patch(BILLERS_API_V1 + "enable-{billerSystemId}", billerDto.getBillerSystemId())
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
    void testDisableBiller_BillerExists() throws Exception {
        Biller biller = new Biller();
        biller.setBillerId("DisableBiller_01");
        biller.setBillerName("DisableBiller_01");
        biller.setBillerSystemName("DisableBiller_01");
        biller.setBillerSystemId("BSID002");
        biller.setBillerStatus(Status.ACTIVE);

        billerRepository.save(biller);

        TestHelper testHelper = new TestHelper(mockMvc);
        BillerDto billerDto = new BillerDto();
        billerDto.setBillerSystemId("BSID002");

        MvcResult mvcResult = mockMvc.perform(patch(BILLERS_API_V1 + "disable-{billerSystemId}", billerDto.getBillerSystemId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", testHelper.retrieveValidAccessToken()))
                .andExpect(status().isOk())
                .andReturn();
        ApiResponseJson<?> response =
                ClientUtil.getGsonMapper()
                        .fromJson(mvcResult.getResponse().getContentAsString(), ApiResponseJson.class);
        assertTrue(response.isSuccess());

    }




//    @Test
//    void testViewABiller() throws Exception {
//        //It requires the save biller endpoint
//        TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
//        testHelper.updateMakerSelfPermissionByAddingNeededPermission("view-billers");
//
//        String billerSystemId = "BSID001";
//
//      MvcResult result =  mockMvc.perform(get(BILLERS_API_V1 + "get-{billerSystemId}-details", billerSystemId)
//                        .header("Authorization", testHelper.retrieveValidAccessToken()))
//                .andExpect(status().isOk()).andReturn();
//
//        ApiResponseJson<BillerDto> response =
//                ClientUtil.getGsonMapper()
//                        .fromJson(result.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<BillerDto>>() {}.getType());
//
//
//        assertTrue(response.isSuccess());
//        assertEquals(billerSystemId, response.getData().getBillerSystemId());
//
//    }

    @Test
    void testDisableBiller_BillerNotExists() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc);
        BillerDto billerDto = new BillerDto();
        billerDto.setBillerSystemId("BSID006");

        MvcResult mvcResult = mockMvc
                .perform(
                        patch(BILLERS_API_V1 + "disable-{billerSystemId}", billerDto.getBillerSystemId())
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
