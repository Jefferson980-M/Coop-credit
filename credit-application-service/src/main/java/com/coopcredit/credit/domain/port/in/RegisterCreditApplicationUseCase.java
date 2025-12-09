package com.coopcredit.credit.domain.port.in;

import com.coopcredit.credit.domain.model.CreditApplication;

public interface RegisterCreditApplicationUseCase {
    CreditApplication register(Long affiliateId, Double amount, Integer term, Double rate, String purpose);
}
