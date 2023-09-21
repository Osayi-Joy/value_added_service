package com.digicore.billent.backoffice.service.test.integration.resellers;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.AUDIT_TRAIL_API_V1;
import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.RESELLER_AUDIT_TRAIL_API_V1;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_NUMBER;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_NUMBER_DEFAULT_VALUE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_SIZE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_SIZE_DEFAULT_VALUE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.VALUE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/*
 * @author Ikechi Ucheagwu
 * @createdOn Sep-21(Thur)-2023
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BackOfficeResellerAuditTrailsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PropertyConfig propertyConfig;

  @BeforeEach
  void checkup() throws Exception {
    new H2TestConfiguration(propertyConfig);
    TestHelper testHelper = new TestHelper(mockMvc);
    testHelper.createTestRole();
  }

  @Test
  void testSearchAuditTrails() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);

    MvcResult mvcResult = mockMvc.perform(
            get(RESELLER_AUDIT_TRAIL_API_V1 + "search").param(PAGE_NUMBER, PAGE_NUMBER_DEFAULT_VALUE)
                .param(PAGE_SIZE, PAGE_SIZE_DEFAULT_VALUE)
                .param(VALUE, "")
                .header("Authorization", testHelper.retrieveValidAccessToken()))
        .andExpect(status().isOk())
        .andReturn();

    ApiResponseJson<?> response =
        ClientUtil.getGsonMapper()
            .fromJson(mvcResult.getResponse().getContentAsString().trim(), ApiResponseJson.class);

    assertTrue(response.isSuccess());
  }

}
