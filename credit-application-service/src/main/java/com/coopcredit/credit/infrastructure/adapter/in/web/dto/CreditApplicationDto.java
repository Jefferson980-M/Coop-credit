package com.coopcredit.credit.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class CreditApplicationDto {

    @Data
    @Builder
    @Schema(description = "Credit application request")
    public static class Request {
        @NotNull(message = "Affiliate ID is required")
        @Schema(description = "ID of the affiliate requesting credit", example = "1")
        private Long affiliateId;

        @NotNull(message = "Amount is required")
        @Min(value = 1000, message = "Amount must be at least 1000")
        @jakarta.validation.constraints.Max(value = 1000000000, message = "Amount cannot exceed 1 billion")
        @Schema(description = "Requested credit amount", example = "10000000")
        private Double amount;

        @NotNull(message = "Term is required")
        @Min(value = 1, message = "Term must be at least 1 month")
        @jakarta.validation.constraints.Max(value = 360, message = "Term cannot exceed 360 months")
        @Schema(description = "Credit term in months", example = "24")
        private Integer term;

        @Schema(description = "Proposed monthly interest rate (%)", example = "1.5")
        private Double rate; // Tasa propuesta

        @Schema(description = "Purpose of the credit", example = "Compra de vehículo")
        private String purpose;
    }

    @Data
    @Builder
    public static class Response {
        private Long id;
        private Long affiliateId;
        private Double amount;
        private Integer term;
        private Double rate;
        private String purpose;
        private String status;
        private LocalDateTime createdAt;
        private RiskEvaluationDto riskEvaluation;
    }

    @Data
    @Builder
    public static class RiskEvaluationDto {
        private Long id;
        private Integer score;
        private String recommendation;
    }
}
