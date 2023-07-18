package com.digicore.billent.backoffice.service.util;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-18(Tue)-2023
 */

public class SwaggerDocUtil {

 private SwaggerDocUtil() {
 }

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

}


