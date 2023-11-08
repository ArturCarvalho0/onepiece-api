package br.com.onepiece.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

  @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("One Piece API")
                        .version("1.0")
                        .description("How to use the API")
                        .termsOfService("https://github.com/ArturCarvalho0")
                        .license(new License()
                                .name("Apache License, Version 2.0")
                                .url("http://www.apache.org/licenses/")));
    }
}
