package com.coopcredit.credit.infrastructure.adapter.out.persistence.repository;

import com.coopcredit.credit.infrastructure.adapter.out.persistence.entity.CreditApplicationEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataCreditApplicationRepository extends JpaRepository<CreditApplicationEntity, Long> {
        @EntityGraph(value = "CreditApplication.affiliateAndRisk")
        List<CreditApplicationEntity> findAll();

        @EntityGraph(value = "CreditApplication.affiliateAndRisk")
        List<CreditApplicationEntity> findByAffiliateId(Long affiliateId);

        @org.springframework.data.jpa.repository.EntityGraph(attributePaths = { "affiliate", "riskEvaluation" })
        java.util.List<CreditApplicationEntity> findByStatus(String status);

        @org.springframework.data.jpa.repository.EntityGraph(attributePaths = { "affiliate", "riskEvaluation" })
        @org.springframework.data.jpa.repository.Query("SELECT c FROM CreditApplicationEntity c WHERE c.affiliate.userId = :userId")
        java.util.List<CreditApplicationEntity> findByAffiliateUserId(
                        @org.springframework.data.repository.query.Param("userId") Long userId);

        @org.springframework.data.jpa.repository.EntityGraph(attributePaths = { "affiliate", "riskEvaluation" })
        @org.springframework.data.jpa.repository.Query("SELECT c FROM CreditApplicationEntity c WHERE c.affiliate.userId IN (SELECT u.id FROM UserEntity u WHERE u.username = :username)")
        java.util.List<CreditApplicationEntity> findByAffiliateUsername(
                        @org.springframework.data.repository.query.Param("username") String username);
}
