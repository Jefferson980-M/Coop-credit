package com.coopcredit.credit.infrastructure.adapter.out.external;

import com.coopcredit.credit.domain.model.RiskEvaluation;
import com.coopcredit.credit.domain.port.out.RiskEvaluationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Component
@RequiredArgsConstructor
public class RiskServiceAdapter implements RiskEvaluationPort {

    private final RestTemplate restTemplate;

    @Value("${app.risk-service.url:http://localhost:8081}")
    private String riskServiceUrl;

    @Override
    public RiskEvaluation evaluate(String document, Double amount, Integer term) {
        String url = riskServiceUrl + "/risk-evaluation";
        RiskRequest request = new RiskRequest(document, amount, term);

        try {
            ResponseEntity<RiskResponse> response = restTemplate.postForEntity(url, request, RiskResponse.class);
            if (response.getBody() != null) {
                RiskResponse body = response.getBody();
                String recommendation;
                if (body.getScore() >= 501) {
                    recommendation = "APROBADO";
                } else {
                    recommendation = "RECHAZADO";
                }

                return new RiskEvaluation(null, body.getScore(), body.getNivelRiesgo(), recommendation,
                        body.getDetalle());
            }
        } catch (Exception e) {

            throw new RuntimeException("Error communicating with Risk Service", e);
        }
        return new RiskEvaluation(null, 0, "UNKNOWN", "ERROR", "Communication Error");
    }

    @lombok.Data
    @lombok.AllArgsConstructor
    private static class RiskRequest {
        private String documento;
        private Double monto;
        private Integer plazo;
    }

    @lombok.Data
    @lombok.NoArgsConstructor
    private static class RiskResponse {
        private String documento;
        private int score;
        private String nivelRiesgo;
        private String detalle;
    }
}
