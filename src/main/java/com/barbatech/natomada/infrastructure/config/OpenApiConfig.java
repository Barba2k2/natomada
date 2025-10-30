package com.barbatech.natomada.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI/Swagger configuration
 *
 * Access Swagger UI at: http://localhost:8080/swagger-ui.html
 * Access API docs at: http://localhost:8080/v3/api-docs
 */
@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name:NaTomada API}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenAPI() {
        // Security scheme for JWT
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
            .info(new Info()
                .title("NaTomada API")
                .description("""
                    API para o aplicativo NaTomada - Mapeamento e gerenciamento de estações de recarga para veículos elétricos.

                    ## Funcionalidades
                    - Autenticação JWT com refresh tokens
                    - Gerenciamento de perfil e configurações
                    - Busca e favoritos de estações de recarga
                    - Catálogo de veículos elétricos
                    - Gerenciamento de veículos do usuário

                    ## Autenticação
                    1. Registre-se em `/api/auth/register`
                    2. Faça login em `/api/auth/login` para obter o JWT
                    3. Use o token no header: `Authorization: Bearer <token>`
                    4. Para renovar o token, use `/api/auth/refresh-token`
                    """)
                .version("1.0.0")
                .contact(new Contact()
                    .name("BarbaeTech")
                    .email("contato@barbatech.com")
                    .url("https://barbatech.com"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                new Server()
                    .url("http://localhost:8080")
                    .description("Development server"),
                new Server()
                    .url("https://api.natomada.com")
                    .description("Production server")
            ))
            .addSecurityItem(new SecurityRequirement()
                .addList(securitySchemeName))
            .components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes(securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("JWT token obtido através do endpoint /api/auth/login")));
    }
}
