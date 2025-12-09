package com.coopcredit.credit.domain.port.out;

import com.coopcredit.credit.domain.model.RiskEvaluation;

public interface RiskEvaluationPort {
    RiskEvaluation evaluate(String document, Double amount, Integer term);
}
