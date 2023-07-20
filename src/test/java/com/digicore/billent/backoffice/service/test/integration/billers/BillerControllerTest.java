package com.digicore.billent.backoffice.service.test.integration.billers;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLERS_API_V1;
import static com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class BillerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired private BackOfficeUserAuthService<BackOfficeUserAuthProfileDTO> backOfficeUserAuthService;

    @Test
    void testGetAllBillers() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
        testHelper.updateMakerSelfPermissionByAddingNeededPermission("view-billers");
        int pageNumber = 0;
        int pageSize = 10;

        MvcResult mvcResult = mockMvc.perform(get(BILLERS_API_V1 + "get-all-billers")
                        .param(PAGE_NUMBER, String.valueOf(pageNumber))
                        .param(PAGE_SIZE, String.valueOf(pageSize))
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
        int pageNumber = 0;
        int pageSize = 10;
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        Status billerStatus = Status.ACTIVE;

        MvcResult mvcResult = mockMvc.perform(get(BILLERS_API_V1 + "filter-billers-by-billerStatus")
                        .param(PAGE_NUMBER, String.valueOf(pageNumber))
                        .param(PAGE_SIZE, String.valueOf(pageSize))
                        .param(START_DATE, startDate)
                        .param(END_DATE, endDate)
                        .param("billerStatus", billerStatus.toString())
                        .header("Authorization",testHelper.retrieveValidAccessToken()))
                .andExpect(status().isOk())
                .andReturn();

        PaginatedResponseDTO<BillerDto> paginatedResponseDTO = getPaginatedResponseDTO(mvcResult);

        assertNotNull(paginatedResponseDTO.getContent());
        assertTrue(paginatedResponseDTO.getIsFirstPage());
        assertTrue(paginatedResponseDTO.getIsLastPage());
    }
    
    private static PaginatedResponseDTO<BillerDto> getPaginatedResponseDTO(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ApiResponseJson<PaginatedResponseDTO<BillerDto>> response =
                ClientUtil.getGsonMapper()
                        .fromJson(result.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<PaginatedResponseDTO<BillerDto>>>() {}.getType());
        assertTrue(response.isSuccess());



        return response.getData();

    }
}
