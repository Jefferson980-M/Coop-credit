package com.coopcredit.credit.domain.port.in;

import com.coopcredit.credit.domain.model.CreditApplication;

public interface EvaluateCreditApplicationUseCase {
    CreditApplication evaluate(Long applicationId);
}
