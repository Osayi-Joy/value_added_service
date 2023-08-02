package com.digicore.billent.backoffice.service.config;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * @author Oluwatobi Ogunwuyi
 * @createdOn Sep-05(Mon)-2022
 */
@Configuration
public class CORSFilter implements CorsConfigurationSource {

  @Override
  public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("*"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "PATCH"));
    config.setAllowedHeaders(
        List.of(
            "Content-Type",
            "Access-Control-Allow-Headers",
            "Access-Control-Expose-Headers",
            "Access-Control-Allow-Origin",
            "Content-Disposition",
            "Authorization",
            " X-Requested-With"));
    config.addExposedHeader("Content-Disposition");
    return config;
  }
}
