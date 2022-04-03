package com.liveproject.oauth2.api.gateway;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.liveproject.oauth2.api.gateway.model.HealthProfile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

public class GatewayToProfileEndpointTest extends BaseTests {

    @AfterEach
    void resetAll() {
        wireMockServer.resetAll();
    }

    @Test
    public void whenReceiveProfileRequest_thenRouteToGetProfileEndpoint() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlMatching("/profile/john"))
                        .willReturn(aResponse().withStatus(OK.value())));

        client.mutateWith(mockJwt())
                .get()
                .uri("/profile/john")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void whenReceiveAddProfileRequest_thenRouteToPostProfileEndpoint() {
        HealthProfile profile = new HealthProfile();
        profile.setUsername("john");

        wireMockServer.stubFor(WireMock.post(WireMock.urlMatching("/profile"))
                .willReturn(aResponse().withStatus(OK.value())));

        client.mutateWith(mockJwt())
                .mutateWith(csrf())
                .post()
                .uri("/profile")
                .bodyValue(profile)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void whenReceiveDeleteProfileRequest_thenRouteToDeleteProfileEndpoint() {
        wireMockServer.stubFor(WireMock.delete(WireMock.urlMatching("/profile/john"))
                .willReturn(aResponse().withStatus(OK.value())));

        client.mutateWith(mockJwt())
                .mutateWith(csrf())
                .delete()
                .uri("/profile/john")
                .exchange()
                .expectStatus().isOk();
    }
}
