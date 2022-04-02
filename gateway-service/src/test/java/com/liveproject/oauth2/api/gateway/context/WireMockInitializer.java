package com.liveproject.oauth2.api.gateway.context;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

public class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().port(7070));

        wireMockServer.start();

        applicationContext.addApplicationListener(applicationEvent -> {
            if (ContextClosedEvent.class.isAssignableFrom(applicationEvent.getClass())) {
                wireMockServer.stop();
            }
        });

        // register as a bean
        applicationContext.getBeanFactory().registerSingleton("wireMockServer", wireMockServer);

        TestPropertyValues.of(
                "liveproject.oauth2.authorization.health.resources.url=" + wireMockServer.baseUrl()
        ).applyTo(applicationContext);
    }
}
