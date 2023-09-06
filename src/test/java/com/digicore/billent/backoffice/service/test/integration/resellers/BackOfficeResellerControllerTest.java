package com.digicore.billent.backoffice.service.test.integration.resellers;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.*;
import static com.digicore.billent.data.lib.modules.common.constants.SystemConstants.CHECKER_EMAIL;
import static com.digicore.billent.data.lib.modules.common.constants.SystemConstants.MAKER_EMAIL;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_SIZE_DEFAULT_VALUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;

import com.digicore.billent.data.lib.modules.billers.repository.BillerRepository;
import com.digicore.billent.data.lib.modules.common.contributor.dto.BackOfficeResellerProfileDTO;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
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
 * @author Oluwatobi Ogunwuyi
 * @createdOn Sep-03(Sun)-2023
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BackOfficeResellerControllerTest {

  // todo write test for the controllers that are missing, since reseller profiles are self
  // onboarding you can call the required repositories to insert all data required for the reseller
  // controller test
  @Autowired private MockMvc mockMvc;

  @Autowired private BillerRepository billerRepository;

  @Autowired private PropertyConfig propertyConfig;

  private static PaginatedResponseDTO<BackOfficeResellerProfileDTO> getPaginatedResponseDTO(
      MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
    ApiResponseJson<PaginatedResponseDTO<BackOfficeResellerProfileDTO>> response =
        ClientUtil.getGsonMapper()
            .fromJson(
                result.getResponse().getContentAsString().trim(),
                new TypeToken<
                    ApiResponseJson<
                        PaginatedResponseDTO<BackOfficeResellerProfileDTO>>>() {}.getType());
    assertTrue(response.isSuccess());

    return response.getData();
  }

  @BeforeEach
  void checkup() throws Exception {
    new H2TestConfiguration(propertyConfig);
    TestHelper testHelper = new TestHelper(mockMvc);
    testHelper.createTestRole();
  }

  @Test
  void testGetAllResellers() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);

    MvcResult mvcResult =
        mockMvc
            .perform(
                get(RESELLERS_API_V1 + "get-all")
                    .param(PAGE_NUMBER, PAGE_NUMBER_DEFAULT_VALUE)
                    .param(PAGE_SIZE, PAGE_SIZE_DEFAULT_VALUE)
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

    PaginatedResponseDTO<BackOfficeResellerProfileDTO> paginatedResponseDTO =
        getPaginatedResponseDTO(mvcResult);

    assertNotNull(paginatedResponseDTO.getContent());
    assertTrue(paginatedResponseDTO.getIsFirstPage());
    assertTrue(paginatedResponseDTO.getIsLastPage());
  }

  @Test
  void testEnableBackOfficeResellerUser_ProfileExists() throws Exception {

    TestHelper testHelper = new TestHelper(mockMvc);
    MvcResult mvcResult =
        mockMvc
            .perform(
                patch(
                        RESELLERS_API_V1 + "enable-{email}-{resellerId}", "testemail@gmail.com", "resellermail@gmail.com")

                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

    ApiResponseJson<?> response =
        ClientUtil.getGsonMapper()
            .fromJson(mvcResult.getResponse().getContentAsString(), ApiResponseJson.class);
    assertTrue(response.isSuccess());
  }
}
