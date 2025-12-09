package com.coopcredit.credit.infrastructure.adapter.in.web;

import com.coopcredit.credit.infrastructure.adapter.in.web.dto.AuthDtos.LoginRequest;
import com.coopcredit.credit.infrastructure.adapter.in.web.dto.AuthDtos.AuthResponse;
import com.coopcredit.credit.infrastructure.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coopcredit.credit.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final SpringDataUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        java.util.List<String> roles = authentication.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(new AuthResponse(jwt, roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody LoginRequest signUpRequest) {
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        // Determine role: use provided role or default to ROLE_AFILIADO
        String role = signUpRequest.getRole();
        if (role == null || role.isEmpty()) {
            role = "ROLE_AFILIADO";
        } else if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        // Validate role
        if (!role.equals("ROLE_AFILIADO") && !role.equals("ROLE_ANALISTA") && !role.equals("ROLE_ADMIN")) {
            return ResponseEntity.badRequest().body("Invalid role. Valid roles: AFILIADO, ANALISTA, ADMIN");
        }

        com.coopcredit.credit.infrastructure.adapter.out.persistence.entity.UserEntity user = com.coopcredit.credit.infrastructure.adapter.out.persistence.entity.UserEntity
                .builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles(java.util.Collections.singleton(role))
                .build();

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully with role: " + role);
    }
}
