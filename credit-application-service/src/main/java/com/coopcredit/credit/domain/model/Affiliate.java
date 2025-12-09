package com.coopcredit.credit.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Affiliate {
    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String document;
    private Double salary;
    private boolean active;
    private LocalDateTime createdAt;

    public Affiliate() {
    }

    public Affiliate(Long id, String name, String email, String document, Double salary, boolean active,
            LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.document = document;
        this.salary = salary;
        this.active = active;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ToString, HashCode, Equals - manual
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Affiliate affiliate = (Affiliate) o;
        return Objects.equals(id, affiliate.id) && Objects.equals(document, affiliate.document);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, document);
    }
}
