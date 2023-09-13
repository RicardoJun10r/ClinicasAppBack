package com.agendamentos.online.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("Mobile")
                    .description("CRUD de paciente | clinica | profissional | agendamento")
                    .version("1.0")
                    .termsOfService("Termo de uso: Open Source")
                    .license(new License()
                            .name("Apache 2.0")
                            .url("https://github.com/RicardoJun10r/ClinicasAppBack")
                    )
            ).externalDocs(
                    new ExternalDocumentation()
                    .description("Ricardo Junior e João Felipe")
                    .url("https://github.com/RicardoJun10r/ClinicasAppBack"));
    }
    
}

