package com.coopcredit.credit.infrastructure.adapter.out.persistence.mapper;

import com.coopcredit.credit.domain.model.Affiliate;
import com.coopcredit.credit.infrastructure.adapter.out.persistence.entity.AffiliateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AffiliateMapper {

    AffiliateEntity toEntity(Affiliate domain);

    Affiliate toDomain(AffiliateEntity entity);
}
