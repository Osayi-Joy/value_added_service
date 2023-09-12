package com.digicore.billent.backoffice.service.test.integration.wallet;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.service.AuthProfileService;
import com.digicore.billent.data.lib.modules.common.wallet.dto.WalletBalanceResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.service.implementation.WalletServiceImpl;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.otp.service.NotificationDispatcher;
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

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.WALLET_API_V1;
import static com.digicore.billent.data.lib.modules.common.constants.SystemConstants.MAKER_EMAIL;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private WalletServiceImpl walletServiceImpl;

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

    @Test
    void viewWalletBalanceTest() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc);
        testHelper.updateTestRole("view-all-wallet-balances");

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
}
