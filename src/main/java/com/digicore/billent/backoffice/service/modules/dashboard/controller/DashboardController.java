package com.digicore.billent.backoffice.service.modules.dashboard.controller;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.DASHBOARD_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.DASHBOARD_CONTROLLER_CONTROLLER_VIEW;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.DASHBOARD_CONTROLLER_CONTROLLER_VIEW_DESCRIPTION;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.DASHBOARD_CONTROLLER_DESCRIPTION;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.DASHBOARD_CONTROLLER_TITLE;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.dashboard.service.DashboardOperation;
import com.digicore.request.processor.annotations.TokenValid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author Ikechi Ucheagwu
 * @createdOn Sep-13(Wed)-2023
 */
@RestController
@RequestMapping(DASHBOARD_API_V1)
@Tag(name = DASHBOARD_CONTROLLER_TITLE, description = DASHBOARD_CONTROLLER_DESCRIPTION)
@RequiredArgsConstructor
public class DashboardController {

  private final DashboardOperation dashboardOperation;

  @TokenValid()
  @PreAuthorize("hasAuthority('view-dashboard')")
  @GetMapping("get-metric-data")
  @Operation(
      summary = DASHBOARD_CONTROLLER_CONTROLLER_VIEW,
      description = DASHBOARD_CONTROLLER_CONTROLLER_VIEW_DESCRIPTION)
  public ResponseEntity<Object> viewDashboard() {
    return ControllerResponse.buildSuccessResponse(
        dashboardOperation.viewDashboard(), "View dashboard successfully done");
  }

}
