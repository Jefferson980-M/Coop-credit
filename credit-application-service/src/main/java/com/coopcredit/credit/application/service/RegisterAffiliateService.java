package com.coopcredit.credit.application.service;

import com.coopcredit.credit.domain.model.Affiliate;
import com.coopcredit.credit.domain.port.in.RegisterAffiliateUseCase;
import com.coopcredit.credit.domain.port.out.AffiliateRepositoryPort;
import java.time.LocalDateTime;

public class RegisterAffiliateService implements RegisterAffiliateUseCase {

    private final AffiliateRepositoryPort affiliateRepositoryPort;

    public RegisterAffiliateService(AffiliateRepositoryPort affiliateRepositoryPort) {
        this.affiliateRepositoryPort = affiliateRepositoryPort;
    }

    @Override
    public Affiliate register(Affiliate affiliate) {
        if (affiliateRepositoryPort.existsByDocument(affiliate.getDocument())) {
            throw new RuntimeException("Affiliate with document " + affiliate.getDocument() + " already exists.");
        }
        if (affiliate.getSalary() <= 0) {
            throw new RuntimeException("Salary must be greater than 0");
        }

        affiliate.setActive(true);
        affiliate.setCreatedAt(LocalDateTime.now());
        return affiliateRepositoryPort.save(affiliate);
    }
}
