package com.digicore.billent.backoffice.service.test.integration.audit_trails;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.AUDIT_TRAIL_API_V1;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.DOWNLOAD_FORMAT;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.END_DATE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_NUMBER;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_NUMBER_DEFAULT_VALUE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_SIZE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_SIZE_DEFAULT_VALUE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.START_DATE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.VALUE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.request.processor.dto.AuditLogDTO;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/*
 * @author Ikechi Ucheagwu
 * @createdOn Sep-11(Mon)-2023
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BackOfficeAuditTrailsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PropertyConfig propertyConfig;

  @BeforeEach
  void checkup() throws Exception {
    new H2TestConfiguration(propertyConfig);
    TestHelper testHelper = new TestHelper(mockMvc);
    testHelper.createTestRole();
  }

  @Test
  void testFetchFilteredAuditTrails() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);

    MvcResult mvcResult = mockMvc.perform(
            get(AUDIT_TRAIL_API_V1 + "filter").param(PAGE_NUMBER, PAGE_NUMBER_DEFAULT_VALUE)
                .param(PAGE_SIZE, PAGE_SIZE_DEFAULT_VALUE)
                .header("Authorization", testHelper.retrieveValidAccessToken()))
        .andExpect(status().isOk())
        .andReturn();

    PaginatedResponseDTO<AuditLogDTO> paginatedResponseDTO = getPaginatedResponseDTO(mvcResult);

    assertNotNull(paginatedResponseDTO.getContent());
    assertTrue(paginatedResponseDTO.getIsFirstPage());
    assertTrue(paginatedResponseDTO.getIsLastPage());
  }

  @Test
  void testSearchAuditTrails() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);

    MvcResult mvcResult = mockMvc.perform(
            get(AUDIT_TRAIL_API_V1 + "search").param(PAGE_NUMBER, PAGE_NUMBER_DEFAULT_VALUE)
                .param(PAGE_SIZE, PAGE_SIZE_DEFAULT_VALUE)
                .param(VALUE, "")
                .header("Authorization", testHelper.retrieveValidAccessToken()))
        .andExpect(status().isOk())
        .andReturn();

    ApiResponseJson<?> response =
        ClientUtil.getGsonMapper()
            .fromJson(mvcResult.getResponse().getContentAsString().trim(), ApiResponseJson.class);

    assertTrue(response.isSuccess());
  }

  @Test
  void testExportProductsAsCsv() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);

    mockMvc.perform(get(AUDIT_TRAIL_API_V1 + "export-to-csv")
            .param(PAGE_NUMBER, PAGE_NUMBER_DEFAULT_VALUE)
            .param(PAGE_SIZE, PAGE_SIZE_DEFAULT_VALUE)
            .param(START_DATE, "2023-05-20")
            .param(END_DATE, "2023-11-19")
            .param("activity", "LOGIN_SUCCESS")
            .param(DOWNLOAD_FORMAT, "csv")
            .header("Authorization", testHelper.retrieveValidAccessToken()))
        .andExpect(status().is2xxSuccessful());
  }

  private static PaginatedResponseDTO<AuditLogDTO> getPaginatedResponseDTO(
      MvcResult result) throws UnsupportedEncodingException {
    ApiResponseJson<PaginatedResponseDTO<AuditLogDTO>> response =
        ClientUtil.getGsonMapper()
            .fromJson(
                result.getResponse().getContentAsString().trim(),
                new TypeToken<
                    ApiResponseJson<PaginatedResponseDTO<AuditLogDTO>>>() {
                }.getType());
    assertTrue(response.isSuccess());
    return response.getData();
  }
}
