package com.digicore.billent.backoffice.service.util;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-18(Tue)-2023
 */

public class SwaggerDocUtil {

  // Approval Controller Doc
  public static final String APPROVAL_CONTROLLER_TITLE = "BackOffice-Approval-Module";
  public static final String APPROVAL_CONTROLLER_DESCRIPTION =
      "This module contains all required APIs to complete the checker operations in the system.";
  public static final String APPROVAL_CONTROLLER_APPROVE_REQUEST_TITLE = "Approve pending request";
  public static final String APPROVAL_CONTROLLER_APPROVE_REQUEST_DESCRIPTION =
      "This API is used to approve a pending request, it requires the authenticated user has the right approve"
          + " permission based on the pending request type";
  public static final String APPROVAL_CONTROLLER_DECLINE_REQUEST_TITLE = "Decline pending request";
  public static final String APPROVAL_CONTROLLER_DECLINE_REQUEST_DESCRIPTION =
      "This API is used to decline a pending request.";
  public static final String APPROVAL_CONTROLLER_GET_REQUEST_TITLE = "Get a single pending request";
  public static final String APPROVAL_CONTROLLER_GET_REQUEST_DESCRIPTION =
      "This API is used to get a pending request.";
  public static final String APPROVAL_CONTROLLER_GET_TREATED_REQUEST_TITLE = "Get treated request";
  public static final String APPROVAL_CONTROLLER_GET_TREATED_REQUEST_DESCRIPTION =
      "This API is used to get all treated request.";
  public static final String APPROVAL_CONTROLLER_GET_PENDING_REQUEST_TITLE =
      "Get all pending request";
  public static final String APPROVAL_CONTROLLER_GET_PENDING_REQUEST_DESCRIPTION =
      "This API is used to get all pending request.";
  // Authentication Controller Doc
  public static final String AUTHENTICATION_CONTROLLER_TITLE = "BackOffice-Authentication-Module";
  public static final String AUTHENTICATION_CONTROLLER_DESCRIPTION =
      "This module contains all required APIs to complete authentication.";
  public static final String AUTHENTICATION_CONTROLLER_LOGIN_TITLE = "Authenticate a user";
  public static final String AUTHENTICATION_CONTROLLER_LOGIN_DESCRIPTION =
      "This API is used to authenticate a user.";
  // Onboarding Controller Doc
  public static final String ONBOARDING_CONTROLLER_TITLE = "BackOffice-Onboarding-Module";
  public static final String ONBOARDING_CONTROLLER_DESCRIPTION =
      "This module contains all required APIs to invite new users into the system.";
  public static final String ONBOARDING_CONTROLLER_INVITE_USER_TITLE =
      "Invite a user to the backoffice platform, this module goes through maker checker process.";
  public static final String ONBOARDING_CONTROLLER_INVITE_USER_DESCRIPTION =
      "This API is used to invite a user to the backoffice platform, it sends an invite mail to the user.";
  public static final String ONBOARDING_CONTROLLER_RE_INVITE_USER_TITLE =
      "Resend invitation to an already invited user";
  public static final String ONBOARDING_CONTROLLER_RE_INVITE_USER_DESCRIPTION =
      "This API is used to resend invitation to an already invited user.This is useful for when they didn't get the"
          + " previous invite mail";

  public static final String ONBOARDING_CONTROLLER_RESET_DEFAULT_PASSWORD_TITLE =
      "Reset default password";
  public static final String ONBOARDING_CONTROLLER_RESET_DEFAULT_PASSWORD_DESCRIPTION =
      "This API is used to reset the default password of a new user, it requires a resetKey that can be found in the "
          + "access token after login";
  // Role Controller Doc
  public static final String ROLE_CONTROLLER_TITLE = "BackOffice-Role-Module";
  public static final String ROLE_CONTROLLER_DESCRIPTION =
      "This module contains all required APIs to complete operations surrounding authorization"
          + " ( role/permission management ). it goes through maker checker process";

