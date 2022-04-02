package com.liveproject.oauth2.api.gateway;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.liveproject.oauth2.api.gateway.model.HealthAdvice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.springframework.http.HttpStatus.OK;

public class GatewayToAdviceEndpointTest extends BaseTests {

    @AfterEach
    void resetAll() {
        wireMockServer.resetAll();
    }

    @Test
    public void whenReceiveAddMetricRequest_thenRouteToPostMetricEndpoint() {
        HealthAdvice advice = new HealthAdvice();
        advice.setUsername("john");
        advice.setAdvice("advice");

        wireMockServer.stubFor(WireMock.post(WireMock.urlMatching("/advice"))
                .willReturn(aResponse().withStatus(OK.value())));

        client.post()
                .uri("/advice")
                .bodyValue(advice)
                .exchange()
                .expectStatus().isOk();
    }
}
