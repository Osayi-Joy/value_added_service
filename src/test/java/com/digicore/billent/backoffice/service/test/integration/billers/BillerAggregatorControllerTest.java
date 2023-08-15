package com.digicore.billent.backoffice.service.test.integration.billers;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLER_AGGREGATORS_API_V1;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.billers.aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.billers.aggregator.repository.BillerAggregatorRepository;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.service.AuthProfileService;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
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

/**
 * @author Ezenwa Opara
 * @createdOn 15/08/2023
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BillerAggregatorControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private AuthProfileService<UserAuthProfileDTO> backOfficeUserAuthServiceImpl;


  @Autowired private PropertyConfig propertyConfig;

  @BeforeEach
  void setup() {
    new H2TestConfiguration(propertyConfig);
  }

  @Test
  void testUpdateBillerAggregatorDetail_Success() throws Exception {
    BillerAggregatorDTO billerAggregatorDTO = new BillerAggregatorDTO();
    billerAggregatorDTO.setAggregatorSystemId("ID123");
    billerAggregatorDTO.setAggregatorAlias("ALIAS");

    TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthServiceImpl);
    testHelper.updateMakerSelfPermissionByAddingNeededPermission("edit-biller-aggregator");

    MvcResult mvcResult =
        mockMvc
            .perform(
                patch(BILLER_AGGREGATORS_API_V1 + "edit")
                    .content(ClientUtil.getGsonMapper().toJson(billerAggregatorDTO))
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
