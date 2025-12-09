package com.coopcredit.riskmock.service;

import org.springframework.stereotype.Service;

@Service
public class RiskService {

    public com.coopcredit.riskmock.model.RiskEvaluationResponse evaluateRisk(String document, Double monto,
            Integer plazo) {
        if (document == null || document.isEmpty()) {
            return new com.coopcredit.riskmock.model.RiskEvaluationResponse(document, 300, "ALTO",
                    "Documento inválido");
        }

        // Validate reasonable amount (max 1 billion)
        if (monto != null && (monto > 1000000000.0 || monto.isInfinite() || monto.isNaN())) {
            return new com.coopcredit.riskmock.model.RiskEvaluationResponse(document, 300, "ALTO",
                    "Monto excede límites razonables o es inválido");
        }

        // Validate reasonable term (max 360 months = 30 years)
        if (plazo != null && plazo > 360) {
            return new com.coopcredit.riskmock.model.RiskEvaluationResponse(document, 300, "ALTO",
                    "Plazo excede límites razonables (máximo 360 meses)");
        }

        try {

            // Create a deterministic seed based on document
            long documentHash = Math.abs(document.hashCode());
            long seed = documentHash & 0x7FFFFFFFL;
            java.util.Random random = new java.util.Random(seed);

            // Base score from document (300-950)
            int baseScore = 300 + random.nextInt(651);

            // Penalize high amounts (reduce score)
            // For every 10M, reduce score by 50 points
            int amountPenalty = 0;
            if (monto != null && monto > 5000000) {
                amountPenalty = (int) ((monto - 5000000) / 10000000.0 * 50);
                amountPenalty = Math.min(amountPenalty, 200); // Max penalty 200 points
            }

            // Penalize long terms (reduce score)
            // For every 12 months beyond 24, reduce score by 30 points
            int termPenalty = 0;
            if (plazo != null && plazo > 24) {
                termPenalty = ((plazo - 24) / 12) * 30;
                termPenalty = Math.min(termPenalty, 150); // Max penalty 150 points
            }

            // Calculate final score
            int score = baseScore - amountPenalty - termPenalty;
            score = Math.max(300, Math.min(950, score)); // Clamp between 300-950

            String nivelRiesgo;
            String detalle;

            if (score <= 500) {
                nivelRiesgo = "ALTO";
                detalle = "Historial crediticio deficiente. Reportes negativos vigentes.";
            } else if (score <= 700) {
                nivelRiesgo = "MEDIO";
                detalle = "Historial crediticio moderado. Capacidad de endeudamiento limitada.";
            } else {
                nivelRiesgo = "BAJO";
                detalle = "Excelente historial crediticio. Totalmente aprobado.";
            }

            return new com.coopcredit.riskmock.model.RiskEvaluationResponse(document, score, nivelRiesgo, detalle);

        } catch (Exception e) {
            return new com.coopcredit.riskmock.model.RiskEvaluationResponse(document, 300, "ALTO",
                    "Error en evaluación");
        }
    }
}
