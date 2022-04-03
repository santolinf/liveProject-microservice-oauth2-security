package com.liveproject.oauth2.api.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class SecurityConfig {

    @Value("${liveproject.oauth2.authorization.jwt.public-key}")
    private String publicKey;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity.authorizeExchange()
                    .anyExchange().authenticated()
                .and()
                    .oauth2ResourceServer().jwt(c -> c.publicKey(publicKey()))
                .and()
                .build();
    }

    private RSAPublicKey publicKey() {
        try {
            var key = Base64.getDecoder().decode(publicKey);
            var x509 = new X509EncodedKeySpec(key);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(x509);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error generating public key");
        }
    }
}
