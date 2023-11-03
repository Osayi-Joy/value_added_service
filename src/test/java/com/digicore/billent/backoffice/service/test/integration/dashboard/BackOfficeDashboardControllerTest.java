package com.digicore.billent.backoffice.service.test.integration.dashboard;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.DASHBOARD_API_V1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.common.dashboard.dto.DashboardDTO;
import com.digicore.billent.data.lib.modules.common.wallet.dto.CreateWalletResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.service.WalletService;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/*
 * @author Ikechi Ucheagwu
 * @createdOn Sep-13(Wed)-2023
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BackOfficeDashboardControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PropertyConfig propertyConfig;

  @MockBean
  private WalletService walletService;

  @BeforeEach
  void  checkup() throws Exception {
    new H2TestConfiguration(propertyConfig);
    TestHelper testHelper = new TestHelper(mockMvc);
    testHelper.createTestRole();
    CreateWalletResponseData createWalletResponseData = new CreateWalletResponseData();
    createWalletResponseData.setCurrency("NGN");
    createWalletResponseData.setWalletName("Wallet Name");
    createWalletResponseData.setSystemWalletId("865753dcy1");
    createWalletResponseData.setCustomerId("89756rft781");
    createWalletResponseData.setCustomerName("Oluwatobi Ogunwuyi");
    when(walletService.createWallet(any())).thenReturn(createWalletResponseData);
  }

  @Test
  void testViewDashboard() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);

    MvcResult mvcResult = mockMvc.perform(
            get(DASHBOARD_API_V1 + "get-metric-data")
                .header("Authorization", testHelper.retrieveValidAccessToken()))
        .andExpect(status().isOk())
        .andReturn();

    DashboardDTO dashboardDTO = getDashboardDTO(mvcResult);
    assertNotNull(dashboardDTO);
   // assertEquals(2, dashboardDTO.getNumberOfBillersAggregators());
  }

  private static DashboardDTO getDashboardDTO(
      MvcResult result) throws UnsupportedEncodingException {
    ApiResponseJson<DashboardDTO> response =
        ClientUtil.getGsonMapper()
            .fromJson(
                result.getResponse().getContentAsString().trim(),
                new TypeToken<ApiResponseJson<DashboardDTO>>() {
                }.getType());
    assertTrue(response.isSuccess());
    return response.getData();
  }

}
