package com.coopcredit.credit.domain.port.in;

import com.coopcredit.credit.domain.model.Affiliate;

public interface RegisterAffiliateUseCase {
    Affiliate register(Affiliate affiliate);
}
