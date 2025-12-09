package com.coopcredit.credit.domain.model;

import java.time.LocalDateTime;

public class CreditApplication {
    private Long id;
    private Affiliate affiliate;
    private Double amount;
    private Integer term;
    private Double rate;
    private String purpose;
    private String status;
    private LocalDateTime createdAt;
    private RiskEvaluation riskEvaluation;

    public CreditApplication() {
    }

    public CreditApplication(Long id, Affiliate affiliate, Double amount, Integer term, String purpose, String status,
            LocalDateTime createdAt, RiskEvaluation riskEvaluation) {
        this.id = id;
        this.affiliate = affiliate;
        this.amount = amount;
        this.term = term;
        this.purpose = purpose;
        this.status = status;
        this.createdAt = createdAt;
        this.riskEvaluation = riskEvaluation;
    }

    // Getters / Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Affiliate getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affiliate affiliate) {
        this.affiliate = affiliate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public RiskEvaluation getRiskEvaluation() {
        return riskEvaluation;
    }

    public void setRiskEvaluation(RiskEvaluation riskEvaluation) {
        this.riskEvaluation = riskEvaluation;
    }
}
