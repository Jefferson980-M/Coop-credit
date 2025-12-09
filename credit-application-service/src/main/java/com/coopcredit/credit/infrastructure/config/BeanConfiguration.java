package com.coopcredit.credit.infrastructure.config;

import com.coopcredit.credit.application.service.EvaluateCreditApplicationService;
import com.coopcredit.credit.application.service.RegisterAffiliateService;
import com.coopcredit.credit.application.service.RegisterCreditApplicationService;
import com.coopcredit.credit.domain.port.in.EvaluateCreditApplicationUseCase;
import com.coopcredit.credit.domain.port.in.RegisterAffiliateUseCase;
import com.coopcredit.credit.domain.port.in.RegisterCreditApplicationUseCase;
import com.coopcredit.credit.domain.port.out.AffiliateRepositoryPort;
import com.coopcredit.credit.domain.port.out.CreditApplicationRepositoryPort;
import com.coopcredit.credit.domain.port.out.RiskEvaluationPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public RegisterAffiliateUseCase registerAffiliateUseCase(AffiliateRepositoryPort affiliateRepositoryPort) {
        return new RegisterAffiliateService(affiliateRepositoryPort);
    }

    @Bean
    public EvaluateCreditApplicationUseCase evaluateCreditApplicationUseCase(
            CreditApplicationRepositoryPort creditRepositoryPort,
            RiskEvaluationPort riskEvaluationPort) {
        return new EvaluateCreditApplicationService(creditRepositoryPort, riskEvaluationPort);
    }

    @Bean
    public RegisterCreditApplicationUseCase registerCreditApplicationUseCase(
            AffiliateRepositoryPort affiliateRepositoryPort,
            CreditApplicationRepositoryPort creditApplicationRepositoryPort) {
        return new RegisterCreditApplicationService(affiliateRepositoryPort, creditApplicationRepositoryPort);
    }
}
