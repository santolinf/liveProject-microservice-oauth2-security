package com.liveproject.oauth2.api.gateway;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.liveproject.oauth2.api.gateway.model.HealthMetric;
import com.liveproject.oauth2.api.gateway.model.HealthProfile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.liveproject.oauth2.api.gateway.model.enums.HealthMetricType.ECG;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

public class GatewayToMetricEndpointTest extends BaseTests {

    @AfterEach
    void resetAll() {
        wireMockServer.resetAll();
    }

    @Test
    public void whenReceiveFindMetricsRequest_thenRouteToGetHealthMetricsEndpoint() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlMatching("/metric"))
                        .willReturn(aResponse().withStatus(OK.value())));

        client.mutateWith(mockJwt())
                .get()
                .uri("/metric")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void whenReceiveAddMetricRequest_thenRouteToPostMetricEndpoint() {
        HealthMetric metric = new HealthMetric();
        metric.setType(ECG);
        metric.setValue(60);
        HealthProfile profile = new HealthProfile();
        profile.setUsername("john");
        metric.setProfile(profile);

        wireMockServer.stubFor(WireMock.post(WireMock.urlMatching("/metric"))
                .willReturn(aResponse().withStatus(OK.value())));

        client.mutateWith(mockJwt())
                .mutateWith(csrf())
                .post()
                .uri("/metric")
                .bodyValue(metric)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void whenReceiveDeleteMetricRequest_thenRouteToDeleteMetricEndpoint() {
        wireMockServer.stubFor(WireMock.delete(WireMock.urlMatching("/metric/john"))
                .willReturn(aResponse().withStatus(OK.value())));

        client.mutateWith(mockJwt())
                .mutateWith(csrf())
                .delete()
                .uri("/metric/john")
                .exchange()
                .expectStatus().isOk();
    }
}
