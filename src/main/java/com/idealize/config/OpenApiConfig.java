package com.idealize.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Hello Swegger ;)")
                        .version("v1")
                        .description("Api para estudos")
                        .termsOfService("www.estudosparavagas.com")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("www.estudosparavagas.com")));
    }

}
