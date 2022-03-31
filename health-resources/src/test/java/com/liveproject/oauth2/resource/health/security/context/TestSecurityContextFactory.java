package com.liveproject.oauth2.resource.health.security.context;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestSecurityContextFactory implements WithSecurityContextFactory<TestUser> {

    @Override
    public SecurityContext createSecurityContext(TestUser testUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Instant issuesAt = Instant.now();
        Instant expiresAt = issuesAt.plusSeconds(1000);

        var jwt = Jwt.withTokenValue("token")
                .header("user_name", testUser.username())
                .claim("user_name", testUser.username())
                .issuedAt(issuesAt)
                .expiresAt(expiresAt)
                .build();

        List<GrantedAuthority> authorities = Arrays.stream(testUser.authorities())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        var authentication = new JwtAuthenticationToken(jwt, authorities);

        context.setAuthentication(authentication);

        return context;
    }
}
