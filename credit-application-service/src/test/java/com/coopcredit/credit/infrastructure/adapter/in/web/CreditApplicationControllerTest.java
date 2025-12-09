package com.coopcredit.credit.infrastructure.adapter.in.web;

import com.coopcredit.credit.domain.model.CreditApplication;
import com.coopcredit.credit.domain.port.in.RegisterCreditApplicationUseCase;
import com.coopcredit.credit.infrastructure.config.security.JwtTokenProvider;
import com.coopcredit.credit.infrastructure.config.security.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.coopcredit.credit.infrastructure.adapter.in.web.mapper.CreditApplicationWebMapper;
import com.coopcredit.credit.infrastructure.config.security.JwtAuthenticationFilter;
import com.coopcredit.credit.infrastructure.config.SecurityConfig;

@WebMvcTest(CreditApplicationController.class)
@AutoConfigureMockMvc
@Import({ SecurityConfig.class,
                JwtAuthenticationFilter.class })
class CreditApplicationControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private RegisterCreditApplicationUseCase registerCreditApplicationUseCase;

        @MockBean
        private CreditApplicationWebMapper mapper;

        @MockBean
        private JwtTokenProvider jwtTokenProvider;
        @MockBean
        private CustomUserDetailsService customUserDetailsService;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        @WithMockUser(roles = "AFILIADO")
        void createApplication_WhenAffiliate_ShouldReturn200() throws Exception {
                com.coopcredit.credit.infrastructure.adapter.in.web.dto.CreditApplicationDto.Request request = com.coopcredit.credit.infrastructure.adapter.in.web.dto.CreditApplicationDto.Request
                                .builder()
                                .affiliateId(1L)
                                .amount(1000.0)
                                .term(12)
                                .purpose("Test")
                                .build();

                CreditApplication mockApp = new CreditApplication();
                mockApp.setId(101L);
                mockApp.setStatus("PENDING");

                com.coopcredit.credit.infrastructure.adapter.in.web.dto.CreditApplicationDto.Response mockResponse = com.coopcredit.credit.infrastructure.adapter.in.web.dto.CreditApplicationDto.Response
                                .builder()
                                .id(101L)
                                .status("PENDING")
                                .build();

                when(registerCreditApplicationUseCase.register(anyLong(), anyDouble(), anyInt(), anyDouble(),
                                anyString()))
                                .thenReturn(mockApp);
                when(mapper.toResponse(any(CreditApplication.class))).thenReturn(mockResponse);

                mockMvc.perform(post("/credit-applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void createApplication_WhenAdmin_ShouldAlsoWorkOrFailDependingOnConfig() throws Exception {
                com.coopcredit.credit.infrastructure.adapter.in.web.dto.CreditApplicationDto.Request request = com.coopcredit.credit.infrastructure.adapter.in.web.dto.CreditApplicationDto.Request
                                .builder()
                                .affiliateId(1L)
                                .amount(1000.0)
                                .term(12)
                                .build();

                mockMvc.perform(post("/credit-applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isForbidden());
        }
}
