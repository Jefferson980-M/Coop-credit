package com.coopcredit.credit.infrastructure.adapter.out.persistence;

import com.coopcredit.credit.AbstractIntegrationTest;
import com.coopcredit.credit.infrastructure.adapter.out.persistence.entity.AffiliateEntity;
import com.coopcredit.credit.infrastructure.adapter.out.persistence.repository.SpringDataAffiliateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
class AffiliateRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private SpringDataAffiliateRepository repository;

    @Test
    void shouldFindAffiliateByDocument() {
        AffiliateEntity affiliate = AffiliateEntity.builder()
                .name("Test User")
                .email("test@integration.com")
                .document("99887766")
                .salary(3000.0)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(affiliate);

        Optional<AffiliateEntity> found = repository.findByDocument("99887766");
        assertTrue(found.isPresent());
    }
}
