package com.setec.su54_pos_api.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import io.swagger.v3.oas.models.servers.Server;
@Configuration
public class SwaggerConfig {
    @Value("${production}")
    private  String production;
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SU54 Spring Boot API "+production)
                        .version("1.0")
                        .description("API documentation for My Spring Boot application")
                        ).servers(List.of(
                new Server().url("https://sumchanthou.com/swagger-ui/index.html")
        ));

    }
}
