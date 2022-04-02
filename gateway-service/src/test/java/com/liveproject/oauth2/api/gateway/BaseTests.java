package com.liveproject.oauth2.api.gateway;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.liveproject.oauth2.api.gateway.context.WireMockInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@ContextConfiguration(initializers = WireMockInitializer.class)
public abstract class BaseTests {

    @Autowired
    protected WebTestClient client;

    @Autowired
    protected WireMockServer wireMockServer;
}
