package com.digicore.billent.backoffice.service.util;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-03(Mon)-2023
 */

public class BackOfficeUserServiceApiUtil {

 private BackOfficeUserServiceApiUtil() {
 }

 //Base API versioning
 public static final String API_V1 = "/api/v1/backoffice/";

 //Authentication API versioning
 public static final String AUTHENTICATION_API_V1 = API_V1 + "authentication/process/";

 //Onboarding API versioning
 public static final String ONBOARDING_API_V1 = API_V1 + "onboarding/process/";

 //Roles API versioning
 public static final String ROLES_API_V1 = API_V1 + "role/process/";
}
