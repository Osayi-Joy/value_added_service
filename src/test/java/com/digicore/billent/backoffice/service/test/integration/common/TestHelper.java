package com.digicore.billent.backoffice.service.test.integration.common;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.common.util.ClientUtil;
import com.digicore.registhentication.authentication.dtos.request.LoginRequestDTO;
import com.digicore.registhentication.authentication.dtos.response.LoginResponse;
import com.digicore.registhentication.authentication.enums.AuthenticationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
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
@Component
@RequiredArgsConstructor
@Slf4j
public class TestHelper {


    private final MockMvc mockMvc;

    public String retrieveMakerAccessToken() throws Exception {
     LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
     loginRequestDTO.setAuthenticationType(AuthenticationType.PASSWORD);
     loginRequestDTO.setPassword(SYSTEM_DEFAULT_PASSWORD);
     loginRequestDTO.setEmail(MAKER_EMAIL);
     loginRequestDTO.setUsername(MAKER_EMAIL);

     MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTHENTICATION_API_V1.concat("login"))
                     .content(ClientUtil.getGsonMapper().toJson(loginRequestDTO)).contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk()).andReturn();

     ApiResponseJson<?> response = ClientUtil.getGsonMapper().fromJson(result.getResponse().getContentAsString().trim(),ApiResponseJson.class);
     assertTrue(response.isSuccess());


     String loginResponseInString = ClientUtil.getGsonMapper().toJson(response.getData());

     LoginResponse loginResponse = ClientUtil.getGsonMapper().fromJson(loginResponseInString, LoginResponse.class);

     return "Bearer ".concat(loginResponse.getAccessToken());
 }

}
