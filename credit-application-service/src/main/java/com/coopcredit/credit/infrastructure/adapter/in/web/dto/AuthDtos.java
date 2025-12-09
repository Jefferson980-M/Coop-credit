package com.coopcredit.credit.infrastructure.adapter.in.web.dto;

import lombok.Data;

public class AuthDtos {
    @Data
    public static class LoginRequest {
        private String username;
        private String password;
        private String role; // Optional: ROLE_AFILIADO, ROLE_ANALISTA, ROLE_ADMIN
    }

    @Data
    public static class AuthResponse {
        private String token;
        private java.util.List<String> roles;

        public AuthResponse(String token, java.util.List<String> roles) {
            this.token = token;
            this.roles = roles;
        }
    }
}
