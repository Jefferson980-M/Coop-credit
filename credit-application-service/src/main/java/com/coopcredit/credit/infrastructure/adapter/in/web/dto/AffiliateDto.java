package com.coopcredit.credit.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class AffiliateDto {

    @Data
    @Builder
    @Schema(description = "Affiliate registration request")
    public static class Request {
        @NotBlank(message = "Name is required")
        @Schema(description = "Full name of the affiliate", example = "María González")
        private String name;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Schema(description = "Email address", example = "maria@example.com")
        private String email;

        @NotBlank(message = "Document is required")
        @Schema(description = "Identity document number", example = "9876543210")
        private String document;

        @NotNull(message = "Salary is required")
        @Min(value = 0, message = "Salary must be greater than 0")
        @Schema(description = "Monthly salary", example = "5000000")
        private Double salary;
    }

    @Data
    @Builder
    public static class Response {
        private Long id;
        private String name;
        private String email;
        private String document;
        private Double salary;
        private boolean active;
        private LocalDateTime createdAt;
    }
}
