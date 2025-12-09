package com.coopcredit.credit.domain.model;

public class RiskEvaluation {
    private Long id;
    private Integer score;
    private String riskLevel;
    private String recommendation;
    private String detail;

    public RiskEvaluation() {
    }

    public RiskEvaluation(Long id, Integer score, String riskLevel, String recommendation, String detail) {
        this.id = id;
        this.score = score;
        this.riskLevel = riskLevel;
        this.recommendation = recommendation;
        this.detail = detail;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
