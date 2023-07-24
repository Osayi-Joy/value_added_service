package com.digicore.billent.backoffice.service.test.integration.common;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-22(Sat)-2023
 */

import com.digicore.config.properties.PropertyConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2TestConfiguration {
    PropertyConfig propertyConfig;
    public H2TestConfiguration(PropertyConfig propertyConfig) {
        // Ensure the tests use H2 database, or fail otherwise

        if (!"jdbc:h2:mem:billent".equalsIgnoreCase(propertyConfig.getDatabaseUrl())) {
            throw new IllegalArgumentException("Tests must use H2 database for data source URL.");
        }
    }
}
