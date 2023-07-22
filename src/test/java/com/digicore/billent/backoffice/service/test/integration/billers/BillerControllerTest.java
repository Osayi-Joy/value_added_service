package com.digicore.billent.backoffice.service.test.integration.billers;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLERS_API_V1;
import static com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.modules.billers.service.BillerBackOfficeService;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.BackOfficeUserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.backoffice.authentication.service.BackOfficeUserAuthService;
import com.digicore.billent.data.lib.modules.billers.dto.BillerDto;
import com.digicore.common.util.ClientUtil;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.registhentication.registration.enums.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
@Profile("test")
class BillerControllerTest {
    //todo rewrite this test when the controller to save billers is available
    @Autowired
    private MockMvc mockMvc;




    @Autowired private BackOfficeUserAuthService<BackOfficeUserAuthProfileDTO> backOfficeUserAuthService;
    @Test
    void testGetAllBillers() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
        testHelper.updateMakerSelfPermissionByAddingNeededPermission("view-billers");

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

    @Test
    void testFetchBillersByStatus() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
        testHelper.updateMakerSelfPermissionByAddingNeededPermission("view-billers");
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
    void testExportBillersAsCsv() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
        testHelper.updateMakerSelfPermissionByAddingNeededPermission("export-billers");
        int pageNumber = 0;
        int pageSize = 10;
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        Status billerStatus = Status.ACTIVE;
        String downloadFormat = "csv";

        ResultActions result = mockMvc.perform(get(BILLERS_API_V1 + "export-to-csv")
                        .param(PAGE_NUMBER, PAGE_NUMBER_DEFAULT_VALUE)
                        .param(PAGE_SIZE, PAGE_SIZE_DEFAULT_VALUE)
                        .param(START_DATE, startDate)
                        .param(END_DATE, endDate)
                        .param(BILLER_STATUS, billerStatus.toString())
                        .param(DOWNLOAD_FORMAT, downloadFormat)
                        .header("Authorization", testHelper.retrieveValidAccessToken()))
                .andExpect(status().is2xxSuccessful());

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


    private static PaginatedResponseDTO<BillerDto> getPaginatedResponseDTO(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ApiResponseJson<PaginatedResponseDTO<BillerDto>> response =
                ClientUtil.getGsonMapper()
                        .fromJson(result.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<PaginatedResponseDTO<BillerDto>>>() {}.getType());
        assertTrue(response.isSuccess());



        return response.getData();

    }
}
