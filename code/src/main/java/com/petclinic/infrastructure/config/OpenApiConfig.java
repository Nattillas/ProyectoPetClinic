package com.petclinic.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for OpenAPI documentation.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI petClinicOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PetClinic API")
                        .description("API for managing pets, owners, and veterinary visits")
                        .version("1.0.0"));
    }
}