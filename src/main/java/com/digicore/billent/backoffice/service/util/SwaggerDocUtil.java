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
  public static final String AUTHENTICATION_CONTROLLER_REQUEST_PASSWORD_RESET_TITLE =
      "Initiate a user's password reset";
  public static final String AUTHENTICATION_CONTROLLER_REQUEST_PASSWORD_RESET_DESCRIPTION =
      "This API is used to initiate password reset for a user, it sends an otp to the user's email";

  public static final String AUTHENTICATION_CONTROLLER_VERIFY_EMAIL_OTP_TITLE =
      "Verify a user's email";

  public static final String AUTHENTICATION_CONTROLLER_VERIFY_EMAIL_OTP_DESCRIPTION =
      "This API verifies a user's email, it sends an otp to the user's phoneNumber after successfully validating the email";

  public static final String AUTHENTICATION_CONTROLLER_VERIFY_SMS_OTP_TITLE =
      "Verify a user's phoneNumber";

  public static final String AUTHENTICATION_CONTROLLER_VERIFY_SMS_OTP_DESCRIPTION =
      "This API verifies a user's phoneNumber for password reset";

  public static final String AUTHENTICATION_CONTROLLER_UPDATE_PASSWORD_TITLE =
      "Reset user password";

  public static final String AUTHENTICATION_CONTROLLER_UPDATE_PASSWORD_DESCRIPTION =
      "This API resets a user's existing password to a new password provided by the user, the otp required is the resetKey that came from the response after validating the phone number";
  public static final String AUTHENTICATION_CONTROLLER_CHANGE_MY_PASSWORD_TITLE =
          "Change password";

  public static final String AUTHENTICATION_CONTROLLER_CHANGE_MY_PASSWORD_DESCRIPTION =
          "This API is used to update an authenticated user password";

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

  public static final String ROLE_CONTROLLER_GET_ROLE_TITLE = "Get a role";
  public static final String ROLE_CONTROLLER_GET_ROLE_DESCRIPTION =
      "This API is used to fetch a  role in the system.";

  public static final String ROLE_CONTROLLER_GET_ALL_PERMISSIONS_TITLE = "Get all permissions";
  public static final String ROLE_CONTROLLER_GET_ALL_PERMISSIONS_DESCRIPTION =
      "This API is used to fetch all available permissions in the system";

  public static final String ROLE_CONTROLLER_CREATE_A_ROLE_TITLE = "Add a new role";
  public static final String ROLE_CONTROLLER_CREATE_A_ROLE_DESCRIPTION =
      "This API is used to add a new role to the system, it goes through maker checker.";

  public static final String ROLE_CONTROLLER_DELETE_A_ROLE_TITLE = "Delete a role";
  public static final String ROLE_CONTROLLER_DELETE_A_ROLE_DESCRIPTION =
      "This API is used to delete a role on the system, it goes through maker checker.";
  public static final String ROLE_CONTROLLER_UPDATE_A_ROLE_TITLE = "Update a role";
  public static final String ROLE_CONTROLLER_UPDATE_A_ROLE_DESCRIPTION =
      "This API is used to update a role on the system, it goes through maker checker.";
  public static final String ROLE_CONTROLLER_DISABLE_A_ROLE_TITLE = "Disable a role";
  public static final String ROLE_CONTROLLER_DISABLE_A_ROLE_DESCRIPTION = "This API is used to disable a role on the system, it goes through maker checker.";

  public static final String ROLE_CONTROLLER_ENABLE_A_ROLE_TITLE = "Enable a role";
  public static final String ROLE_CONTROLLER_ENABLE_A_ROLE_DESCRIPTION = "This API is used to enable a role on the system, it goes through maker checker.";


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

  public static final String BILLER_AGGREGATOR_CONTROLLER_GET_AGGREGATOR_TITLE =
      "Get a biller aggregator";
  public static final String BILLER_AGGREGATOR_CONTROLLER_GET_AGGREGATOR_DESCRIPTION =
      "This API is used to fetch a biller aggregator's details.";
  public static final String BILLER_AGGREGATOR_CONTROLLER_ENABLE_AGGREGATOR_TITLE =
      "Enable a biller AGGREGATOR";
  public static final String BILLER_AGGREGATOR_CONTROLLER_ENABLE_AGGREGATOR_DESCRIPTION =
      "This API is used to enable a biller aggregator.";
  public static final String BILLER_AGGREGATOR_CONTROLLER_UPDATE_AGGREGATOR_TITLE =
      "Update a biller aggregator";
  public static final String BILLER_AGGREGATOR_CONTROLLER_UPDATE_AGGREGATOR_DESCRIPTION =
      "This API is used to update a biller aggregator's details.";

  public static final String BILLER_AGGREGATOR_CONTROLLER_GET_ALL_AGGREGATORS_TITLE =
      "Get all aggregators";
  public static final String BILLER_AGGREGATOR_CONTROLLER_GET_ALL_AGGREGATORS_DESCRIPTION =
      "This API is used to fetch all available biller aggregators in the system, it requires a query param named paginated and value "
          + "true if data is been retrieved for a paginated view.";

  public static final String BILLER_AGGREGATOR_CONTROLLER_EXPORT_AGGREGATORS_IN_CSV_TITLE =
      "Export aggregators in csv";
  public static final String BILLER_AGGREGATOR_CONTROLLER_EXPORT_AGGREGATORS_IN_CSV_DESCRIPTION =
      "This API is used to export aggregators in csv.";
  public static final String BILLER_AGGREGATOR_CONTROLLER_GET_ALL_BILLERS_UNDER_AGGREGATOR_CONTROLLER_TITLE =
          "Get all Billers under aggregator";
  public static final String BILLER_AGGREGATOR_CONTROLLER_GET_ALL_BILLERS_UNDER_AGGREGATOR_CONTROLLER_DESCRIPTION =
          "This API is used to fetch all available billers under aggregator in the system, it requires a query param named aggregator system id to fetch the data";
  public static final String BILLER_AGGREGATOR_CONTROLLER_FILTER_AGGREGATORS_CONTROLLER_TITLE =
          "Filter aggregators";
  public static final String BILLER_AGGREGATOR_CONTROLLER_FILTER_AGGREGATORS_CONTROLLER_DESCRIPTION =
          "This API is used to fetch all filtered aggregator, it requires a query param named aggregator status, to fetch the data";

  public static final String BILLER_AGGREGATOR_CONTROLLER_SEARCH_AGGREGATOR_CONTROLLER_TITLE =
          "Search for a aggregator";
  public static final String BILLER_AGGREGATOR_CONTROLLER_SEARCH_AGGREGATOR_CONTROLLER_DESCRIPTION =
          "This API is used to search for an aggregator in the system, it requires a query param named value to fetch the data";

  // Profile Controller Doc
  public static final String PROFILE_CONTROLLER_TITLE = "BackOffice-Profile-Module";
  public static final String PROFILE_CONTROLLER_DESCRIPTION =
      "This module contains all required APIs to complete operations surrounding all backoffice user profiles "
          + "management. it goes through maker checker process";

  public static final String PROFILE_CONTROLLER_GET_ALL_USERS_TITLE =
      "Get all back office user profiles";
  public static final String PROFILE_CONTROLLER_GET_ALL_USERS_DESCRIPTION =
      "This API is used to fetch all back office user profiles in the system.";

  public static final String PROFILE_CONTROLLER_GET_USER_TITLE = "Get a back office user profile";
  public static final String PROFILE_CONTROLLER_GET_USER_DESCRIPTION =
      "This API is used to fetch a back office user profile in the system.";
  public static final String PROFILE_CONTROLLER_FETCH_SELF_USER_DETAILS_TITLE = "Fetch self user profile details";
  public static final String PROFILE_CONTROLLER_FETCH_SELF_USER_DETAILS_DESCRIPTION =
          "This API is used to fetch self user profile details in the system.";

  public static final String PROFILE_CONTROLLER_FILTER_USERS_TITLE = "Filter back office users";
  public static final String PROFILE_CONTROLLER_FILTER_USERS_DESCRIPTION =
      "This API is used to Filter back office users based on the created date and status.";

  public static final String PROFILE_CONTROLLER_SEARCH_USERS_TITLE = "Search back office users.";
  public static final String PROFILE_CONTROLLER_SEARCH_USERS_DESCRIPTION =
      "This API is used to search for back office users, it expects a value where the value is your search word.";

  public static final String PROFILE_CONTROLLER_DELETE_USER_PROFILE_TITLE = "Delete user profile";
  public static final String PROFILE_CONTROLLER_DELETE_USER_PROFILE_DESCRIPTION =
      "This API is used to delete user profile.";
  public static final String PROFILE_CONTROLLER_ENABLE_USER_PROFILE_TITLE = "Enable user profile";
  public static final String PROFILE_CONTROLLER_ENABLE_USER_PROFILE_DESCRIPTION =
      "This API is used to enable user profile.";

  public static final String PROFILE_CONTROLLER_DISABLE_USER_PROFILE_TITLE = "Disable user profile";
  public static final String PROFILE_CONTROLLER_DISABLE_USER_PROFILE_DESCRIPTION =
      "This API is used to disable user profile.";
  public static final String PROFILE_CONTROLLER_UPDATE_USER_PROFILE_TITLE = "Update user profile";
  public static final String PROFILE_CONTROLLER_UPDATE_USER_PROFILE_DESCRIPTION =
      "This API is used to update user profile.";

  public static final String PROFILE_CONTROLLER_EXPORT_PROFILES_IN_CSV_TITLE = "Export backoffice users";
  public static final String PROFILE_CONTROLLER_EXPORT_PROFILES_IN_CSV_DESCRIPTION= "This API is used to export backoffice users.";

  // Reseller Controller Doc
  public static final String RESELLER_CONTROLLER_TITLE = "BackOffice-Reseller-Module";
  public static final String RESELLER_CONTROLLER_DESCRIPTION =
      "This module contains all required APIs to complete operations surrounding resellers. it goes through maker checker "
          + "process";
  public static final String RESELLER_CONTROLLER_GET_ALL_RESELLER_TITLE = "Get all resellers";
  public static final String RESELLER_CONTROLLER_GET_ALL_RESELLER_DESCRIPTION =
      "This API is used to fetch all resellers.";

  public static final String RESELLER_CONTROLLER_FETCH_RESELLER_BY_STATUS_TITLE =
      "Filter resellers by Status or Date Created";
  public static final String RESELLER_CONTROLLER_FETCH_RESELLER_BY_STATUS_DESCRIPTION =
      "This API is used to Filter resellers by Status or Date Created.";

  public static final String RESELLER_CONTROLLER_FETCH_RESELLER_BY_SEARCH_TITLE =
      "Search resellers";
  public static final String RESELLER_CONTROLLER_FETCH_RESELLER_BY_SEARCH_DESCRIPTION =
      "This API is used to Search resellers by all table header except date created and status, use the filter endpoint if you wish to fetch data by status or date.";
  public static final String RESELLER_CONTROLLER_EXPORT_RESELLER_IN_CSV_TITLE =
      "Export resellers in csv";
  public static final String RESELLER_CONTROLLER_EXPORT_RESELLER_IN_CSV_DESCRIPTION =
      "This API is used to export resellers in csv.";

  public static final String RESELLER_CONTROLLER_FETCH_RESELLER_USER_BY_STATUS_TITLE =
      "Filter reseller users by Status or Date Created";
  public static final String RESELLER_CONTROLLER_FETCH_RESELLER_USER_BY_STATUS_DESCRIPTION =
      "This API is used to Filter reseller users by Status or Date Created.";

  public static final String RESELLER_CONTROLLER_FETCH_RESELLER_USER_BY_SEARCH_TITLE =
      "Search reseller users";
  public static final String RESELLER_CONTROLLER_FETCH_RESELLER_USER_BY_SEARCH_DESCRIPTION =
      "This API is used to Search reseller users by all table header except date created and status, use the filter endpoint if you wish to fetch data by status or date.";
  public static final String RESELLER_CONTROLLER_EXPORT_RESELLER_USER_IN_CSV_TITLE =
      "Export reseller users in csv";
  public static final String RESELLER_CONTROLLER_EXPORT_RESELLER_USER_IN_CSV_DESCRIPTION =
      "This API is used to export reseller users in csv.";

  public static final String RESELLER_PROFILE_CONTROLLER_GET_RESELLER_PROFILE_DETAIL_TITLE =
      "Get a reseller profile detail";
  public static final String RESELLER_PROFILE_CONTROLLER_GET_RESELLER_PROFILE_DETAIL_DESCRIPTION =
      "This API is used to fetch a reseller profile detail in the system.";

  public static final String RESELLER_PROFILE_CONTROLLER_GET_RESELLER_WALLET_BALANCE_TITLE =
      "Get a reseller wallet balance";
  public static final String RESELLER_PROFILE_CONTROLLER_GET_RESELLER_WALLET_BALANCE_DESCRIPTION =
      "This API is used to fetch a reseller wallet balance in the system.";

  public static final String RESELLER_CONTROLLER_DISABLE_A_RESELLER_USER_TITLE = "Disable a reseller user";

  public static final String RESELLER_CONTROLLER_DISABLE_A_RESELLER_USER_DESCRIPTION = "This API is used to disable a reseller user in the system.";

  public static final String RESELLER_CONTROLLER_ENABLE_A_RESELLER_USER_TITLE = "Enable a reseller user";

  public static final String RESELLER_CONTROLLER_GET_RESELLER_USER_DETAILS_TITLE = "Fetch Reseller User Details";

  public static final String RESELLER_CONTROLLER_DISABLE_A_RESELLER_TITLE = "Disable a reseller";

  public static final String RESELLER_CONTROLLER_DISABLE_A_RESELLER_DESCRIPTION = "This API is used to disable a reseller in the system.";

  public static final String RESELLER_CONTROLLER_ENABLE_A_RESELLER_TITLE = "Enable a reseller";
  public static final String RESELLER_CONTROLLER_ENABLE_A_RESELLER_DESCRIPTION = "his API is used to enable a reseller in the system.";

  public static final String RESELLER_CONTROLLER_GET_RESELLER_USER_DETAILS_DESCRIPTION = "This API is used to view a reseller user's details.";

  public static final String RESELLER_CONTROLLER_ENABLE_A_RESELLER_USER_DESCRIPTION = "This API is used to enable a reseller user in the system.";
  // Audit Trail Controller Doc
  public static final String AUDIT_TRAIL_CONTROLLER_TITLE = "BackOffice-Audit-Trail-Module";
  public static final String AUDIT_TRAIL_CONTROLLER_DESCRIPTION =
          "This module contains all required APIs to view audit trails.";
  public static final String AUDIT_TRAIL_CONTROLLER_FETCH_SELF_TITLE =
          "Fetch self audit trails";
  public static final String AUDIT_TRAIL_CONTROLLER_FETCH_SELF_DESCRIPTION =
          "This API is used to retrieve logged in user audit trails only.";

  public static final String AUDIT_TRAIL_CONTROLLER_FETCH_ALL_TITLE =
          "Fetch all audit trails";
  public static final String AUDIT_TRAIL_CONTROLLER_FETCH_ALL_DESCRIPTION =
          "This API is used to retrieve all audit trails.";

  public static final String AUDIT_TRAIL_CONTROLLER_FETCH_FILTERED_AUDIT_TRAILS =
      "Fetch filtered audit trails by activity or date range.";

  public static final String AUDIT_TRAIL_CONTROLLER_FETCH_FILTERED_AUDIT_TRAILS_DESCRIPTION =
      "This API is used to retrieve all audit trails filtered by activity or date range.";

  public static final String AUDIT_TRAIL_CONTROLLER_SEARCH_AUDIT_TRAILS =
      "Search audit trails.";

  public static final String AUDIT_TRAIL_CONTROLLER_SEARCH_AUDIT_DESCRIPTION =
      "This API is used to search all audit trails.";
  public static final String AUDIT_TRAIL_CONTROLLER_EXPORT_AUDIT_TRAILS_TO_CSV =
      "Export audit trails.";
  public static final String AUDIT_TRAIL_CONTROLLER_EXPORT_AUDIT_TRAILS_TO_CSV_DESCRIPTION =
      "This API is used to export audit trails.";
  // Wallets Controller Doc
  public static final String WALLET_CONTROLLER_TITLE = "BackOffice-Wallet-Module";
  public static final String WALLET_CONTROLLER_DESCRIPTION =
          "This module contains all required APIs to complete operations surrounding wallets.";

  public static final String WALLET_CONTROLLER_GET_A_WALLET_BALANCE_TITLE = "Get a wallet balance";
  public static final String WALLET_CONTROLLER_GET_A_WALLET_BALANCE_DESCRIPTION =
          "This API is used to fetch a wallet's balance.";
  public static final String WALLET_CONTROLLER_GET_ALL_WALLET_TITLE = "Get all wallets";
  public static final String WALLET_CONTROLLER_GET_ALL_WALLET_DESCRIPTION =
          "This API is used to fetch all wallets.";
  public static final String WALLET_CONTROLLER_CREDIT_TITLE = "Credit a wallet position";
  public static final String WALLET_CONTROLLER_CREDIT_DESCRIPTION =
      "This API is used to credit a customer wallet position. it goes through maker checker process";
  public static final String WALLET_CONTROLLER_FETCH_WALLET_BY_SEARCH_TITLE =
          "Search wallets";
  public static final String WALLET_CONTROLLER_FETCH_WALLET_BY_SEARCH_DESCRIPTION =
          "This API is used to Search wallets by all table header except date created and status, use the filter endpoint if you wish to fetch data by status";
  public static final String WALLET_CONTROLLER_FETCH_WALLET_BY_STATUS_TITLE =
          "Filter wallets by Status And Date Created";
  public static final String WALLET_CONTROLLER_FETCH_WALLET_BY_STATUS_DESCRIPTION =
          "This API is used to Filter wallets by Status And Date Created.";
  public static final String WALLET_CONTROLLER_FETCH_TRANSACTION_TITLE = "Fetch a wallet transaction log";
  public static final String WALLET_CONTROLLER_FETCH_TRANSACTION_DESCRIPTION = "This API is used to retrieve a wallet transaction log, The expected date format is yyyy-MM-dd. e.g 2023-01-01.";
  public static final String WALLET_CONTROLLER_EXPORT_WALLET_IN_CSV_TITLE =
          "Export wallets in csv";
  public static final String WALLET_CONTROLLER_EXPORT_WALLET_IN_CSV_DESCRIPTION =
          "This API is used to export wallets in csv.";
  // Dashboard Controller Doc
  public static final String DASHBOARD_CONTROLLER_TITLE = "BackOffice-Dashboard-Module";
  public static final String DASHBOARD_CONTROLLER_DESCRIPTION =
      "This module contains the required API to view dashboard.";

  public static final String DASHBOARD_CONTROLLER_CONTROLLER_VIEW =
      "View dashboard.";
  public static final String DASHBOARD_CONTROLLER_CONTROLLER_VIEW_DESCRIPTION =
      "This API is used to view dashboard.";

  //RESELLER TRANSACTION
  public static final String BACKOFFICE_RESELLER_CONTROLLER_GET_ALL_RESELLER_TRANSACTIONS_TITLE =
      "Fetch all reseller transactions.";
  public static final String BACKOFFICE_RESELLER_CONTROLLER_GET_ALL_RESELLER_TRANSACTIONS_DESCRIPTION =
      "This API is used to fetch all reseller transactions.";

  public static final String BACKOFFICE_RESELLER_CONTROLLER_FILTER_RESELLER_TRANSACTIONS_TITLE =
      "Filter reseller transactions.";
  public static final String BACKOFFICE_RESELLER_CONTROLLER_FILTER_RESELLER_TRANSACTIONS_DESCRIPTION =
      "This API is used to filter reseller transactions.";

  public static final String BACKOFFICE_RESELLER_CONTROLLER_SEARCH_RESELLER_TRANSACTIONS_TITLE =
      "Search reseller transactions.";
  public static final String BACKOFFICE_RESELLER_CONTROLLER_SEARCH_RESELLER_TRANSACTIONS_DESCRIPTION =
      "This API is used to search reseller transactions.";

  public static final String BACKOFFICE_RESELLER_CONTROLLER_VIEW_RESELLER_TRANSACTIONS_TITLE =
      "View reseller transactions.";
  public static final String BACKOFFICE_RESELLER_CONTROLLER_VIEW_RESELLER_TRANSACTIONS_DESCRIPTION =
      "This API is used to view reseller transactions.";

  public static final String BACKOFFICE_RESELLER_CONTROLLER_EXPORT_RESELLER_TRANSACTIONS_TITLE =
      "Export reseller transactions.";
  public static final String BACKOFFICE_RESELLER_CONTROLLER_EXPORT_RESELLER_TRANSACTIONS_DESCRIPTION =
      "This API is used to export reseller transactions.";

  private SwaggerDocUtil() {}
}
