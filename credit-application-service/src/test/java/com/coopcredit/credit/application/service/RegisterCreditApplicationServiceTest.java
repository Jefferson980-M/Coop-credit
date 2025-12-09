package com.coopcredit.credit.application.service;

import com.coopcredit.credit.domain.model.Affiliate;
import com.coopcredit.credit.domain.model.CreditApplication;
import com.coopcredit.credit.domain.port.in.EvaluateCreditApplicationUseCase;
import com.coopcredit.credit.domain.port.out.AffiliateRepositoryPort;
import com.coopcredit.credit.domain.port.out.CreditApplicationRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterCreditApplicationServiceTest {

    @Mock
    private AffiliateRepositoryPort affiliateRepositoryPort;

    @Mock
    private CreditApplicationRepositoryPort creditApplicationRepositoryPort;

    @Mock
    private EvaluateCreditApplicationUseCase evaluateCreditApplicationUseCase;

    @InjectMocks
    private RegisterCreditApplicationService service;

    private Affiliate activeAffiliate;

    @BeforeEach
    void setUp() {
        activeAffiliate = new Affiliate();
        activeAffiliate.setId(1L);
        activeAffiliate.setActive(true);
        activeAffiliate.setSalary(2000.0);
        activeAffiliate.setCreatedAt(LocalDateTime.now().minusMonths(4)); // > 3 months
    }

    @Test
    void register_ShouldRegisterAndEvaluate_WhenValid() {
        when(affiliateRepositoryPort.findById(1L)).thenReturn(Optional.of(activeAffiliate));

        CreditApplication savedApp = new CreditApplication();
        savedApp.setId(100L);
        when(creditApplicationRepositoryPort.save(any(CreditApplication.class))).thenReturn(savedApp);

        CreditApplication evaluatedApp = new CreditApplication();
        evaluatedApp.setId(100L);
        evaluatedApp.setStatus("APPROVED");
        when(evaluateCreditApplicationUseCase.evaluate(100L)).thenReturn(evaluatedApp);

        CreditApplication result = service.register(1L, 5000.0, 12, 1.5, "Test");

        assertNotNull(result);
        verify(creditApplicationRepositoryPort).save(any(CreditApplication.class));
        verify(evaluateCreditApplicationUseCase).evaluate(100L);
    }

    @Test
    void register_ShouldThrowException_WhenAffiliateInactive() {
        activeAffiliate.setActive(false);
        when(affiliateRepositoryPort.findById(1L)).thenReturn(Optional.of(activeAffiliate));

        assertThrows(RuntimeException.class, () -> service.register(1L, 5000.0, 12, 1.5, "Test"));
    }

    @Test
    void register_ShouldThrowException_WhenAmountTooHigh() {
        // Salary 2000, Max 20000. Request 25000.
        when(affiliateRepositoryPort.findById(1L)).thenReturn(Optional.of(activeAffiliate));

        assertThrows(RuntimeException.class, () -> service.register(1L, 25000.0, 12, 1.5, "Test"));
    }

    @Test
    void register_ShouldThrowException_WhenMonthlyPaymentTooHigh() {
        // Salary 2000. 30% = 600.
        // Request 6500 for 10 months = 650/month > 600.
        when(affiliateRepositoryPort.findById(1L)).thenReturn(Optional.of(activeAffiliate));

        assertThrows(RuntimeException.class, () -> service.register(1L, 6500.0, 10, 1.5, "Test"));
    }
}
