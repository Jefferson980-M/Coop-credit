package com.coopcredit.credit.infrastructure.adapter.out.persistence.mapper;

import com.coopcredit.credit.domain.model.CreditApplication;
import com.coopcredit.credit.infrastructure.adapter.out.persistence.entity.CreditApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { AffiliateMapper.class })
public interface CreditApplicationMapper {

    CreditApplicationEntity toEntity(CreditApplication domain);

    CreditApplication toDomain(CreditApplicationEntity entity);
}
