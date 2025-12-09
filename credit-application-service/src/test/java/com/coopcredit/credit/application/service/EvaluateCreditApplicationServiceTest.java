package com.coopcredit.credit.application.service;

import com.coopcredit.credit.domain.model.Affiliate;
import com.coopcredit.credit.domain.model.CreditApplication;
import com.coopcredit.credit.domain.model.RiskEvaluation;
import com.coopcredit.credit.domain.port.out.CreditApplicationRepositoryPort;
import com.coopcredit.credit.domain.port.out.RiskEvaluationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvaluateCreditApplicationServiceTest {

    @Mock
    private CreditApplicationRepositoryPort creditRepositoryPort;

    @Mock
    private RiskEvaluationPort riskEvaluationPort;

    @InjectMocks
    private EvaluateCreditApplicationService service;

    private CreditApplication application;
    private Affiliate affiliate;

    @BeforeEach
    void setUp() {
        affiliate = new Affiliate();
        affiliate.setId(1L);
        affiliate.setDocument("12345");

        application = new CreditApplication();
        application.setId(100L);
        application.setAffiliate(affiliate);
        application.setAmount(1000.0);
        application.setTerm(12);
        application.setStatus("PENDING");
    }

    @Test
    void evaluate_ShouldApprove_WhenRiskIsApproved() {
        when(creditRepositoryPort.findById(100L)).thenReturn(Optional.of(application));

        RiskEvaluation safeRisk = new RiskEvaluation(null, 800, "BAJO", "APPROVED", "All good");
        when(riskEvaluationPort.evaluate("12345", 1000.0, 12)).thenReturn(safeRisk);

        when(creditRepositoryPort.save(any(CreditApplication.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreditApplication result = service.evaluate(100L);

        assertEquals("APPROVED", result.getStatus());
        assertNotNull(result.getRiskEvaluation());
        assertEquals(800, result.getRiskEvaluation().getScore());
    }

    @Test
    void evaluate_ShouldReject_WhenRiskIsRejected() {
        when(creditRepositoryPort.findById(100L)).thenReturn(Optional.of(application));

        RiskEvaluation riskyRisk = new RiskEvaluation(null, 400, "ALTO", "REJECTED", "Too risky");
        when(riskEvaluationPort.evaluate("12345", 1000.0, 12)).thenReturn(riskyRisk);

        when(creditRepositoryPort.save(any(CreditApplication.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreditApplication result = service.evaluate(100L);

        assertEquals("REJECTED", result.getStatus());
        assertEquals("ALTO", result.getRiskEvaluation().getRiskLevel());
    }
}
