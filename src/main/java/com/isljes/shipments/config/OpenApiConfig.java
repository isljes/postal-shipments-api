package com.isljes.shipments.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                description = "OpenApi документация REST-сервиса почтовых перевозок",
                title = "Postal Shipments API",
                version = "1.0"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
}
