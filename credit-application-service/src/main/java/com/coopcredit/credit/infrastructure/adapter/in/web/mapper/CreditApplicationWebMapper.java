package com.coopcredit.credit.infrastructure.adapter.in.web.mapper;

import com.coopcredit.credit.domain.model.CreditApplication;
import com.coopcredit.credit.domain.model.RiskEvaluation;
import com.coopcredit.credit.infrastructure.adapter.in.web.dto.CreditApplicationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreditApplicationWebMapper {

    @Mapping(target = "affiliateId", source = "affiliate.id")
    CreditApplicationDto.Response toResponse(CreditApplication domain);

    CreditApplicationDto.RiskEvaluationDto toRiskResponse(RiskEvaluation domain);
}
