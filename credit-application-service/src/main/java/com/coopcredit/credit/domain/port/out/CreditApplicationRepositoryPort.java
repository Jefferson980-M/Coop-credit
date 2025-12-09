package com.coopcredit.credit.domain.port.out;

import com.coopcredit.credit.domain.model.CreditApplication;
import java.util.List;
import java.util.Optional;

public interface CreditApplicationRepositoryPort {
    CreditApplication save(CreditApplication creditApplication);

    Optional<CreditApplication> findById(Long id);

    List<CreditApplication> findByAffiliateUserId(Long userId);

    List<CreditApplication> findByAffiliateUsername(String username);

    List<CreditApplication> findByStatus(String status);

    List<CreditApplication> findAll();

    List<CreditApplication> findByAffiliateId(Long affiliateId);
}
