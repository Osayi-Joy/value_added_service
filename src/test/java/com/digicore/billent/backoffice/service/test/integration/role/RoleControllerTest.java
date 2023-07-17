//package com.digicore.billent.backoffice.service.modules.roles.controller;
//
//import com.digicore.billent.data.lib.modules.role.dto.RoleDTO;
//import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.json.JSONObject;
//import org.junit.Test;
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
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void testGetAllRoles() throws Exception {
//        /*int pageNumber = 0;
//        int pageSize = 10;
//
//        MvcResult mvcResult = mockMvc.perform(get(ROLES_API_V1 + "get-all-roles")
//                        .param("pageNumber", String.valueOf(pageNumber))
//                        .param("pageSize", String.valueOf(pageSize)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseContent = mvcResult.getResponse().getContentAsString();
//        JSONObject jsonResponse = new JSONObject(responseContent);
//        PaginatedResponseDTO<RoleDTO> paginatedResponseDTO = objectMapper.readValue(jsonResponse.getString("data"), PaginatedResponseDTO.class);
//
//        String message = jsonResponse.getString("message");
//        assertEquals("Roles retrieved successfully", message);
//        assertNotNull(paginatedResponseDTO.getContent());
//        assertTrue(paginatedResponseDTO.isFirstPage());
//        assertTrue(paginatedResponseDTO.isLastPage());
//        assertNotNull(paginatedResponseDTO.getContent());*/
//    }
//}
