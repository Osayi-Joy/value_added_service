package com.digicore.billent.backoffice.service.config;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.API_V1;

import com.digicore.config.properties.PropertyConfig;
import com.digicore.request.processor.enums.RequestHandlerType;
import com.digicore.request.processor.processors.RequestHandlerPostProcessor;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Primary;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * @author Oluwatobi Ogunwuyi
 * @createdOn Apr-23(Sun)-2023
 */
@Configuration
@RequiredArgsConstructor
public class BackOfficeSwaggerConfig {
  // todo externalize configs where applicable
  // todo this config was copied from another project and should be refactored properly

  private final PropertyConfig propertyConfig;

  @Bean
  public OpenAPI springShopOpenAPI() {
    final String securitySchemeName = "bearerAuth";
    return new OpenAPI()
        .info(
            new Info()
                .title("Biller Platform/BackOffice APIs")
                .description(
                    "This documentation contains all the APIs exposed for the backoffice console. Aside the authentication and reset password APIs, all other APIs requires a valid authenticated user jwt access token before they can be invoked")
                .version("v1.0.0")
                .license(
                    new License()
                        .name("Proprietary License")
                        .url("http://localhost:2760/download-license")))
        .externalDocs(
            new ExternalDocumentation()
                .description("Issue tracker")
                .url(
                    "https://gitlab.com/teamdigicore/billent-backoffice-service/-/issues"))
        .addServersItem(new Server().description("Local Server").url("http://localhost:3700"))
        .addServersItem(
            new Server()
                .description("Digicore Development Server")
                .url(propertyConfig.getSwaggerUrl()))
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
        .components(
            new Components()
                .addSecuritySchemes(
                    securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
  }

  @Bean
  public GroupedOpenApi backofficeApi() {
    return GroupedOpenApi.builder().group("backoffice").pathsToMatch(API_V1.concat("**")).build();
  }

  @Bean
  public RequestHandlerPostProcessor requestHandlerPostProcessor() {
    return new RequestHandlerPostProcessor(List.of(RequestHandlerType.PROCESS_MAKER_REQUESTS));
  }

  @Bean
  @Primary
  @Description("Thymeleaf template engine with Spring integration")
  public SpringTemplateEngine springTemplateEngine() {
    return new SpringTemplateEngine();
  }
}
