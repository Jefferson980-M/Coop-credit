package com.coopcredit.riskmock.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskEvaluationRequest {
    @NotBlank(message = "El documento es obligatorio")
    private String documento;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    @jakarta.validation.constraints.Max(value = 1000000000, message = "El monto no puede exceder 1 billón")
    private Double monto;

    @NotNull(message = "El plazo es obligatorio")
    @Positive(message = "El plazo debe ser positivo")
    @jakarta.validation.constraints.Max(value = 360, message = "El plazo no puede exceder 360 meses")
    private Integer plazo;
}
