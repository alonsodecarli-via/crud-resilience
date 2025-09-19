package br.com.casasbahia.crud_h2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Produtos - Grupo Casas Bahia")
                        .version("1.0")
                        .description("CRUD do cadastro de produtos com Spring Boot e H2"));
    }

}
