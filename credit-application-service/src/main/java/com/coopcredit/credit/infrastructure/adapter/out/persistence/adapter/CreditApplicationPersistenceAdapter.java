package com.coopcredit.credit.infrastructure.adapter.out.persistence.adapter;

import com.coopcredit.credit.infrastructure.adapter.out.persistence.entity.CreditApplicationEntity;
import com.coopcredit.credit.infrastructure.adapter.out.persistence.repository.SpringDataCreditApplicationRepository;
import com.coopcredit.credit.domain.model.CreditApplication;
import com.coopcredit.credit.domain.port.out.CreditApplicationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreditApplicationPersistenceAdapter implements CreditApplicationRepositoryPort {

    private final SpringDataCreditApplicationRepository repository;
    private final com.coopcredit.credit.infrastructure.adapter.out.persistence.mapper.CreditApplicationMapper mapper;

    @Override
    public CreditApplication save(CreditApplication application) {
        CreditApplicationEntity entity = mapper.toEntity(application);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public java.util.Optional<CreditApplication> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public java.util.List<CreditApplication> findByAffiliateUserId(Long userId) {
        return repository.findByAffiliateUserId(userId).stream()
                .map(mapper::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public java.util.List<CreditApplication> findByAffiliateUsername(String username) {
        return repository.findByAffiliateUsername(username).stream()
                .map(mapper::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public java.util.List<CreditApplication> findByStatus(String status) {
        return repository.findByStatus(status).stream()
                .map(mapper::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<CreditApplication> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<CreditApplication> findByAffiliateId(Long affiliateId) {
        return repository.findByAffiliateId(affiliateId).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

}
