package com.coopcredit.riskmock.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Risk Central Mock Service API", version = "v1", description = "Servicio simulado de evaluación de riesgo crediticio"))
public class OpenApiConfig {
}
