package com.coopcredit.credit.infrastructure.adapter.out.persistence.adapter;

import com.coopcredit.credit.infrastructure.adapter.out.persistence.entity.AffiliateEntity;
import com.coopcredit.credit.infrastructure.adapter.out.persistence.repository.SpringDataAffiliateRepository;
import com.coopcredit.credit.domain.model.Affiliate;
import com.coopcredit.credit.domain.port.out.AffiliateRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import com.coopcredit.credit.infrastructure.adapter.out.persistence.mapper.AffiliateMapper;

@Component
@RequiredArgsConstructor
public class AffiliatePersistenceAdapter implements AffiliateRepositoryPort {

    private final SpringDataAffiliateRepository repository;
    private final AffiliateMapper mapper;

    @Override
    public Affiliate save(Affiliate affiliate) {
        AffiliateEntity entity = mapper.toEntity(affiliate);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Affiliate> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Affiliate> findByDocument(String document) {
        return repository.findByDocument(document).map(mapper::toDomain);
    }

    @Override
    public Optional<Affiliate> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public boolean existsByDocument(String document) {
        return repository.existsByDocument(document);
    }
}
