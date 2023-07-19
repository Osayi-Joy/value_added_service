package com.digicore.billent.backoffice.service.test.integration.billers;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLERS_API_V1;
import static com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.billent.backoffice.service.modules.billers.service.BillerBackOfficeService;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.BackOfficeUserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.backoffice.authentication.service.BackOfficeUserAuthService;
import com.digicore.billent.data.lib.modules.common.util.SearchRequest;
import com.digicore.otp.service.NotificationDispatcher;
import com.digicore.registhentication.registration.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class ExportBillerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired private NotificationDispatcher notificationDispatcher;
    @Autowired private BackOfficeUserAuthService<BackOfficeUserAuthProfileDTO> backOfficeUserAuthService;
    @MockBean
    private BillerBackOfficeService billerBackOfficeService;

    @Test
    void testFetchBillersByStatus() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
        testHelper.updateMakerSelfPermissionByAddingNeededPermission("export-billers");
        int pageNumber = 0;
        int pageSize = 10;
        SearchRequest mockSearchRequest = new SearchRequest();
        mockSearchRequest.setStatus(Status.ACTIVE);
        mockSearchRequest.setDownloadFormat("csv");
        mockSearchRequest.setStartDate("2022-01-01");
        mockSearchRequest.setEndDate("2022-12-31");
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        doNothing().when(billerBackOfficeService).downloadAllBillersInCSV(mockResponse, mockSearchRequest, pageNumber, pageSize);

        ResultActions result = mockMvc.perform(get(BILLERS_API_V1 + "export-billers-to-csv")
                        .param(PAGE_NUMBER, String.valueOf(pageNumber))
                        .param(PAGE_SIZE, String.valueOf(pageSize))
                        .content(new ObjectMapper().writeValueAsString(mockSearchRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization",testHelper.retrieveValidAccessToken()))
                .andExpect(status().isOk());

    }
}
