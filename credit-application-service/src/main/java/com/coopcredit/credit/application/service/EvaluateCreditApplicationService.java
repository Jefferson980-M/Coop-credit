package com.coopcredit.credit.application.service;

import com.coopcredit.credit.domain.model.CreditApplication;
import com.coopcredit.credit.domain.model.RiskEvaluation;
import com.coopcredit.credit.domain.port.in.EvaluateCreditApplicationUseCase;
import com.coopcredit.credit.domain.port.out.CreditApplicationRepositoryPort;
import com.coopcredit.credit.domain.port.out.RiskEvaluationPort;

public class EvaluateCreditApplicationService implements EvaluateCreditApplicationUseCase {

    private final CreditApplicationRepositoryPort creditRepositoryPort;
    private final RiskEvaluationPort riskEvaluationPort;

    private static final double MAX_DEBT_RATIO = 0.30;
    private static final double MAX_AMOUNT_MULTIPLIER = 10.0;

    public EvaluateCreditApplicationService(CreditApplicationRepositoryPort creditRepositoryPort,
            RiskEvaluationPort riskEvaluationPort) {
        this.creditRepositoryPort = creditRepositoryPort;
        this.riskEvaluationPort = riskEvaluationPort;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public CreditApplication evaluate(Long applicationId) {
        CreditApplication application = creditRepositoryPort.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        RiskEvaluation evaluation = riskEvaluationPort.evaluate(
                application.getAffiliate().getDocument(),
                application.getAmount(),
                application.getTerm());


        String recommendation = evaluation.getRecommendation();

        if (!"RECHAZADO".equals(recommendation)) {
            if (application.getAmount() > application.getAffiliate().getSalary() * MAX_AMOUNT_MULTIPLIER) {
                recommendation = "RECHAZADO";
                evaluation.setDetail(evaluation.getDetail() + " | Internal Policy: Amount exceeds 10x salary");
            }

            Double rate = application.getRate();
            if (rate != null && rate > 0) {
                double monthlyRate = rate / 100.0;
                double installment = (application.getAmount() * monthlyRate)
                        / (1 - Math.pow(1 + monthlyRate, -application.getTerm()));

                if (installment > application.getAffiliate().getSalary() * MAX_DEBT_RATIO) {
                    recommendation = "RECHAZADO";
                    evaluation.setDetail(evaluation.getDetail() + " | Internal Policy: Installment > 30% Salary");
                }
            }
        }

        evaluation.setRecommendation(recommendation);
        application.setRiskEvaluation(evaluation);
        application.setStatus(recommendation);

        return creditRepositoryPort.save(application);
    }
}
