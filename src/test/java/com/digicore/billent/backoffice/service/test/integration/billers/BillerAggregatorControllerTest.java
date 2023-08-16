package com.digicore.billent.backoffice.service.test.integration.billers;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.billers.aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.billers.aggregator.model.BillerAggregator;
import com.digicore.billent.data.lib.modules.billers.aggregator.repository.BillerAggregatorRepository;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.service.AuthProfileService;
import com.digicore.config.properties.PropertyConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLER_AGGREGATORS_API_V1;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ezenwa Opara
 * @createdOn 16/08/2023
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BillerAggregatorControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private AuthProfileService<UserAuthProfileDTO> backOfficeUserAuthServiceImpl;

  @Autowired private PropertyConfig propertyConfig;
  @Autowired private ObjectMapper objectMapper;
  @Autowired BillerAggregatorRepository billerAggregatorRepository;

  @BeforeEach
  void checkup() {
    new H2TestConfiguration(propertyConfig);
  }

  @Test
  void testFetchBillerAggregatorById_Success() throws Exception {
    String aggregatorSystemId = "AGG001";
    BillerAggregator savedAggregator = new BillerAggregator();
    savedAggregator.setAggregatorAlias("AGGREGATOR_ALIAS");
    savedAggregator.setAggregatorSystemId(aggregatorSystemId);
    billerAggregatorRepository.save(savedAggregator);

    TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthServiceImpl);
    testHelper.updateMakerSelfPermissionByAddingNeededPermission("view-biller-aggregator-details");

    MvcResult mvcResult =
        mockMvc
            .perform(
                get(BILLER_AGGREGATORS_API_V1 + "get-" + aggregatorSystemId + "-details")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

    ApiResponseJson<BillerAggregatorDTO> response =
        objectMapper.readValue(
            mvcResult.getResponse().getContentAsString().trim(), new TypeReference<>() {});
    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    BillerAggregatorDTO retrievedAggregator = response.getData();
    assertEquals(aggregatorSystemId, retrievedAggregator.getAggregatorSystemId());
  }
}
