package com.dreamers.the_dreamers.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        // Tên của scheme bảo mật
        final String securitySchemeName = "Bearer Authentication";

        // Định nghĩa scheme bảo mật JWT
        Components components = new Components()
                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        // Yêu cầu token cho toàn bộ các endpoint
        SecurityRequirement securityItem = new SecurityRequirement()
                .addList(securitySchemeName);

        // Thông tin liên hệ, giấy phép, máy chủ và info chung...
        Contact contact = new Contact();
        contact.setEmail("letritrung2605@gmail.com");
        contact.setName("Le Trí Trung");
        contact.setUrl("https://www.your-website.com");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("The Dreamers API Documentation")
                .version("1.0")
                .contact(contact)
                .description("Tài liệu API cho website của câu lạc bộ từ thiện The Dreamers.")
                .termsOfService("https://www.your-website.com/terms")
                .license(mitLicense);

        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("URL máy chủ cho môi trường Phát triển");

        Server prodServer = new Server();
        prodServer.setUrl("https://www.your-production-domain.com");
        prodServer.setDescription("URL máy chủ cho môi trường Production");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer))
                .components(components)
                .addSecurityItem(securityItem);
    }
}