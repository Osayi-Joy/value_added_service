package com.digicore.billent.backoffice.service.util;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-18(Tue)-2023
 */

public class SwaggerDocUtil {

 //Approval Controller Doc
 public static final String APPROVAL_CONTROLLER_TITLE = "BackOffice-Approval-Module";
 public static final String APPROVAL_CONTROLLER_DESCRIPTION = "This module contains all required APIs to complete the checker operations in the system.";
 public static final String APPROVAL_CONTROLLER_APPROVE_REQUEST_TITLE = "Approve pending request";
 public static final String APPROVAL_CONTROLLER_APPROVE_REQUEST_DESCRIPTION = "This API is used to approve a pending request, it requires the authenticated user has the right approve permission based on the pending request type";
 public static final String APPROVAL_CONTROLLER_DECLINE_REQUEST_TITLE = "Decline pending request";
 public static final String APPROVAL_CONTROLLER_DECLINE_REQUEST_DESCRIPTION = "This API is used to decline a pending request.";
 public static final String APPROVAL_CONTROLLER_GET_REQUEST_TITLE = "Get a single pending request";
 public static final String APPROVAL_CONTROLLER_GET_REQUEST_DESCRIPTION = "This API is used to get a pending request.";
 public static final String APPROVAL_CONTROLLER_GET_TREATED_REQUEST_TITLE = "Get treated request";
 public static final String APPROVAL_CONTROLLER_GET_TREATED_REQUEST_DESCRIPTION = "This API is used to get all treated request.";
 public static final String APPROVAL_CONTROLLER_GET_PENDING_REQUEST_TITLE = "Get all pending request";
 public static final String APPROVAL_CONTROLLER_GET_PENDING_REQUEST_DESCRIPTION = "This API is used to get all pending request.";
 //Authentication Controller Doc
 public static final String AUTHENTICATION_CONTROLLER_TITLE = "BackOffice-Authentication-Module";
 public static final String AUTHENTICATION_CONTROLLER_DESCRIPTION = "This module contains all required APIs to complete authentication.";
 public static final String AUTHENTICATION_CONTROLLER_LOGIN_TITLE = "Authenticate a user";
 public static final String AUTHENTICATION_CONTROLLER_LOGIN_DESCRIPTION = "This API is used to authenticate a user.";
 //Onboarding Controller Doc
 public static final String ONBOARDING_CONTROLLER_TITLE = "BackOffice-Onboarding-Module";
 public static final String ONBOARDING_CONTROLLER_DESCRIPTION = "This module contains all required APIs to invite new users into the system.";
 public static final String ONBOARDING_CONTROLLER_INVITE_USER_TITLE = "Invite a user to the backoffice platform, this module goes through maker checker process.";
 public static final String ONBOARDING_CONTROLLER_INVITE_USER_DESCRIPTION = "This API is used to invite a user to the backoffice platform, it sends an invite mail to the user.";
 public static final String ONBOARDING_CONTROLLER_RE_INVITE_USER_TITLE = "Resend invitation to an already invited user";
 public static final String ONBOARDING_CONTROLLER_RE_INVITE_USER_DESCRIPTION = "This API is used to resend invitation to an already invited user.This is useful for when they didn't get the previous invite mail";
 //Role Controller Doc
 public static final String ROLE_CONTROLLER_TITLE = "BackOffice-Role-Module";
 public static final String ROLE_CONTROLLER_DESCRIPTION = "This module contains all required APIs to complete operations surrounding authorization ( role/permission management ). it goes through maker checker process";
 public static final String ROLE_CONTROLLER_GET_ALL_ROLES_TITLE = "Authenticate a user";
 public static final String ROLE_CONTROLLER_GET_ALL_ROLES_DESCRIPTION = "This API is used to authenticate a user.";


 //Biller Controller Doc
 public static final String BILLER_CONTROLLER_TITLE = "BackOffice-Biller-Module";
 public static final String BILLER_CONTROLLER_DESCRIPTION = "This module contains all required APIs to complete operations surrounding Billers. it goes through maker checker process";
 public static final String BILLER_CONTROLLER_GET_ALL_BILLERS_TITLE = "Get all billers";
 public static final String BILLER_CONTROLLER_GET_ALL_BILLERS_DESCRIPTION = "This API is used to fetch all billers.";
 public static final String BILLER_CONTROLLER_GET_A_BILLER_TITLE = "Get details of a billers";
 public static final String BILLER_CONTROLLER_GET_A_BILLER__DESCRIPTION = "This API is used to view details of a biller.";
 public static final String BILLER_CONTROLLER_FETCH_BILLERS_BY_STATUS_TITLE = "Filter billers by Status";
 public static final String BILLER_CONTROLLER_FETCH_BILLERS_BY_STATUS_DESCRIPTION = "This API is used to Filter billers by Status.";
 public static final String BILLER_CONTROLLER_EXPORT_BILLERS_IN_CSV_TITLE = "Export billers in csv";
 public static final String BILLER_CONTROLLER_EXPORT_BILLERS_IN_CSV_DESCRIPTION = "This API is used to export billers in csv.";
 private SwaggerDocUtil() {
 }

}

