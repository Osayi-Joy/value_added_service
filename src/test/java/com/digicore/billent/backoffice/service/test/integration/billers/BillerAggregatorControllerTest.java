//package com.digicore.billent.backoffice.service.test.integration.billers;
//
//import com.digicore.api.helper.response.ApiResponseJson;
//import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
//import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
//import com.digicore.billent.data.lib.modules.billers.aggregator.dto.BillerAggregatorDTO;
//import com.digicore.billent.data.lib.modules.billers.aggregator.model.BillerAggregator;
//import com.digicore.billent.data.lib.modules.billers.aggregator.repository.BillerAggregatorRepository;
//import com.digicore.billent.data.lib.modules.billers.dto.BillerDto;
//import com.digicore.billent.data.lib.modules.billers.dto.ProductDto;
//import com.digicore.billent.data.lib.modules.billers.model.Biller;
//import com.digicore.billent.data.lib.modules.billers.repository.BillerRepository;
//import com.digicore.billent.data.lib.modules.common.authentication.dto.UserAuthProfileDTO;
//import com.digicore.billent.data.lib.modules.common.authentication.service.AuthProfileService;
//import com.digicore.common.util.ClientUtil;
//import com.digicore.config.properties.PropertyConfig;
//import com.digicore.registhentication.registration.enums.Status;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLER_AGGREGATORS_API_V1;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * @author Ezenwa Opara
// * @createdOn 16/08/2023
// */
//import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLERS_API_V1;
//import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLER_AGGREGATORS_API_V1;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * @author Ezenwa Opara
// * @createdOn 15/08/2023
// */
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//class BillerAggregatorControllerTest {
//
//    //todo rewrite this test cases
//
//  @Autowired private MockMvc mockMvc;
//
//  @Autowired private AuthProfileService<UserAuthProfileDTO> backOfficeUserAuthServiceImpl;
//
//  @Autowired private PropertyConfig propertyConfig;
//  @Autowired private ObjectMapper objectMapper;
//  @Autowired BillerAggregatorRepository billerAggregatorRepository;
//
//  @BeforeEach
//  void checkup() {
//    new H2TestConfiguration(propertyConfig);
//  }
//
//  @Test
//  void testFetchBillerAggregatorById_Success() throws Exception {
//    TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthServiceImpl);
//    testHelper.updateMakerSelfPermissionByAddingNeededPermission("view-biller-aggregator-details");
//
//    MvcResult mvcResult =
//        mockMvc
//            .perform(
//                get(BILLER_AGGREGATORS_API_V1 + "get-" + aggregatorSystemId + "-details")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .header("Authorization", testHelper.retrieveValidAccessToken()))
//            .andExpect(status().isOk())
//            .andReturn();
//
//    ApiResponseJson<BillerAggregatorDTO> response =
//        objectMapper.readValue(
//            mvcResult.getResponse().getContentAsString().trim(), new TypeReference<>() {});
//    assertTrue(response.isSuccess());
//    assertNotNull(response.getData());
//
//    BillerAggregatorDTO retrievedAggregator = response.getData();
//    assertEquals(aggregatorSystemId, retrievedAggregator.getAggregatorSystemId());
//  }
//
//    @Test
//    void testEnableAggregator_Success() throws Exception {
//        BillerAggregator billerAggregator = new BillerAggregator();
//        billerAggregator.setAggregatorName("EnableAggregator_01");
//        billerAggregator.setAggregatorSystemId("BSID001");
//        billerAggregator.setAggregatorAlias("AGGREGATOR_ALIAS");
//        billerAggregator.setAggregatorStatus(Status.INACTIVE);
//
//        billerAggregatorRepository.save(billerAggregator);
//
//        TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthServiceImpl);
//        testHelper.updateMakerSelfPermissionByAddingNeededPermission("enable-biller-aggregator");
//        BillerAggregatorDTO billerAggregatorDTO = new BillerAggregatorDTO();
//        billerAggregatorDTO.setAggregatorSystemId("BSID001");
//
//        MvcResult mvcResult = mockMvc.perform(patch(BILLER_AGGREGATORS_API_V1 + "enable")
//                        .content(ClientUtil.getGsonMapper().toJson(billerAggregatorDTO))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", testHelper.retrieveValidAccessToken()))
//                .andExpect(status().isOk())
//                .andReturn();
//        ApiResponseJson<?> response =
//                ClientUtil.getGsonMapper()
//                        .fromJson(mvcResult.getResponse().getContentAsString(), ApiResponseJson.class);
//        assertTrue(response.isSuccess());
//    }
//
//    @Test
//    void testEnableAggregator_BillerAggregatorNotExist() throws Exception {
//
//
//        TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthServiceImpl);
//        testHelper.updateMakerSelfPermissionByAddingNeededPermission("enable-biller-aggregator");
//        BillerAggregatorDTO billerAggregatorDTO = new BillerAggregatorDTO();
//        billerAggregatorDTO.setAggregatorSystemId("BSID002");
//
//        MvcResult mvcResult = mockMvc
//                .perform(
//                        patch(BILLER_AGGREGATORS_API_V1 + "enable")
//                                .content(ClientUtil.getGsonMapper().toJson(billerAggregatorDTO))
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .header("Authorization", testHelper.retrieveValidAccessToken()))
//                .andExpect(status().isBadRequest())
//                .andReturn();
//
//        ApiResponseJson<?> response =
//                ClientUtil.getGsonMapper()
//                        .fromJson(mvcResult.getResponse().getContentAsString(), ApiResponseJson.class);
//
//        assertFalse(response.isSuccess());
//    }
//
//
//@Test
//    void testDisableAggregator_Success() throws Exception {
//            BillerAggregator billerAggregator = new BillerAggregator();
//            billerAggregator.setAggregatorName("DisableAggregator_01");
//            billerAggregator.setAggregatorSystemId("BSID0011");
//            billerAggregator.setAggregatorAlias("AGGREGATOR_ALIAS1");
//            billerAggregator.setAggregatorStatus(Status.ACTIVE);
//
//            billerAggregatorRepository.save(billerAggregator);
//
//            TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthServiceImpl);
//            testHelper.updateMakerSelfPermissionByAddingNeededPermission("disable-biller-aggregator");
//            BillerAggregatorDTO billerAggregatorDTO = new BillerAggregatorDTO();
//            billerAggregatorDTO.setAggregatorSystemId("BSID0011");
//
//            MvcResult mvcResult = mockMvc.perform(patch(BILLER_AGGREGATORS_API_V1 + "disable")
//            .content(ClientUtil.getGsonMapper().toJson(billerAggregatorDTO))
//            .contentType(MediaType.APPLICATION_JSON)
//            .header("Authorization", testHelper.retrieveValidAccessToken()))
//            .andExpect(status().isOk())
//            .andReturn();
//            ApiResponseJson<?> response =
//        ClientUtil.getGsonMapper()
//        .fromJson(mvcResult.getResponse().getContentAsString(), ApiResponseJson.class);
//        assertTrue(response.isSuccess());
//        }
//
//@Test
//    void testDisableAggregator_BillerAggregatorNotExist() throws Exception {
//
//
//            TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthServiceImpl);
//            testHelper.updateMakerSelfPermissionByAddingNeededPermission("disable-biller-aggregator");
//            BillerAggregatorDTO billerAggregatorDTO = new BillerAggregatorDTO();
//            billerAggregatorDTO.setAggregatorSystemId("BSID0012");
//
//            MvcResult mvcResult = mockMvc
//            .perform(
//            patch(BILLER_AGGREGATORS_API_V1 + "disable")
//            .content(ClientUtil.getGsonMapper().toJson(billerAggregatorDTO))
//            .contentType(MediaType.APPLICATION_JSON)
//            .header("Authorization", testHelper.retrieveValidAccessToken()))
//            .andExpect(status().isBadRequest())
//            .andReturn();
//
//            ApiResponseJson<?> response =
//        ClientUtil.getGsonMapper()
//        .fromJson(mvcResult.getResponse().getContentAsString(), ApiResponseJson.class);
//
//        assertFalse(response.isSuccess());
//        }
//
//
//@Test
//  void testGetAllAggregators() throws Exception {
//          TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
//          testHelper.updateMakerSelfPermissionByAddingNeededPermission("view-biller-aggregators");
//
//          int pageNumber = 0;
//          int pageSize = 10;
//
//          MvcResult mvcResult =
//          mockMvc
//          .perform(
//          get(BILLER_AGGREGATORS_API_V1 + "get-all".concat("?paginated=true"))
//          .param("pageNumber", String.valueOf(pageNumber))
//          .param("pageSize", String.valueOf(pageSize))
//          .header("Authorization", testHelper.retrieveValidAccessToken()))
//          .andExpect(status().isOk())
//          .andReturn();
//
//          PaginatedResponseDTO<BillerAggregatorDTO> paginatedResponseDTO =
//        getPaginatedResponseDTO(mvcResult);
//
//        assertNotNull(paginatedResponseDTO.getContent());
//        assertTrue(paginatedResponseDTO.getIsFirstPage());
//        assertTrue(paginatedResponseDTO.getIsLastPage());
//        assertNotNull(paginatedResponseDTO.getContent());
//        }
//
//      private static PaginatedResponseDTO<BillerAggregatorDTO> getPaginatedResponseDTO(MvcResult result)
//        throws UnsupportedEncodingException {
//        ApiResponseJson<PaginatedResponseDTO<BillerAggregatorDTO>> response =
//        ClientUtil.getGsonMapper()
//        .fromJson(
//        result.getResponse().getContentAsString().trim(),
//        new TypeToken<
//        ApiResponseJson<PaginatedResponseDTO<BillerAggregatorDTO>>>() {}.getType());
//        assertTrue(response.isSuccess());
//        return response.getData();
//        }
//}
