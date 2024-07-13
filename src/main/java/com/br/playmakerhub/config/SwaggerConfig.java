package com.br.playmakerhub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration

public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Playmaker Hub Api")
                .version("v1")
                .description("")
                .termsOfService("https://github.com/ParloNeto")
                .license(new License()
                        .name("Paulo Neto")
                        .url("https://github.com/ParloNeto")
                )
        );
    }
}