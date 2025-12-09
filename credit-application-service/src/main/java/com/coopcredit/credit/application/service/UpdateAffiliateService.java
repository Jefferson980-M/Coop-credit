package com.coopcredit.credit.application.service;

import com.coopcredit.credit.domain.model.Affiliate;
import com.coopcredit.credit.domain.port.in.UpdateAffiliateUseCase;
import com.coopcredit.credit.domain.port.out.AffiliateRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateAffiliateService implements UpdateAffiliateUseCase {

    private final AffiliateRepositoryPort affiliateRepositoryPort;

    @Override
    @Transactional
    public Affiliate update(Long id, Affiliate affiliate) {
        Affiliate existing = affiliateRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Affiliate not found"));

        existing.setName(affiliate.getName());
        existing.setEmail(affiliate.getEmail());

        if (affiliate.getSalary() <= 0) {
            throw new RuntimeException("Salary must be greater than 0");
        }
        existing.setSalary(affiliate.getSalary());

        return affiliateRepositoryPort.save(existing);
    }
}