  public static final String ROLE_CONTROLLER_GET_ALL_ROLES_TITLE = "Get all roles";
  public static final String ROLE_CONTROLLER_GET_ALL_ROLES_DESCRIPTION =
      "This API is used to fetch all available roles in the system, it requires a query param named paginated and value "
          + "true if data is been retrieved for a paginated view.";

  public static final String ROLE_CONTROLLER_GET_ALL_PERMISSIONS_TITLE = "Get all permissions";
  public static final String ROLE_CONTROLLER_GET_ALL_PERMISSIONS_DESCRIPTION =
      "This API is used to fetch all available permissions in the system";

  public static final String ROLE_CONTROLLER_CREATE_A_ROLE_TITLE = "Add a new role";
  public static final String ROLE_CONTROLLER_CREATE_A_ROLE_DESCRIPTION =
      "This API is used to add a new role to the system, it goes through maker checker.";

  public static final String ROLE_CONTROLLER_DELETE_A_ROLE_TITLE = "Delete a role";
  public static final String ROLE_CONTROLLER_DELETE_A_ROLE_DESCRIPTION =
      "This API is used to delete a role on the system, it goes through maker checker.";

  // Biller Controller Doc
  public static final String BILLER_CONTROLLER_TITLE = "BackOffice-Biller-Module";
  public static final String BILLER_CONTROLLER_DESCRIPTION =
      "This module contains all required APIs to complete operations surrounding Billers. it goes through maker checker "
          + "process";
  public static final String BILLER_CONTROLLER_GET_ALL_BILLERS_TITLE = "Get all billers";
  public static final String BILLER_CONTROLLER_GET_ALL_BILLERS_DESCRIPTION =
      "This API is used to fetch all billers.";
  public static final String BILLER_CONTROLLER_GET_A_BILLER_TITLE = "Get a billers";
  public static final String BILLER_CONTROLLER_GET_A_BILLER_DESCRIPTION =
      "This API is used to fetch a biller's details.";
  public static final String BILLER_CONTROLLER_FETCH_BILLERS_BY_STATUS_TITLE =
      "Filter billers by Status";
  public static final String BILLER_CONTROLLER_FETCH_BILLERS_BY_STATUS_DESCRIPTION =
      "This API is used to Filter billers by Status.";
  public static final String BILLER_CONTROLLER_EXPORT_BILLERS_IN_CSV_TITLE =
      "Export billers in csv";
  public static final String BILLER_CONTROLLER_EXPORT_BILLERS_IN_CSV_DESCRIPTION =
      "This API is used to export billers in csv.";

  // Product Controller Doc
  public static final String PRODUCT_CONTROLLER_TITLE = "BackOffice-Product-Module";
  public static final String PRODUCT_CONTROLLER_DESCRIPTION =
      "This module contains all required APIs to complete operations surrounding Products. it goes through maker checker"
          + " process";
  public static final String PRODUCT_CONTROLLER_GET_ALL_PRODUCTS_TITLE = "Get all products";
  public static final String PRODUCT_CONTROLLER_GET_ALL_PRODUCTS_DESCRIPTION =
      "This API is used to fetch all products.";
  public static final String PRODUCT_CONTROLLER_GET_A_PRODUCT_TITLE = "Get a products";
  public static final String PRODUCT_CONTROLLER_GET_A_PRODUCT_DESCRIPTION =
      "This API is used to fetch a product's details.";
  public static final String PRODUCT_CONTROLLER_FETCH_PRODUCTS_BY_STATUS_TITLE =
      "Filter products by Status";
  public static final String PRODUCT_CONTROLLER_FETCH_PRODUCTS_BY_STATUS_DESCRIPTION =
      "This API is used to Filter products by Status.";
  public static final String PRODUCT_CONTROLLER_EXPORT_PRODUCTS_IN_CSV_TITLE =
      "Export products in csv";
  public static final String PRODUCT_CONTROLLER_EXPORT_PRODUCTS_IN_CSV_DESCRIPTION =
      "This API is used to export products in csv.";
  public static final String BILLER_CONTROLLER_ENABLE_A_BILLER_TITLE = "Enable a biller";
  public static final String BILLER_CONTROLLER_ENABLE_A_BILLER_DESCRIPTION =
      "This API is used to enable a biller.";
  public static final String BILLER_CONTROLLER_DISABLE_A_BILLER_TITLE = "Enable a biller";
  public static final String BILLER_CONTROLLER_DISABLE_A_BILLER_DESCRIPTION =
      "This API is used to enable a biller.";
  public static final String BILLER_CONTROLLER_UPDATE_A_BILLER_TITLE = "Update a billers";
  public static final String BILLER_CONTROLLER_UPDATE_A_BILLER_DESCRIPTION =
      "This API is used to update a biller's details.";

