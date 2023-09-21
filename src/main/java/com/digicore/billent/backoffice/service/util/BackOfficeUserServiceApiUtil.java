package com.digicore.billent.backoffice.service.util;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-03(Mon)-2023
 */

public class BackOfficeUserServiceApiUtil {

 //Base API versioning
 public static final String API_V1 = "/api/v1/backoffice/";
 //Authentication API versioning
 public static final String AUTHENTICATION_API_V1 = API_V1 + "authentication/process/";
 //Onboarding API versioning
 public static final String ONBOARDING_API_V1 = API_V1 + "onboarding/process/";
 //Approval API versioning
 public static final String APPROVAL_API_V1 = API_V1 + "approval/process/";
 //Roles API versioning
 public static final String ROLES_API_V1 = API_V1 + "role/process/";

 //Billers API versioning
 public static final String BILLERS_API_V1 = API_V1 + "billers/process/";

 //Products API versioning
 public static final String PRODUCTS_API_V1 = API_V1 + "products/process/";

 //Biller Aggregators API versioning
 public static final String BILLER_AGGREGATORS_API_V1 = API_V1 + "biller-aggregators/process/";

 //Profile API versioning
 public static final String PROFILE_API_V1 = API_V1 + "profile/process/";

 //Resellers API versioning
 public static final String RESELLERS_API_V1 = API_V1 + "resellers/process/";

 //AuditTrail API versioning
 public static final String AUDIT_TRAIL_API_V1 = API_V1 + "audit-trail/process/";

 //Wallets API versioning
 public static final String WALLET_API_V1 = API_V1 + "wallets/process/";

 //Dashboard API versioning
 public static final String DASHBOARD_API_V1 = API_V1 + "dashboard/process/";

 //AuditTrail API versioning
 public static final String RESELLER_AUDIT_TRAIL_API_V1 = API_V1 + "reseller/audit-trail/process/";

 private BackOfficeUserServiceApiUtil() {
 }
}
