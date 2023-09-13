package com.digicore.billent.backoffice.service.modules.dashboard.service;
/*
 * @author Ikechi Ucheagwu
 * @createdOn Sep-13(Wed)-2023
 */

import com.digicore.billent.data.lib.modules.common.dashboard.DashboardService;
import com.digicore.request.processor.dto.DashboardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardOperation {

  private final DashboardService backOfficeDashboardServiceImpl;

  public DashboardDTO viewDashboard() {
    return backOfficeDashboardServiceImpl.populateDashboard();
  }
}
