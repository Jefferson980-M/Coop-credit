package com.coopcredit.riskmock.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskEvaluationResponse {
    private String documento;
    private int score;
    private String nivelRiesgo; // ALTO, MEDIO, BAJO
    private String detalle;
}
