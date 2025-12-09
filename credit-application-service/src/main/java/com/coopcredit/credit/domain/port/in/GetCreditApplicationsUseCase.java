package com.coopcredit.credit.domain.port.in;

import com.coopcredit.credit.domain.model.CreditApplication;
import java.util.List;

public interface GetCreditApplicationsUseCase {
    List<CreditApplication> getApplicationsForAffiliate(String username);

    List<CreditApplication> getPendingApplications();

    List<CreditApplication> getAllApplications();
}
