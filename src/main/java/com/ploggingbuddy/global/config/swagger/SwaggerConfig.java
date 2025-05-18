package com.ploggingbuddy.global.config.swagger;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private static final String LICENSE_NAME = "PloggingBuddy";
    private static final String SWAGGER_VERSION = "v0.0.1";
    private static final String SWAGGER_TITLE = "PloggingBuddy Server Api";
    private static final String SWAGGER_DESCRIPTION = "PloggingBuddy Server API Document";

    private static final String GITHUB_URL = "https://github.com/ploggingBuddy/PloggingBuddy-backend.git";

    @Bean
    public OpenAPI api(){
        return new OpenAPI().components(jwtComponents()).info(swaggerInfo());
    }

    private Info swaggerInfo() {
        License license = new License();
        license.setUrl(GITHUB_URL);
        license.setName(LICENSE_NAME);

        return new Info().version(SWAGGER_VERSION)
                .title(SWAGGER_TITLE)
                .description(SWAGGER_DESCRIPTION)
                .license(license);
    }

    private Components jwtComponents() {
        return new Components()
                .addSecuritySchemes(
                        "access-token",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")

                );
    }

}