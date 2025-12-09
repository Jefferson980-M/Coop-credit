package com.coopcredit.credit.infrastructure.adapter.in.web;

import com.coopcredit.credit.domain.model.Affiliate;
import com.coopcredit.credit.domain.port.in.RegisterAffiliateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.coopcredit.credit.infrastructure.adapter.in.web.mapper.AffiliateWebMapper;
import com.coopcredit.credit.domain.port.in.UpdateAffiliateUseCase;

@RestController
@RequestMapping("/affiliates")
@RequiredArgsConstructor
public class AffiliateController {

    private final RegisterAffiliateUseCase registerAffiliateUseCase;
    private final UpdateAffiliateUseCase updateAffiliateUseCase;
    private final AffiliateWebMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALISTA')")
    @io.swagger.v3.oas.annotations.Operation(summary = "Register new affiliate", description = "Creates a new affiliate. Accessible by ADMIN and ANALISTA roles.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Affiliate registration data", required = true, content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.infrastructure.adapter.in.web.dto.AffiliateDto.Request.class), examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "Affiliate Example", value = "{\"name\": \"María González\", \"email\": \"maria@example.com\", \"document\": \"9876543210\", \"salary\": 5000000}")))
    public ResponseEntity<com.coopcredit.credit.infrastructure.adapter.in.web.dto.AffiliateDto.Response> registerAffiliate(
            @jakarta.validation.Valid @RequestBody com.coopcredit.credit.infrastructure.adapter.in.web.dto.AffiliateDto.Request request) {
        Affiliate domain = mapper.toDomain(request);
        Affiliate saved = registerAffiliateUseCase.register(domain);
        return ResponseEntity.ok(mapper.toResponse(saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALISTA')")
    public ResponseEntity<com.coopcredit.credit.infrastructure.adapter.in.web.dto.AffiliateDto.Response> updateAffiliate(
            @PathVariable Long id,
            @jakarta.validation.Valid @RequestBody com.coopcredit.credit.infrastructure.adapter.in.web.dto.AffiliateDto.Request request) {
        Affiliate domain = mapper.toDomain(request);

        return ResponseEntity.ok(mapper.toResponse(updateAffiliateUseCase.update(id, domain)));
    }
}
