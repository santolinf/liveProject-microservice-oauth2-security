package com.liveproject.oauth2.resource.health.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${liveproject.oauth2.authorization.jwt.public-key}")
    private String publicKey;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                        .mvcMatchers(HttpMethod.DELETE, "/metric/*", "/profile/*").hasAuthority("admin")
                        .mvcMatchers(HttpMethod.POST, "/advice").hasAuthority("advice")
                        .anyRequest().authenticated()
                .and().csrf().disable();

        http.oauth2ResourceServer().jwt(c -> {
            // verify the JWS
            c.decoder(jwtDecoder());
            // take the authorities from the Jwt claims and add them to the authenticated Principal in the Spring Security context
            c.jwtAuthenticationConverter(jwtAuthenticationConverter());
        });
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        var key = Base64.getDecoder().decode(publicKey);
        var x509 = new X509EncodedKeySpec(key);

        var rsaPublicKey = generatePublicKeyFromSpec(x509);
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
    }

    @SuppressWarnings("unchecked")
    @Bean
    public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        var converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> authorities = (List<String>) jwt.getClaims().get("authorities");
            return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        });

        return converter;
    }

    private RSAPublicKey generatePublicKeyFromSpec(KeySpec keySpec) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error generating public key");
        }
    }
}
