package org.warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                            .title("Devicewarehouse")
                            .version("1.0.0")
                .contact(new Contact()
                            .email("akinin_igor@mail.ru")
                            .name("Akinin Igor")));
    }
}
