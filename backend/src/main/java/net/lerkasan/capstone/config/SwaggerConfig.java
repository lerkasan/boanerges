package net.lerkasan.capstone.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springSwaggerOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Boanerges API Docs")
                        .description("Boanerges REST API documentation")
                        .version("v1.0.0"));
    }
}
