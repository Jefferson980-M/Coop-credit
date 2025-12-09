package com.coopcredit.credit.infrastructure.adapter.in.web;

import com.coopcredit.credit.domain.model.CreditApplication;
import com.coopcredit.credit.domain.port.in.RegisterCreditApplicationUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.coopcredit.credit.domain.port.in.GetCreditApplicationsUseCase;
import com.coopcredit.credit.infrastructure.adapter.in.web.mapper.CreditApplicationWebMapper;
import java.util.List;

import com.coopcredit.credit.infrastructure.adapter.in.web.dto.CreditApplicationDto;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/credit-applications")
@RequiredArgsConstructor
public class CreditApplicationController {

        private final RegisterCreditApplicationUseCase registerCreditApplicationUseCase;
        private final GetCreditApplicationsUseCase getCreditApplicationsUseCase;
        private final com.coopcredit.credit.domain.port.in.EvaluateCreditApplicationUseCase evaluateCreditApplicationUseCase;
        private final CreditApplicationWebMapper mapper;

        @PostMapping
        @PreAuthorize("hasRole('AFILIADO') or hasRole('ADMIN')")
        @io.swagger.v3.oas.annotations.Operation(summary = "Create credit application", description = "Creates a new credit application for an affiliate. Accessible by AFILIADO and ADMIN roles.")
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credit application request", required = true, content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CreditApplicationDto.Request.class), examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "Credit Application Example", value = "{\"affiliateId\": 1, \"amount\": 10000000, \"term\": 24, \"rate\": 1.5, \"purpose\": \"Compra de vehículo\"}")))
        public ResponseEntity<CreditApplicationDto.Response> registerApplication(
                        @jakarta.validation.Valid @RequestBody CreditApplicationDto.Request request) {
                CreditApplication saved = registerCreditApplicationUseCase.register(
                                request.getAffiliateId(),
                                request.getAmount(),
                                request.getTerm(),
                                request.getRate(),
                                request.getPurpose());
                return ResponseEntity.ok(mapper.toResponse(saved));
        }

        @GetMapping
        @PreAuthorize("hasRole('AFILIADO') or hasRole('ANALISTA') or hasRole('ADMIN')")
        public ResponseEntity<List<CreditApplicationDto.Response>> getApplications(
                        @org.springframework.security.core.annotation.AuthenticationPrincipal UserDetails userDetails) {

                boolean isAnalista = userDetails.getAuthorities().stream()
                                .anyMatch(a -> a.getAuthority().equals("ROLE_ANALISTA"));

                boolean isAfiliado = userDetails.getAuthorities().stream()
                                .anyMatch(a -> a.getAuthority().equals("ROLE_AFILIADO"));

                if (isAnalista) {
                        return ResponseEntity.ok(getCreditApplicationsUseCase.getPendingApplications().stream()
                                        .map(mapper::toResponse)
                                        .collect(java.util.stream.Collectors.toList()));
                } else if (isAfiliado) {
                        return ResponseEntity
                                        .ok(getCreditApplicationsUseCase
                                                        .getApplicationsForAffiliate(userDetails.getUsername()).stream()
                                                        .map(mapper::toResponse)
                                                        .collect(java.util.stream.Collectors.toList()));
                } else {
                        // Admin sees all
                        return ResponseEntity.ok(getCreditApplicationsUseCase.getAllApplications().stream()
                                        .map(mapper::toResponse)
                                        .collect(java.util.stream.Collectors.toList()));
                }
        }

        @PutMapping("/{id}/evaluate")
        @PreAuthorize("hasRole('ANALISTA') or hasRole('ADMIN')")
        @io.swagger.v3.oas.annotations.Operation(summary = "Evaluate credit application", description = "Evaluates a pending credit application. Only ANALISTA and ADMIN can evaluate.")
        public ResponseEntity<CreditApplicationDto.Response> evaluateApplication(@PathVariable Long id) {
                CreditApplication evaluated = evaluateCreditApplicationUseCase.evaluate(id);
                return ResponseEntity.ok(mapper.toResponse(evaluated));
        }
}
