package com.coopcredit.credit.application.service;

import com.coopcredit.credit.domain.model.CreditApplication;
import com.coopcredit.credit.domain.port.in.GetCreditApplicationsUseCase;
import com.coopcredit.credit.domain.port.out.CreditApplicationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCreditApplicationsService implements GetCreditApplicationsUseCase {

    private final CreditApplicationRepositoryPort creditRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public List<CreditApplication> getApplicationsForAffiliate(String username) {

        return creditRepositoryPort.findByAffiliateUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditApplication> getPendingApplications() {
        return creditRepositoryPort.findByStatus("PENDIENTE");
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditApplication> getAllApplications() {
        return creditRepositoryPort.findAll();
    }
}
