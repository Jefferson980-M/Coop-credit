package com.coopcredit.riskmock.controller;

import com.coopcredit.riskmock.model.RiskEvaluationRequest;
import com.coopcredit.riskmock.model.RiskEvaluationResponse;
import com.coopcredit.riskmock.service.RiskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RiskController.class)
class RiskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RiskService riskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void evaluateRisk_ShouldAcceptDocumentoAndReturnRisk() throws Exception {
        RiskEvaluationRequest request = new RiskEvaluationRequest("123456", 5000.0, 12);

        RiskEvaluationResponse mockResponse = new RiskEvaluationResponse("123456", 600, "MEDIO", "Detalle");

        when(riskService.evaluateRisk(anyString(), org.mockito.ArgumentMatchers.any(),
                org.mockito.ArgumentMatchers.any()))
                .thenReturn(mockResponse);

        // Verify we can send "documento" (mapped from field `documento`)
        mockMvc.perform(post("/risk-evaluation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))) // ObjectMapper will use field name "documento"
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documento").value("123456"))
                .andExpect(jsonPath("$.score").value(600))
                .andExpect(jsonPath("$.nivelRiesgo").value("MEDIO"));
    }
}
