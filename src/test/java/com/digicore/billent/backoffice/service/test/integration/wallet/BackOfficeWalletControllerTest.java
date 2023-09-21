package com.digicore.billent.backoffice.service.test.integration.wallet;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.service.AuthProfileService;
import com.digicore.billent.data.lib.modules.common.wallet.dto.TopUpWalletDTO;
import com.digicore.billent.data.lib.modules.common.wallet.dto.WalletBalanceResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.dto.WalletResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.service.WalletService;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.otp.service.NotificationDispatcher;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.WALLET_API_V1;
import static com.digicore.billent.data.lib.modules.common.constants.SystemConstants.MAKER_EMAIL;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_SIZE_DEFAULT_VALUE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * @author Ademiju Taiwo
 * @createdOn Sept-11(Mon)-2023
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BackOfficeWalletControllerTest {

    @Autowired
    private PropertyConfig propertyConfig;
    @Autowired private AuthProfileService<UserAuthProfileDTO> resellerUserAuthProfileServiceImpl;

    @MockBean
    private NotificationDispatcher notificationDispatcher;
    @MockBean
    private WalletService walletServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    private static String ACCESS_TOKEN="";


    @BeforeEach
    void  checkup() throws Exception {
        new H2TestConfiguration(propertyConfig);
        TestHelper testHelper = new TestHelper(mockMvc);
        when(walletServiceImpl.createWallet(any())).thenReturn(testHelper.initializeWalletResponse("test@test.com"));
        if (ACCESS_TOKEN.isEmpty()){
            testHelper.createBackOfficeProfile(MAKER_EMAIL);
            testHelper.createTestRole();
        }
        ACCESS_TOKEN = testHelper.retrieveValidAccessToken();


    }

  private static PaginatedResponseDTO<WalletResponseData> getPaginatedResponseDTO(MvcResult result)
      throws UnsupportedEncodingException, JsonProcessingException {
    ApiResponseJson<PaginatedResponseDTO<WalletResponseData>> response =
        ClientUtil.getGsonMapper()
            .fromJson(
                result.getResponse().getContentAsString().trim(),
                new TypeToken<
                    ApiResponseJson<PaginatedResponseDTO<WalletResponseData>>>() {}.getType());
    assertTrue(response.isSuccess());

    return response.getData();
    }

    @Test
    void viewWalletBalanceTest() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc);

        MvcResult mvcResult = mockMvc.perform(get(WALLET_API_V1 + "retrieve-WA_TESTID-balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", ACCESS_TOKEN))
                .andExpect(status().isOk())
                .andReturn();
        ApiResponseJson<WalletBalanceResponseData> response =
                ClientUtil.getGsonMapper()
                        .fromJson(mvcResult.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<WalletBalanceResponseData>>() {
                        }.getType());

        assertTrue(response.isSuccess());

    }
    @Test
    void getAllWalletsTests() throws Exception {

        MvcResult mvcResult =
                mockMvc
                        .perform(
                                get(WALLET_API_V1 + "get-all")
                                        .param(PAGE_NUMBER, PAGE_NUMBER_DEFAULT_VALUE)
                                        .param(PAGE_SIZE, PAGE_SIZE_DEFAULT_VALUE)
                                        .param(START_DATE,"2023-07-09")
                                        .param(END_DATE,"2023-09-09")
                                        .header("Authorization", ACCESS_TOKEN))
                        .andExpect(status().isOk())
                        .andReturn();
        ApiResponseJson<PaginatedResponseDTO<WalletResponseData>> response =
                ClientUtil.getGsonMapper()
                        .fromJson(mvcResult.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<PaginatedResponseDTO<WalletResponseData>>>() {
                        }.getType());

        assertTrue(response.isSuccess());
    }

    @Test
    void creditCustomerWalletTest() throws Exception {
        TopUpWalletDTO topUpWalletDTO = new TopUpWalletDTO();
        topUpWalletDTO.setAmount("1000");
        topUpWalletDTO.setSystemWalletID("TEST_ID");
        topUpWalletDTO.setNarration("test");
        topUpWalletDTO.setOrganizationId("WA_01_TEST");
        mockMvc
                .perform(
                        post(WALLET_API_V1 + "credit-position")
                                .content(ClientUtil.getGsonMapper().toJson(topUpWalletDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", ACCESS_TOKEN))
                .andExpect(status().isOk())
                .andReturn();
    }

  @Test
  void searchWalletBySearchKeywordTest() throws Exception {

    MvcResult mvcResult =
        mockMvc
            .perform(
                get(WALLET_API_V1 + "search-wallets")
                    .param(PAGE_NUMBER, PAGE_NUMBER_DEFAULT_VALUE)
                    .param(PAGE_SIZE, PAGE_SIZE_DEFAULT_VALUE)
                    .param(VALUE, "test")
                    .header("Authorization", ACCESS_TOKEN))
            .andExpect(status().isOk())
            .andReturn();
    ApiResponseJson<PaginatedResponseDTO<WalletResponseData>> response =
        ClientUtil.getGsonMapper()
            .fromJson(
                mvcResult.getResponse().getContentAsString().trim(),
                new TypeToken<
                    ApiResponseJson<PaginatedResponseDTO<WalletResponseData>>>() {}.getType());

    assertTrue(response.isSuccess());
        }
}
