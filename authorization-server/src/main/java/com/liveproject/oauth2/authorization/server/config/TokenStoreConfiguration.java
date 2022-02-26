package com.liveproject.oauth2.authorization.server.config;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "liveproject.oauth2.authorization.jwt")
@Setter
@RequiredArgsConstructor
public class TokenStoreConfiguration {

    private String keyAlias;

    private Resource keyStore;

    private char[] keyStorePassword;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(converter());
    }

    @Bean
    public JwtAccessTokenConverter converter() {
        var converter = new JwtAccessTokenConverter();

        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(keyStore, keyStorePassword);
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(keyAlias));

        return converter;
    }
}
