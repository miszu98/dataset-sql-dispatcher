package com.example.datasetsqldispatcher.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Dataset SQL Dispatcher API")
                        .description("Application for split dataset structure to tables")
                        .version("1.0")
                        .license(new License().name("InventiveSoft Licence"))
                        .contact(new Contact().name("Michał Małek").email("michal.malek02@gmail.com")));
    }

}
