package com.digicore.billent.backoffice.service.test.integration.authentication;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.registhentication.authentication.dtos.request.LoginRequestDTO;
import com.digicore.registhentication.authentication.dtos.response.LoginResponse;
import com.digicore.registhentication.authentication.enums.AuthenticationType;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.AUTHENTICATION_API_V1;
import static com.digicore.billent.data.lib.modules.common.constants.SystemConstants.MAKER_EMAIL;
import static com.digicore.billent.data.lib.modules.common.constants.SystemConstants.SYSTEM_DEFAULT_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-17(Mon)-2023
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
 class BackOfficeAuthenticationTest {
    @Autowired
 private MockMvc mockMvc;
    @Autowired
    private PropertyConfig propertyConfig;

    @BeforeEach
      void  checkup(){
        new H2TestConfiguration(propertyConfig);
    }

   @Test
   void getAccessToken() throws Exception {

       LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
       loginRequestDTO.setAuthenticationType(AuthenticationType.PASSWORD);
       loginRequestDTO.setPassword(SYSTEM_DEFAULT_PASSWORD);
       loginRequestDTO.setEmail(MAKER_EMAIL);
       loginRequestDTO.setUsername(MAKER_EMAIL);

       MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTHENTICATION_API_V1.concat("login"))
                       .content(ClientUtil.getGsonMapper().toJson(loginRequestDTO)).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk()).andReturn();

       ApiResponseJson<LoginResponse> response = ClientUtil.getGsonMapper().fromJson(result.getResponse().getContentAsString(),ApiResponseJson.class);
       assertTrue(response.isSuccess());
      // return  response.getData().getAccessToken();

   }
}
