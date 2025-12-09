package com.coopcredit.credit.application.service;

import com.coopcredit.credit.domain.model.Affiliate;
import com.coopcredit.credit.domain.model.CreditApplication;
import com.coopcredit.credit.domain.port.in.RegisterCreditApplicationUseCase;
import com.coopcredit.credit.domain.port.out.AffiliateRepositoryPort;
import com.coopcredit.credit.domain.port.out.CreditApplicationRepositoryPort;

import java.time.LocalDateTime;

public class RegisterCreditApplicationService implements RegisterCreditApplicationUseCase {

    private final AffiliateRepositoryPort affiliateRepositoryPort;
    private final CreditApplicationRepositoryPort creditApplicationRepositoryPort;

    public RegisterCreditApplicationService(AffiliateRepositoryPort affiliateRepositoryPort,
            CreditApplicationRepositoryPort creditApplicationRepositoryPort) {
        this.affiliateRepositoryPort = affiliateRepositoryPort;
        this.creditApplicationRepositoryPort = creditApplicationRepositoryPort;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public CreditApplication register(Long affiliateId, Double amount, Integer term, Double rate, String purpose) {
        Affiliate affiliate = affiliateRepositoryPort.findById(affiliateId)
                .orElseThrow(() -> new RuntimeException("Affiliate not found"));

        if (!affiliate.isActive()) {
            throw new RuntimeException("Affiliate is not active");
        }

        double maxAllowedAmount = affiliate.getSalary() * 10.0;
        if (amount > maxAllowedAmount) {
            throw new RuntimeException(
                    String.format("Amount %.2f exceeds maximum allowed (10x salary = %.2f)",
                            amount, maxAllowedAmount));
        }

        CreditApplication application = new CreditApplication();
        application.setAffiliate(affiliate);
        application.setAmount(amount);
        application.setTerm(term);
        application.setRate(rate);
        application.setPurpose(purpose);
        application.setStatus("PENDIENTE");
        application.setCreatedAt(LocalDateTime.now());

        CreditApplication saved = creditApplicationRepositoryPort.save(application);

        return saved;
    }
}
