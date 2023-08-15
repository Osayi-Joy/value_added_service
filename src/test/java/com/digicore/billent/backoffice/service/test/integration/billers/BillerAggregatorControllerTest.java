package com.digicore.billent.backoffice.service.test.integration.billers;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLER_AGGREGATORS_API_V1;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.BackOfficeUserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.backoffice.authentication.service.BackOfficeUserAuthService;
import com.digicore.billent.data.lib.modules.billers.aggregator.dto.BillerAggregatorDTO;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * @author Ezenwa Opara
 * @createdOn 15/08/2023
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BillerAggregatorControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private PropertyConfig propertyConfig;

  @Autowired
  private BackOfficeUserAuthService<BackOfficeUserAuthProfileDTO> backOfficeUserAuthService;

  @BeforeEach
  void setup() {
    new H2TestConfiguration(propertyConfig);
  }

  @Test
  void testGetAllAggregators() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
    testHelper.updateMakerSelfPermissionByAddingNeededPermission("view-biller-aggregators");

    MvcResult mvcResult =
        mockMvc
            .perform(
                get(BILLER_AGGREGATORS_API_V1 + "get-all")
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

    ApiResponseJson<Set<BillerAggregatorDTO>> response =
        ClientUtil.getGsonMapper()
            .fromJson(
                mvcResult.getResponse().getContentAsString().trim(),
                new TypeToken<ApiResponseJson<Set<BillerAggregatorDTO>>>() {}.getType());

    assertTrue(response.isSuccess());
    assertTrue(response.getData().size() > 0);
    assertNotNull(response.getData());
  }

  @Test
  void testGetAllAggregatorsPaginated() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
    testHelper.updateMakerSelfPermissionByAddingNeededPermission("view-biller-aggregators");

    int pageNumber = 0;
    int pageSize = 10;

    MvcResult mvcResult =
        mockMvc
            .perform(
                get(BILLER_AGGREGATORS_API_V1 + "get-all".concat("?paginated=true"))
                    .param("pageNumber", String.valueOf(pageNumber))
                    .param("pageSize", String.valueOf(pageSize))
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

    PaginatedResponseDTO<BillerAggregatorDTO> paginatedResponseDTO =
        getPaginatedResponseDTO(mvcResult);

    assertNotNull(paginatedResponseDTO.getContent());
    assertTrue(paginatedResponseDTO.getIsFirstPage());
    assertTrue(paginatedResponseDTO.getIsLastPage());
    assertNotNull(paginatedResponseDTO.getContent());
  }

  private static PaginatedResponseDTO<BillerAggregatorDTO> getPaginatedResponseDTO(MvcResult result)
      throws UnsupportedEncodingException {
    ApiResponseJson<PaginatedResponseDTO<BillerAggregatorDTO>> response =
        ClientUtil.getGsonMapper()
            .fromJson(
                result.getResponse().getContentAsString().trim(),
                new TypeToken<
                    ApiResponseJson<PaginatedResponseDTO<BillerAggregatorDTO>>>() {}.getType());
    assertTrue(response.isSuccess());
    return response.getData();
  }
}
