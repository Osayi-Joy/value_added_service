//package com.digicore.billent.backoffice.service.modules.roles.controller;
//
//
//import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
//import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
//import com.digicore.common.util.ClientUtil;
//import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.ROLES_API_V1;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//public class RoleControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    @Test
//     void testGetAllRoles() throws Exception {
//        TestHelper testHelper = new  TestHelper(mockMvc);
//       int pageNumber = 0;
//        int pageSize = 10;
//
//        MvcResult mvcResult = mockMvc.perform(get(ROLES_API_V1 + "get-all-roles")
//                        .param("pageNumber", String.valueOf(pageNumber))
//                        .param("pageSize", String.valueOf(pageSize))
//                        .header("Authorization",testHelper.retrieveMakerAccessToken()))
//                .andExpect(status().isOk())
//                .andReturn();
//
//
//        PaginatedResponseDTO<?> paginatedResponseDTO = ClientUtil.getGsonMapper().fromJson(mvcResult.getResponse().getContentAsString().trim(), PaginatedResponseDTO.class);
//
//        String message = jsonResponse.getString("message");
//        assertEquals("Roles retrieved successfully", message);
//        assertNotNull(paginatedResponseDTO.getContent());
//        assertTrue(paginatedResponseDTO.isFirstPage());
//        assertTrue(paginatedResponseDTO.isLastPage());
//        assertNotNull(paginatedResponseDTO.getContent());
//    }
//}
