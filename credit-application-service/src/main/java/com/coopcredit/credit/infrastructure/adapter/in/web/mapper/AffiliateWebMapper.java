package com.coopcredit.credit.infrastructure.adapter.in.web.mapper;

import com.coopcredit.credit.domain.model.Affiliate;
import com.coopcredit.credit.infrastructure.adapter.in.web.dto.AffiliateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AffiliateWebMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Affiliate toDomain(AffiliateDto.Request request);

    AffiliateDto.Response toResponse(Affiliate domain);
}