  // Biller Aggregator Controller Doc
  public static final String BILLER_AGGREGATOR_CONTROLLER_TITLE =
      "BackOffice-Biller-Aggregator-Module";
  public static final String BILLER_AGGREGATOR_CONTROLLER_DESCRIPTION =
      "This module contains all required APIs to complete operations surrounding Biller Aggregators. it goes through "
          + "maker checker process";

  public static final String BILLER_AGGREGATOR_CONTROLLER_REFRESH_BILLERS_AND_PRODUCTS_TITLE =
      "Refresh all billers and products under an aggregator";
  public static final String BILLER_AGGREGATOR_CONTROLLER_REFRESH_BILLERS_AND_PRODUCTS_DESCRIPTION =
      "This API is used to fetch all billers and their products under an aggregator then update the system data with it."
          + " It goes through maker checker process";
  public static final String PRODUCT_CONTROLLER_ENABLE_A_PRODUCT_TITLE = "Enable a product";
  public static final String PRODUCT_CONTROLLER_ENABLE_A_PRODUCT_DESCRIPTION =
      "This API is used to enable a product.";
  public static final String PRODUCT_CONTROLLER_DISABLE_A_PRODUCT_TITLE = "Disable a product";
  public static final String PRODUCT_CONTROLLER_DISABLE_A_PRODUCT_DESCRIPTION =
      "This API is used to disable a product.";
  public static final String BILLER_AGGREGATOR_CONTROLLER_UPDATE_AGGREGATOR_TITLE = "Update a biller aggregator";
  public static final String BILLER_AGGREGATOR_CONTROLLER_UPDATE_AGGREGATOR_DESCRIPTION =
          "This API is used to update a biller aggregator's details.";

  // Profile Controller Doc

  public static final String PROFILE_CONTROLLER_TITLE = "BackOffice-Profile-Module";
  public static final String PROFILE_CONTROLLER_DESCRIPTION =
      "This module contains all required APIs to complete operations surrounding all backoffice user profiles "
          + "management. it goes through maker checker process";

  public static final String PROFILE_CONTROLLER_GET_ALL_USERS_TITLE =
      "Get all back office user profiles";
  public static final String PROFILE_CONTROLLER_GET_ALL_USERS_DESCRIPTION =
      "This API is used to fetch all back office user profiles in the system.";

  public static final String PROFILE_CONTROLLER_FILTER_USERS_TITLE = "Filter back office users";
  public static final String PROFILE_CONTROLLER_FILTER_USERS_DESCRIPTION =
      "This API is used to Filter back office users based on the created date and status.";

  public static final String PROFILE_CONTROLLER_SEARCH_USERS_TITLE = "Search back office users.";
  public static final String PROFILE_CONTROLLER_SEARCH_USERS_DESCRIPTION =
      "This API is used to search for back office users, it expects a key and value where the key is the name of any "
          + "field returned when you invoked the get all endpoint and value is your search word. There is an exception "
          + "which is the status field, you can't search based on status. If you wish to get back office users "
          + "based on status kindly use the filter endpoint.";

  private SwaggerDocUtil() {}
}
