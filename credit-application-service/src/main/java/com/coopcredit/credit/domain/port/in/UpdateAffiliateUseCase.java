package com.coopcredit.credit.domain.port.in;

import com.coopcredit.credit.domain.model.Affiliate;

public interface UpdateAffiliateUseCase {
    Affiliate update(Long id, Affiliate affiliate);
}
