package com.coopcredit.credit.infrastructure.adapter.out.persistence.repository;

import com.coopcredit.credit.infrastructure.adapter.out.persistence.entity.AffiliateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataAffiliateRepository extends JpaRepository<AffiliateEntity, Long> {
    Optional<AffiliateEntity> findByDocument(String document);

    Optional<AffiliateEntity> findByEmail(String email);

    boolean existsByDocument(String document);
}
