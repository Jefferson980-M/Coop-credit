
package com.coopcredit.credit.domain.port.out;

import com.coopcredit.credit.domain.model.Affiliate;
import java.util.Optional;

public interface AffiliateRepositoryPort {
    Affiliate save(Affiliate affiliate);

    Optional<Affiliate> findById(Long id);

    Optional<Affiliate> findByDocument(String document);

    Optional<Affiliate> findByEmail(String email);

    boolean existsByDocument(String document);
}
