package com.coopcredit.credit.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_applications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "CreditApplication.affiliateAndRisk", attributeNodes = {
        @NamedAttributeNode("affiliate"),
        @NamedAttributeNode("riskEvaluation")
})
public class CreditApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliate_id", nullable = false)
    private AffiliateEntity affiliate;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Integer term;

    private Double rate;

    private String purpose;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_evaluation_id", referencedColumnName = "id")
    private RiskEvaluationEntity riskEvaluation;
}
