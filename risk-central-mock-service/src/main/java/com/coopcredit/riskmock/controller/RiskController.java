package com.coopcredit.riskmock.controller;

import com.coopcredit.riskmock.model.RiskEvaluationRequest;
import com.coopcredit.riskmock.model.RiskEvaluationResponse;
import com.coopcredit.riskmock.service.RiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/risk-evaluation")
@RequiredArgsConstructor
public class RiskController {

    private final RiskService riskService;

    @PostMapping
    public ResponseEntity<RiskEvaluationResponse> evaluateRisk(
            @jakarta.validation.Valid @RequestBody RiskEvaluationRequest request) {
        RiskEvaluationResponse response = riskService.evaluateRisk(
                request.getDocumento(),
                request.getMonto(),
                request.getPlazo());
        return ResponseEntity.ok(response);
    }
}
