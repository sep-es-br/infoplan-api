package br.gov.es.infoplan.config.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.OpenApiCustomizer;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class OpenAPIConfiguration {

    @Value("${openapi.server.url}")
    private String absoluteUrl;

    @Bean
    public OpenAPI defineOpenApi() {
        return new OpenAPI()
                .info(info())
                .servers(List.of(server()))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .tags(List.of(
                        new Tag().name("Autenticação").description("Endpoints para login e informações do usuário"),
                        new Tag().name("Execução Orçamentária")
                                .description("Consultas de receitas e despesas orçamentárias"),
                        new Tag().name("Siscap - Sistema de Captação de Recursos").description(
                                "O objetivo é gerenciar a captação de recursos do estado."),
                        new Tag().name("Sistema de Planejamento Orçamentário").description(
                                "O objetivo é gerenciar o investimento do estado."),
                        new Tag().name("Projetos Estratégicos").description("Acompanhamento de projetos da SEP")));
    }

    @Bean
    public OpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {
        return openApi -> openApi.getPaths().values()
                .forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                    ApiResponses responses = operation.getResponses();
                    responses.addApiResponse("401",
                            new ApiResponse().description("Token JWT inválido, expirado ou ausente"));
                    responses.addApiResponse("403",
                            new ApiResponse().description("Usuário não possui permissão para acessar este recurso"));
                    responses.addApiResponse("500",
                            new ApiResponse().description("Erro interno inesperado no servidor"));
                }));
    }

    private Server server() {
        Server server = new Server();
        server.url(absoluteUrl);
        server.description("InfoPlan API");
        return server;
    }

    private Contact contact() {
        Contact contact = new Contact();
        contact.name("InfoPlan - SEP");
        contact.url("https://info.sep.es.gov.br");
        return contact;
    }

    private Info info() {
        return new Info()
                .title("Infoplan API")
                .version("1.0.0")
                .description("Painel de Informações Estratégicas da SEP")
                .contact(contact());
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
