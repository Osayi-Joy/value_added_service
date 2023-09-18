//package com.digicore.billent.backoffice.service.modules.billers.controller;
//
//
//import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLERS_API_V1;
//import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
//import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;
//
//import com.digicore.api.helper.response.ControllerResponse;
//import com.digicore.billent.backoffice.service.modules.billers.service.BillerBackOfficeProxyService;
//import com.digicore.billent.backoffice.service.modules.billers.service.BillerBackOfficeService;
//import com.digicore.billent.data.lib.modules.billers.dto.BillerDto;
//import com.digicore.registhentication.registration.enums.Status;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Profile;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
///*
// * @author Joy Osayi
// * @createdOn Jul-03(Mon)-2023
// */
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(BILLERS_API_V1)
//@Profile("phase2")
//@Tag(name = BILLER_CONTROLLER_TITLE, description = BILLER_CONTROLLER_DESCRIPTION)
//public class BackOfficeBillerController {
//    private final BillerBackOfficeService billerBackOfficeService;
//    private final BillerBackOfficeProxyService billerBackOfficeProxyService;
//
//    @GetMapping("get-all")
//    @PreAuthorize("hasAuthority('view-billers')")
//    @Operation(
//            summary = BILLER_CONTROLLER_GET_ALL_BILLERS_TITLE,
//            description = BILLER_CONTROLLER_GET_ALL_BILLERS_DESCRIPTION)
//    public ResponseEntity<Object> viewAllBillers(
//            @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
//            @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize)
//    {
//        return ControllerResponse.buildSuccessResponse(
//                billerBackOfficeService.getAllBillers(pageNumber, pageSize), "Retrieved all billers successfully");
//    }
//
//    @GetMapping("export-to-csv")
//    @PreAuthorize("hasAuthority('export-billers')")
//    @Operation(
//            summary = BILLER_CONTROLLER_EXPORT_BILLERS_IN_CSV_TITLE,
//            description = BILLER_CONTROLLER_EXPORT_BILLERS_IN_CSV_DESCRIPTION)
//    public void downloadListOfBillersInCSV(
//            @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
//            @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
//            @RequestParam(value = START_DATE, required = false) String startDate,
//            @RequestParam(value = END_DATE, required = false) String endDate,
//            @RequestParam(value = BILLER_STATUS, required = false) Status billerStatus,
//            @RequestParam(value = DOWNLOAD_FORMAT, required = false) String downloadFormat,
//            HttpServletResponse response) {
//        billerBackOfficeService.downloadAllBillersInCSV(response, billerStatus, startDate, endDate, downloadFormat, pageNumber, pageSize);
//    }
//
//
//    @GetMapping("filter-by-status")
//    @PreAuthorize("hasAuthority('view-billers')")
//    @Operation(
//            summary = BILLER_CONTROLLER_FETCH_BILLERS_BY_STATUS_TITLE,
//            description = BILLER_CONTROLLER_FETCH_BILLERS_BY_STATUS_DESCRIPTION)
//    public ResponseEntity<Object> filterBillersByStatus(
//            @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
//            @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
//            @RequestParam(value = START_DATE, required = false) String startDate,
//            @RequestParam(value = END_DATE, required = false) String endDate,
//            @RequestParam(value = BILLER_STATUS, required = false) Status billerStatus)
//    {
//        return ControllerResponse.buildSuccessResponse(
//                billerBackOfficeService.fetchBillersByStatus(billerStatus, startDate, endDate, pageNumber, pageSize), "Retrieved all billers by status successfully");
//    }
//
//    @GetMapping("get-{billerSystemId}-details")
//    @PreAuthorize("hasAuthority('view-billers')")
//    @Operation(
//            summary = BILLER_CONTROLLER_GET_A_BILLER_TITLE,
//            description = BILLER_CONTROLLER_GET_A_BILLER_DESCRIPTION)
//    public ResponseEntity<Object> fetchBillerById(@PathVariable String billerSystemId) {
//        return ControllerResponse.buildSuccessResponse(
//                billerBackOfficeService.fetchBillerById(billerSystemId), "Retrieved biller details successfully");
//    }
//
//    @PatchMapping("enable-{billerSystemId}")
//    @PreAuthorize("hasAuthority('enable-biller')")
//    @Operation(
//            summary = BILLER_CONTROLLER_ENABLE_A_BILLER_TITLE,
//            description = BILLER_CONTROLLER_ENABLE_A_BILLER_DESCRIPTION)
//    public ResponseEntity<Object> enableBiller(@PathVariable String billerSystemId) {
//        return ControllerResponse.buildSuccessResponse(billerBackOfficeProxyService.enableBiller(billerSystemId),"Biller enabled successfully");
//    }
//
//    @PatchMapping("disable-{billerSystemId}")
//    @PreAuthorize("hasAuthority('disable-biller')")
//    @Operation(
//            summary = BILLER_CONTROLLER_DISABLE_A_BILLER_TITLE,
//            description = BILLER_CONTROLLER_DISABLE_A_BILLER_DESCRIPTION)
//    public ResponseEntity<Object> disableBiller(@PathVariable String billerSystemId) {
//        return ControllerResponse.buildSuccessResponse(billerBackOfficeProxyService.disableBiller(billerSystemId),"Biller disabled successfully");
//    }
//
//    @PatchMapping("edit")
//    @PreAuthorize("hasAuthority('edit-billers')")
//    @Operation(
//            summary = BILLER_CONTROLLER_UPDATE_A_BILLER_TITLE,
//            description = BILLER_CONTROLLER_UPDATE_A_BILLER_DESCRIPTION)
//    public ResponseEntity<Object> updateBillerDetail(@Valid @RequestBody BillerDto billerDto) {
//        return ControllerResponse.buildSuccessResponse(billerBackOfficeProxyService.updateBillerDetail(billerDto),"Updated biller details successfully");
//    }
//
//}
