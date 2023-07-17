//package com.digicore.billent.backoffice.service.test.integration.registration;
///*
// * @author Oluwatobi Ogunwuyi
// * @createdOn Jul-17(Mon)-2023
// */
//
//import com.digicore.api.helper.response.ApiResponseJson;
//import com.digicore.billent.data.lib.modules.common.authentication.dtos.UserRegistrationDTO;
//import com.digicore.common.util.ClientUtil;
//import org.json.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.ONBOARDING_API_V1;
//import static com.digicore.billent.data.lib.modules.common.constants.SystemConstants.MAKER_ROLE_NAME;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@TestPropertySource(
//        locations = "classpath:application-integrationtest.properties")
// class BackOfficeUserRegistrationTest {
// @Autowired
// private MockMvc mockMvc;
//
// @Test
//  void getToken() throws Exception {
//  UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
//  userRegistrationDTO.setEmail("tobiogunwuyi@gmail.com");
//  userRegistrationDTO.setPhoneNumber("07087982874");
//  userRegistrationDTO.setFirstName("Oluwatobi");
//  userRegistrationDTO.setLastName("Ogunwuyi");
//  userRegistrationDTO.setAssignedRole(MAKER_ROLE_NAME);
//  userRegistrationDTO.setUsername("tobiogunwuyi@gmail.com");
//
//
//  MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ONBOARDING_API_V1.concat("user-invitation"))
//                  .content(String.valueOf(userRegistrationDTO)).contentType(MediaType.APPLICATION_JSON))
//          .andExpect(status().isOk()).andReturn();
//
//  ApiResponseJson<?> response = ClientUtil.getGsonMapper().fromJson(result.getResponse().getContentAsString(),ApiResponseJson.class);
//  assertTrue(response.isSuccess());
// }
//}
