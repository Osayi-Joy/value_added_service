//package com.digicore.billent.backoffice.service.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author Oluwatobi Ogunwuyi
// * @createdOn Sep-05(Mon)-2022
// */
//@Configuration
//@RequiredArgsConstructor
//public class CORSFilter implements WebMvcConfigurer {
//
//  private final Dummy dummy;
//  @Override
//  public void addCorsMappings(CorsRegistry registry) {
//    dummy.doNoting();
//    registry
//        .addMapping("/**")
//        .allowedOrigins("*")
//        .allowedHeaders(
//            "Content-Type",
//            "Access-Control-Allow-Headers",
//            "Access-Control-Expose-Headers",
//            "Content-Disposition",
//                "Access-Control-Allow-Origin",
//            "Authorization",
//            " X-Requested-With")
//        .exposedHeaders("Content-Disposition")
//        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "PATCH");
//  }
//}
