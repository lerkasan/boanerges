package net.lerkasan.capstone.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public static final String BOANERGES_API_DOCS = "Boanerges API Docs";
    public static final String BOANERGES_REST_API_DOCUMENTATION = "Boanerges REST API documentation";
    public static final String VERSION = "v1.0.0";

    @Bean
    public OpenAPI springSwaggerOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(BOANERGES_API_DOCS)
                        .description(BOANERGES_REST_API_DOCUMENTATION)
                        .version(VERSION));
    }
}
